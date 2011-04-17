package es.alvsanand.webpage.web.filters;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.cache.Cache;
import javax.cache.CacheException;
import javax.cache.CacheFactory;
import javax.cache.CacheManager;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;

import es.alvsanand.webpage.common.Logger;
import es.alvsanand.webpage.common.UAgentInfo;
import es.alvsanand.webpage.init.DBInitiator;
import es.alvsanand.webpage.web.mobile.config.MobileConfig;

public class MobileFilter implements Filter {
	private final static Logger logger = new Logger(MobileFilter.class);

	private final static String USER_AGENT_HTTP_HEADER = "User-Agent";
	private final static String ACCEPT_HTTP_HEADER = "Accept";

	private final static String CONFIG_FILE = "es.alvsanand.webpage.web.mobile.config.MobileConfig";

	private final static String WEB_INF_DIR = "/WEB-INF";

	private final static String DEFAULT_CONFIG_FILE = WEB_INF_DIR + "/" + "security-config.xml";

	private MobileConfig mobileConfig;

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		loadConfig(filterConfig);
		
		(new DBInitiator()).init();
		
		logger.info("MobileFilter initiated successfully");
	}

	public void loadConfig(FilterConfig filterConfig) {
		mobileConfig = getMobileConfigFromCache();

		if (mobileConfig == null) {
			String configFile = filterConfig.getServletContext().getInitParameter(CONFIG_FILE);

			if (configFile == null || configFile.length() == 0) {
				configFile = DEFAULT_CONFIG_FILE;
			}

			InputStream inputStream = filterConfig.getServletContext().getResourceAsStream(configFile);

			try {
				mobileConfig = MobileConfig.unmarshal(new InputStreamReader(inputStream));

				putMobileConfigFromCache(mobileConfig);
			} catch (Exception e) {
				logger.error("Error loading MobileConfig");

				throw new IllegalArgumentException("MobileConfig is not correct");
			}
		}
	}

	@Override
	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
		if (mobileConfig == null) {
			throw new IllegalArgumentException("SecurityConfig is not correct");
		}
		
		HttpServletRequest httpServletRequest = (HttpServletRequest)servletRequest;
		HttpServletResponse httpServletResponse = (HttpServletResponse)servletResponse;
		
		String url = httpServletRequest.getRequestURI();
		
		logger.debug("Executing MobileFilter for url " + url);
		
		Map<String, String> headers = getHeaderMap(httpServletRequest);
		
		String userAgent = headers.get(USER_AGENT_HTTP_HEADER);
		String httpAccept = headers.get(ACCEPT_HTTP_HEADER);
		
		UAgentInfo uAgentInfo = new UAgentInfo(userAgent, httpAccept);
		
		try {
			Matcher m = (Pattern.compile(mobileConfig.getNoMobilePattern())).matcher(url);

			if (m.matches() && uAgentInfo.isSmartphone()) {				
				boolean end = false;
				for(String pattern: mobileConfig.getExcludedResources().getUrlPattern()){
					if(url.matches(pattern)){
						end = true;
						break;
					}
				}

				if (!end && StringUtils.isNotEmpty(userAgent) && StringUtils.isNotEmpty(httpAccept)) {
					String result = mobileConfig.getDefaultURL();
					
					httpServletResponse.sendRedirect(result);						
					return;
				}
			}
		} catch (Exception e) {
			logger.error("Error executing MobileFilter for url " + url);
		}
		
		filterChain.doFilter(servletRequest, servletResponse);
	}

	@Override
	public void destroy() {
		logger.info("Destroying MobileFilter");
	}

	private es.alvsanand.webpage.web.mobile.config.MobileConfig getMobileConfigFromCache() {
		Cache cache = null;

		try {
			CacheFactory cacheFactory = CacheManager.getInstance().getCacheFactory();
			cache = cacheFactory.createCache(Collections.emptyMap());

			return (es.alvsanand.webpage.web.mobile.config.MobileConfig) cache.get(es.alvsanand.webpage.web.mobile.config.MobileConfig.class
					.getName());
		} catch (CacheException cacheException) {
			logger.error("Error in getting MobileConfig from cache.", cacheException);

			return null;
		}
		catch(Exception exception){
			if(cache!=null){
				cache.remove(es.alvsanand.webpage.web.mobile.config.MobileConfig.class.getName());
			}
			return null;
		}
	}

	private void putMobileConfigFromCache(es.alvsanand.webpage.web.mobile.config.MobileConfig mobileConfig) {
		Cache cache;

		try {
			CacheFactory cacheFactory = CacheManager.getInstance().getCacheFactory();
			cache = cacheFactory.createCache(Collections.emptyMap());
			cache.put(es.alvsanand.webpage.web.mobile.config.MobileConfig.class.getName(), mobileConfig);
		} catch (CacheException cacheException) {
			logger.error("Error in putting MobileConfig from cache.", cacheException);
		}
	}

	private Map<String, String> getHeaderMap(final HttpServletRequest request) {
		Map<String, String> headers = new HashMap<String, String>();

		for (Enumeration<?> e = request.getHeaderNames(); e.hasMoreElements();) {
			String headerName = (String) e.nextElement();

			headers.put(headerName, request.getHeader(headerName));
		}

		return headers;
	}
}
