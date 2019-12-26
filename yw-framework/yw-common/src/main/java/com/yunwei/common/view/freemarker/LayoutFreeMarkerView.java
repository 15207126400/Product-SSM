package com.yunwei.common.view.freemarker;

import java.io.IOException;
import java.io.StringWriter;
import java.util.Locale;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.web.servlet.view.freemarker.FreeMarkerView;

import freemarker.cache.TemplateLoader;
import freemarker.template.SimpleHash;
import freemarker.template.Template;
import freemarker.template.TemplateException;

/**
 * 功能说明: 装饰性freemarker模板<br>
 * 系统版本: v1.0<br>
 * 开发人员: @author zhangjiahao<br>
 * 开发时间: 2018年3月7日<br>
 */
public class LayoutFreeMarkerView extends FreeMarkerView {

	public static final String DEFAULT_LAYOUT_URL = "default.ftl";

	public static final String DEFAULT_TEMPLATE = "default";

	public static final String DEFAULT_SCREEN_CONTENT_KEY = "screen_content";

	public static int SISAP = -1;

	/**
	 * 重写freemarker模板，增加装饰效果；<br/>
	 * 若不存在装饰模板，则仅处理程序页面模板
	 * @param template
	 * @param model
	 * @param response
	 * @return
	 * @throws IOException
	 *         ,TemplateException
	 */
	@Override
	protected void processTemplate(Template template, SimpleHash model, HttpServletResponse response) throws IOException, TemplateException {

		// 目前暂时使用通用模板，后期开发个性化模板
		String template_name = DEFAULT_TEMPLATE;

		Template layoutTemplate = findLayoutTemplate(template.getConfiguration().getTemplateLoader(), template_name + "/" + getUrl(), template_name, template.getLocale());
		if (layoutTemplate == null && !StringUtils.equals(DEFAULT_TEMPLATE, template_name)) {
			layoutTemplate = findLayoutTemplate(template.getConfiguration().getTemplateLoader(), DEFAULT_TEMPLATE + "/" + getUrl(), template_name, template.getLocale());
		}
		if (layoutTemplate != null) {
			processScreenTemplate(template, model);

			layoutTemplate.process(model, response.getWriter());
		} else {
			template.process(model, response.getWriter());
		}
	}

	/**
	 * 查找当前模板的装饰，同路径同名文件 》 同路径default.ftl 》 上层default.ftl 》 default目录
	 * @param templateLoader
	 * @param url
	 * @param locale
	 * @return
	 * @throws IOException
	 */
	private Template findLayoutTemplate(TemplateLoader templateLoader, String url, String template_name, Locale locale) throws IOException {

		if (SISAP == -1) {
			Object t = templateLoader.findTemplateSource(DEFAULT_TEMPLATE + "/" + DEFAULT_LAYOUT_URL);
			if (t == null) {
				SISAP = 1;
			} else {
				SISAP = 0;
			}
		}
//		String flag = RequestUtil.getRequest().getParameter("sandbox");
//		String sandbox_flag = RequestUtil.getRequest().getParameter("sandbox_flag");
//		if ("true".equals(sandbox_flag)) {
//			return getTemplate("sandbox.ftl", locale);
//		}
//		if (SISAP == 1 && "cfsecu".equals(template_name)) {
//			return getTemplate("sandbox_cfsecu.ftl", locale);
//		}
//		if (SISAP == 1 || "true".equalsIgnoreCase(flag)) {
//			return getTemplate("sandbox.ftl", locale);
//		}
		Object t = templateLoader.findTemplateSource(url);
		if (t != null) {
			return getTemplate(url, locale);
		} else if (!(template_name + "/" + DEFAULT_LAYOUT_URL).equals(url)) {
			String[] paths = url.split("/");
			String name = paths[paths.length - 1];
			if (!DEFAULT_LAYOUT_URL.equals(name)) {
				url = url.replace(name, DEFAULT_LAYOUT_URL);
			} else {
				url = "";
				for (int i = 0; i < paths.length - 2; i++) {
					url += paths[i] + "/";
				}
				url += DEFAULT_LAYOUT_URL;
			}
			return findLayoutTemplate(templateLoader, url, template_name, locale);
		}
		return null;
	}

	/**
	 * 解析程序页面模板，以字符串形式写入screen_content
	 * @param template
	 * @param model
	 * @throws TemplateException
	 * @throws IOException
	 */
	private void processScreenTemplate(Template template, SimpleHash model) throws TemplateException, IOException {
		StringWriter sw = new StringWriter();
		template.process(model, sw);
		model.put(DEFAULT_SCREEN_CONTENT_KEY, sw.toString());
	}
}
