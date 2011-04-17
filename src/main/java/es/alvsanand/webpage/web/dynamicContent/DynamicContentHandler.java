package es.alvsanand.webpage.web.dynamicContent;

import java.text.MessageFormat;

import javax.faces.context.FacesContext;

import es.alvsanand.webpage.web.dynamicContent.servlet.DynamicContentServlet;

public class DynamicContentHandler {
	public static String addContent(byte[] contentData, String contentMediaType){		
		String randomValue = es.alvsanand.webpage.common.StringUtils.generateRandomString();
		
		FacesContext.getCurrentInstance().getExternalContext().
			getSessionMap().put(DynamicContentServlet.SES_DYNAMIC_CONTENT_DATA + randomValue, contentData);
		
		FacesContext.getCurrentInstance().getExternalContext().
			getSessionMap().put(DynamicContentServlet.SES_DYNAMIC_CONTENT_MEDIA_TYPE + randomValue, contentMediaType);
		return FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath() + MessageFormat.format(DynamicContentServlet.SERVLET_URL_FORMAT, new String[]{randomValue});
	}
}
