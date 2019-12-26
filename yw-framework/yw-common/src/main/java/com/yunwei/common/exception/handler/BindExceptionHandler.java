package com.yunwei.common.exception.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.AbstractHandlerExceptionResolver;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import com.yunwei.common.exception.StringArgBindException;
import com.yunwei.common.util.FileUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

/**
 * 
* @ClassName: BindExceptionHandler 
* @Description: TODO(TODO) 校验异常处理
* @author zhangjh
* @date 2018年4月12日 下午2:02:39 
*
 */
public class BindExceptionHandler extends AbstractHandlerExceptionResolver {
	
	Logger logger = LoggerFactory.getLogger(BindExceptionHandler.class);

	@Override
	protected ModelAndView doResolveException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
		if (!(ex instanceof BindException || ex instanceof MissingServletRequestParameterException || ex instanceof MaxUploadSizeExceededException)) {
			return null;
		}
		Map<String, Object> errMap = errorMessage(ex);
		HandlerMethod method = (HandlerMethod)handler;
		ResponseBody anno = method.getMethodAnnotation(ResponseBody.class);
		RestController rest = method.getBeanType().getAnnotation(RestController.class);
		
		request.setAttribute("resMap_forInterceptor", errMap);
		
		if (anno != null || rest != null) {
			MappingJackson2JsonView view = new MappingJackson2JsonView();
			/*List<MediaType> producibleMediaTypes = messageConverters.getSupportedMediaTypes();*/
			List<MediaType> requestedMediaTypes = getAcceptableMediaTypes(request);
			/*Set<MediaType> compatibleMediaTypes = new LinkedHashSet<MediaType>();
			for (MediaType requestedType : requestedMediaTypes) {
				for (MediaType producibleType : producibleMediaTypes) {
					if (requestedType.isCompatibleWith(producibleType)) {
						compatibleMediaTypes.add(getMostSpecificMediaType(requestedType, producibleType));
					}
				}
			}
			if (compatibleMediaTypes.isEmpty()) {
				return null;
			}
			List<MediaType> mediaTypes = new ArrayList<MediaType>(compatibleMediaTypes);
			MediaType.sortBySpecificityAndQuality(mediaTypes);
			*/
			List<MediaType> mediaTypes = new ArrayList<MediaType>(requestedMediaTypes);

			MediaType selectedMediaType = null;
			for (MediaType mediaType : mediaTypes) {
				if (mediaType.isConcrete()) {
					selectedMediaType = mediaType;
					break;
				} else if (mediaType.equals(MediaType.ALL) || mediaType.equals(new MediaType("application"))) {
					selectedMediaType = MediaType.APPLICATION_JSON;
					break;
				}
			}
			if (selectedMediaType != null) {
				selectedMediaType = selectedMediaType.removeQualityValue();
				view.setContentType(selectedMediaType.toString());
			}
			view.addStaticAttribute("error_no", errMap.get("error_no"));
			view.addStaticAttribute("error_info", errMap.get("error_info"));
			if(errMap.get("errors")!=null){
				view.addStaticAttribute("error_info", errMap.get("error_info").toString() + errMap.get("errors").toString());
			}
			return new ModelAndView(view);
		}
		return new ModelAndView("error", errMap);
	}

	/**
	 * 异常信息
	 * @param ex
	 * @return
	 */
	protected Map<String, Object> errorMessage(Exception ex) {
		Map<String, Object> errMap = new HashMap<String, Object>();
		errMap.put("error_no", "-1");
		errMap.put("error_info", "参数传入错误");
		Map<String, String> map = new HashMap<String, String>();
		errMap.put("errors", map);
		if (ex instanceof BindException) {
			// StringArgumentValidResolver中的异常需要特殊处理一下key
			boolean isStringArgBindException = ex instanceof StringArgBindException;
			
			BindException bex = (BindException)ex;
			List<ObjectError> errors = bex.getAllErrors();
			for (ObjectError err : errors) {
				if (err instanceof FieldError) {
					FieldError tmpError = (FieldError)err;
					String key = isStringArgBindException ? tmpError.getObjectName() : tmpError.getField();
					map.put(key, tmpError.getDefaultMessage());
				} else {
					map.put(err.getCode(), err.getDefaultMessage());
				}
			}
		} else if (ex instanceof MissingServletRequestParameterException) {
			MissingServletRequestParameterException bex = (MissingServletRequestParameterException)ex;
			map.put(bex.getParameterName(), "不能为空");
		} else if (ex instanceof MaxUploadSizeExceededException) {
			errMap.put("error_no", "-1");
			errMap.put("error_info", "上传文件过大，最大【"+ FileUtil.formatFileSize(((MaxUploadSizeExceededException)ex).getMaxUploadSize(),"0") +"】");
		}
		return errMap;
	}

	private List<MediaType> getAcceptableMediaTypes(HttpServletRequest request) {
		String acceptHeader = request.getHeader("Accept");
		List<MediaType> mediaTypes = new ArrayList<MediaType>();
		if (StringUtils.hasText(acceptHeader)) {
			mediaTypes = MediaType.parseMediaTypes(acceptHeader);
			MediaType.sortBySpecificityAndQuality(mediaTypes);
		}
		return mediaTypes;
	}
	
	@Override
	public int getOrder() {
		return 0;
	}

}
