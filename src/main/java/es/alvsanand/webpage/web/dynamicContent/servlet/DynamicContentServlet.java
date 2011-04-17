package es.alvsanand.webpage.web.dynamicContent.servlet;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;

public class DynamicContentServlet extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = -2536499668649778994L;
	
	
	public static final String SES_DYNAMIC_CONTENT_DATA = "dynamicContentData";
	public static final String SES_DYNAMIC_CONTENT_MEDIA_TYPE = "dynamicContentMediaType";	
	public final static String SERVLET_URL_FORMAT = "/dynamicContent/{0}";
	public final static String SERVLET_URL_REGEXP = "/dynamicContent/(.*)";

	@Override
	public void destroy() {

	}

	@Override
	public ServletConfig getServletConfig() {
		return null;
	}

	@Override
	public String getServletInfo() {
		return null;
	}

	@Override
	public void init(ServletConfig arg0) throws ServletException {
	}

	@Override
	public void service(HttpServletRequest servletRequest, HttpServletResponse servletResponse) throws ServletException, IOException {
		Pattern pattern = Pattern.compile(SERVLET_URL_REGEXP);
		Matcher matcher = pattern.matcher(servletRequest.getRequestURI());
		if (matcher.find()) {
			String dynamicConentId = matcher.group(1);

			if (servletRequest.getSession() != null
					&& servletRequest.getSession().getAttribute(SES_DYNAMIC_CONTENT_DATA + dynamicConentId) != null
					&& StringUtils.isNotEmpty((String) servletRequest.getSession().getAttribute(
							SES_DYNAMIC_CONTENT_MEDIA_TYPE + dynamicConentId))) {
				byte[] contentData = (byte[]) servletRequest.getSession().getAttribute(SES_DYNAMIC_CONTENT_DATA + dynamicConentId);
				String contentMediaType = (String) servletRequest.getSession().getAttribute(SES_DYNAMIC_CONTENT_MEDIA_TYPE + dynamicConentId);

				if (contentData.length > 0) {
					servletResponse.setContentType(contentMediaType);
					servletResponse.setContentLength(contentData.length);

					InputStream inputStream = null;
					OutputStream out;
					try {
						inputStream = new BufferedInputStream(new ByteArrayInputStream(contentData));
						out = new BufferedOutputStream(servletResponse.getOutputStream());
						while (true) {
							int data = inputStream.read();
							if (data == -1) {
								break;
							}
							out.write(data);
						}
					} finally {
						if (inputStream != null) {
							inputStream.close();
						}
					}
				}
				servletRequest.getSession().removeAttribute(SES_DYNAMIC_CONTENT_DATA + dynamicConentId);
				servletRequest.getSession().removeAttribute(SES_DYNAMIC_CONTENT_MEDIA_TYPE + dynamicConentId);
			}
		}
	}
}