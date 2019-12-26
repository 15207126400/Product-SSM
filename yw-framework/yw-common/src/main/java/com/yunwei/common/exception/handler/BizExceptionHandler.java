package com.yunwei.common.exception.handler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.AbstractHandlerExceptionResolver;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import com.yunwei.common.exception.BizException;

/**
 * 
* @ClassName: BizExceptionHandler 
* @Description: TODO(TODO) 业务异常处理
* @author zhangjh
* @date 2018年4月12日 下午2:02:51 
*
 */
public class BizExceptionHandler extends AbstractHandlerExceptionResolver {
	
	private Logger logger = LoggerFactory.getLogger(getClass());

	@Override
	protected ModelAndView doResolveException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
		if (!(ex instanceof BizException)) {
			logger.error("系统运行时异常", ex);
			return null;
		}
		BizException bex = (BizException)ex;
		HandlerMethod method = (HandlerMethod)handler;
		ResponseBody anno = method.getMethodAnnotation(ResponseBody.class);
		RestController rest = method.getBeanType().getAnnotation(RestController.class);

		Map<String, Object> errMap = new HashMap<String, Object>();
		errMap.put("error_no", bex.getError_no());
		errMap.put("error_info", bex.getError_info());
		if(bex.getInfos()!=null){
			errMap.put("infos",bex.getInfos());
		}
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
			view.addStaticAttribute("error_no", bex.getError_no());
			view.addStaticAttribute("error_info", bex.getError_info());
			if(bex.getInfos()!=null){
				view.addStaticAttribute("infos", bex.getInfos());
			}
			return new ModelAndView(view);
		}
		request.setAttribute("exception", bex);
		request.setAttribute("error_no", bex.getError_no());
		request.setAttribute("error_info", bex.getError_info());
		return new ModelAndView("error");
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

	/**
	 * Return the more specific of the acceptable and the producible media types
	 * with the q-value of the former.
	 */
	/*private MediaType getMostSpecificMediaType(MediaType acceptType, MediaType produceType) {
		MediaType produceTypeToUse = produceType.copyQualityValue(acceptType);
		return (MediaType.SPECIFICITY_COMPARATOR.compare(acceptType, produceTypeToUse) <= 0 ? acceptType : produceTypeToUse);
	}*/

	@Override
	public int getOrder() {
		return 0;
	}
}
