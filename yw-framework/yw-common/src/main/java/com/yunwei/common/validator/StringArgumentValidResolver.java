package com.yunwei.common.validator;

import java.lang.annotation.Annotation;
import java.lang.annotation.ElementType;
import java.lang.reflect.Method;
import java.util.Map;

import javax.validation.Valid;
import javax.validation.Validation;
import javax.validation.ValidatorFactory;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.validator.HibernateValidator;
import org.hibernate.validator.HibernateValidatorConfiguration;
import org.hibernate.validator.cfg.ConstraintMapping;
import org.hibernate.validator.cfg.GenericConstraintDef;
import org.hibernate.validator.cfg.context.PropertyConstraintMappingContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.MethodParameter;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.springframework.validation.beanvalidation.SpringValidatorAdapter;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import com.yunwei.common.exception.StringArgBindException;
import com.yunwei.common.util.SpringContext;

/**
* @ClassName: StringArgumentValidResolver 
* @Description: TODO(TODO) 自动参数校验
* @author zhangjh
* @date 2018年4月11日 下午2:49:21 
*
 */
@Component("stringArgumentValidResolver")
@Order(2)
public class StringArgumentValidResolver implements HandlerMethodArgumentResolver {

	Logger logger = LoggerFactory.getLogger(StringArgumentValidResolver.class);

	@Override
	public boolean supportsParameter(MethodParameter paramMethodParameter) {

		if (!String.class.equals(paramMethodParameter.getParameterType())) {
			return false;
		}
		if (!paramMethodParameter.hasParameterAnnotation(Valid.class)) {
			return false;
		}
		return true;
	}

	@Override
	public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory)
			throws Exception {
		RequestParam ann = parameter.getParameterAnnotation(RequestParam.class);
		String paramName = parameter.getParameterName();
		String defaultVaule = null;
		if (ann != null) {
			paramName = StringUtils.defaultString(ann.value(), paramName);
			defaultVaule = ann.defaultValue();
		}
		String value = StringUtils.defaultString(webRequest.getParameter(paramName), defaultVaule);
		StringValidBean bean = new StringValidBean();
		bean.setValue(value);

		WebDataBinder binder = binderFactory.createBinder(webRequest, bean, paramName);
		binder.setValidator(createSingleValidator(parameter.getParameterAnnotations()));
		binder.validate();
		if (binder.getBindingResult().hasErrors() && isBindExceptionRequired(binder, parameter)) {
			throw new StringArgBindException(binder.getBindingResult());
		}
		// Add resolved attribute and BindingResult at the end of the model
		Map<String, Object> bindingResultModel = binder.getBindingResult().getModel();
		mavContainer.removeAttributes(bindingResultModel);
		mavContainer.addAllAttributes(bindingResultModel);

		return binder.convertIfNecessary(value, parameter.getParameterType(), parameter);
	}

	/**
	 * 动态校验规则
	 * @param annotations
	 * @return
	 */
	private Validator createSingleValidator(Annotation[] annotations) {

		ValidatorFactory validatorFacory = SpringContext.getBean("validator", ValidatorFactory.class);
		HibernateValidatorConfiguration config = Validation.byProvider(HibernateValidator.class).configure();
		config.messageInterpolator(validatorFacory.getMessageInterpolator());
		ConstraintMapping mapping = config.createConstraintMapping();
		PropertyConstraintMappingContext pc = mapping.type(StringValidBean.class).property("value", ElementType.FIELD);
		for (Annotation anno : annotations) {
			if (anno.annotationType().equals(Valid.class)) {
				continue;
			}
			GenericConstraintDef def = new GenericConstraintDef(anno.annotationType());
			for (Method m : anno.annotationType().getDeclaredMethods()) {
				try {
					def.param(m.getName(), m.invoke(anno, null));
				} catch (Exception e) {
					logger.warn("组装单个校验发生错误", e);
				}
			}
			pc.constraint(def);
		}
		config.addMapping(mapping);
		ValidatorFactory factory = config.buildValidatorFactory();
		Validator validator = new SpringValidatorAdapter(factory.getValidator());
		return validator;
	}

	protected boolean isBindExceptionRequired(WebDataBinder binder, MethodParameter methodParam) {
		int i = methodParam.getParameterIndex();
		Class<?>[] paramTypes = methodParam.getMethod().getParameterTypes();
		boolean hasBindingResult = (paramTypes.length > (i + 1) && Errors.class.isAssignableFrom(paramTypes[i + 1]));
		return !hasBindingResult;
	}

}
