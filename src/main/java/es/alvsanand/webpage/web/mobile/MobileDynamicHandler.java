package es.alvsanand.webpage.web.mobile;

import java.text.MessageFormat;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;

import com.ocpsoft.pretty.PrettyContext;

import es.alvsanand.webpage.common.UAgentInfo;

@ApplicationScoped
@ManagedBean(name="mobileDynamicHandler")
public class MobileDynamicHandler {
	private final static String USER_AGENT_HTTP_HEADER = "User-Agent";
	private final static String ACCEPT_HTTP_HEADER = "Accept";
	
	
	private final static String ERROR_REGEXP = "/error/(.*)";
	
	private final static String ERROR_PATTERN = "/xhtml/error/{0}.jsf";
	private final static String MOBILE_ERROR_PATTERN = "/xhtml/mobile/error/{0}.jsf";
	
	public String getErrorPath(){
		String viewId = null;
		
		PrettyContext prettyContext = PrettyContext.getCurrentInstance();
		
		String url = prettyContext.getRequestURL().getURL();
		
		UAgentInfo uAgentInfo = getUAgentInfo();
		
		String error = getError(url);
		
		if(error!=null){
			if (uAgentInfo.isSmartphone()) {
				viewId = MessageFormat.format(MOBILE_ERROR_PATTERN, new Object[]{error});
			}
			else{
				viewId = MessageFormat.format(ERROR_PATTERN, new Object[]{error});
			}
		}
		
		return viewId;
	}
	
	private String getError(String url){
		
		Pattern pattern = Pattern.compile(ERROR_REGEXP);
        Matcher matcher = pattern.matcher(url);
        if(matcher.find()){
        	return matcher.group(1);
        }
        else{
        	return null;
        }
	}
	
	private UAgentInfo getUAgentInfo(){
		FacesContext facesContext = FacesContext.getCurrentInstance();
		
		Map<String, String> headers = facesContext.getExternalContext().getRequestHeaderMap();
		
		String userAgent = headers.get(USER_AGENT_HTTP_HEADER);
		String httpAccept = headers.get(ACCEPT_HTTP_HEADER);
		
		return new UAgentInfo(userAgent, httpAccept);
		
	}
}
