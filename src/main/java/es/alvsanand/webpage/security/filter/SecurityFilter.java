package es.alvsanand.webpage.security.filter;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Collections;
import java.util.Locale;

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
import javax.servlet.http.HttpSession;

import es.alvsanand.webpage.common.Globals;
import es.alvsanand.webpage.common.Logger;
import es.alvsanand.webpage.init.DBInitiator;
import es.alvsanand.webpage.model.security.Authentication;
import es.alvsanand.webpage.model.security.GrantedAuthority;
import es.alvsanand.webpage.security.config.HasAllRolesTypeItem;
import es.alvsanand.webpage.security.config.HasAnyRoleTypeItem;
import es.alvsanand.webpage.security.config.HasNoRoleTypeItem;
import es.alvsanand.webpage.security.config.InterceptURL;
import es.alvsanand.webpage.security.config.SecurityConfig;
import es.alvsanand.webpage.security.exception.BadCredentialsException;

public class SecurityFilter implements Filter {
	private final static Logger logger = new Logger(SecurityFilter.class);

	private final static String CONFIG_FILE = "es.alvsanand.webpage.security.filter.SecurityFilter.ConfigFile";

	private final static String WEB_INF_DIR = "/WEB-INF";

	private final static String DEFAULT_CONFIG_FILE = WEB_INF_DIR + "/" + "security-config.xml";

	private SecurityConfig securityConfig;

	public void destroy() {
		logger.info("Destroying SecurityFilter");
	}

	public void init(FilterConfig filterConfig) throws ServletException {
		loadConfig(filterConfig);

		(new DBInitiator()).init();
		
		logger.info("MobileFilter initiated successfully");
	}

	public void loadConfig(FilterConfig filterConfig) {
		securityConfig = getSecurityConfigFromCache();

		if (securityConfig == null) {
			String configFile = filterConfig.getServletContext().getInitParameter(CONFIG_FILE);

			if (configFile == null || configFile.length() == 0) {
				configFile = DEFAULT_CONFIG_FILE;
			}

			InputStream inputStream = filterConfig.getServletContext().getResourceAsStream(configFile);

			try {
				securityConfig = SecurityConfig.unmarshal(new InputStreamReader(inputStream));

				putSecurityConfigFromCache(securityConfig);
			} catch (Exception e) {
				logger.error("Error loading SecurityConfig");

				throw new IllegalArgumentException("SecurityConfig is not correct");
			}
		}
	}

	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException,
			ServletException {
		if (securityConfig == null) {
			throw new IllegalArgumentException("SecurityConfig is not correct");
		}

		Locale locale =  (Locale)((HttpServletRequest)servletRequest).getSession().getAttribute(Globals.SES_LOCALE);
		
		if(locale==null){
			locale = servletRequest.getLocale();
			((HttpServletRequest)servletRequest).getSession().setAttribute(Globals.SES_LOCALE, locale);
		}
		
		String path = ((HttpServletRequest) servletRequest).getServletPath();

		logger.debug("Executing SecurityFilter for path " + path);

		Authentication authentication = getAuthentication(((HttpServletRequest) servletRequest).getSession());

		if (securityConfig != null && securityConfig.getInterceptURL() != null) {
			for (InterceptURL interceptURL : securityConfig.getInterceptURL()) {
				if (interceptURL == null) {
					continue;
				}

				String pattern = interceptURL.getPattern();

				if (path.matches(pattern)) {
					if (!interceptURL.isAuthenticated() && interceptURL.hasAuthenticated()) {
						if(authentication != null && authentication.isAuthenticated()) {
							((HttpServletResponse) servletResponse).sendRedirect(((HttpServletRequest) servletRequest).getContextPath()
								+ securityConfig.getDefaultURL());
						}
					} else {
						if (authentication == null || !authentication.isAuthenticated()) {
							((HttpServletResponse) servletResponse).sendRedirect(((HttpServletRequest) servletRequest).getContextPath()
									+ securityConfig.getDefaultURL());
						} else {
							if (interceptURL.getHasAllRoles() != null && interceptURL.getHasAllRoles().getHasAllRolesTypeItemCount() > 0) {
								for (HasAllRolesTypeItem hasAllRolesTypeItem : interceptURL.getHasAllRoles().getHasAllRolesTypeItem()) {
									boolean roleFounded = false;
									if (authentication != null && authentication.getAuthorities() != null) {
										for (GrantedAuthority grantedAuthority : authentication.getAuthorities()) {
											if (grantedAuthority.getAuthority() != null && hasAllRolesTypeItem != null
													&& grantedAuthority.getAuthority().equalsIgnoreCase(hasAllRolesTypeItem.getRole())) {
												roleFounded = true;
												break;
											}
										}
									}
									if (!roleFounded) {
										throw new BadCredentialsException("The user[" + authentication + "] has not enoguh privileges");
									}
								}
							} else {
								if (interceptURL.getHasAnyRole() != null && interceptURL.getHasAnyRole().getHasAnyRoleTypeItemCount() > 0) {
									boolean roleFounded = false;

									for (HasAnyRoleTypeItem hasAnyRoleTypeItem : interceptURL.getHasAnyRole().getHasAnyRoleTypeItem()) {
										if (authentication != null && authentication.getAuthorities() != null) {
											for (GrantedAuthority grantedAuthority : authentication.getAuthorities()) {

												if (grantedAuthority.getAuthority() != null && hasAnyRoleTypeItem != null
														&& grantedAuthority.getAuthority().equalsIgnoreCase(hasAnyRoleTypeItem.getRole())) {
													roleFounded = true;
													break;
												}
											}
										}
									}

									if (!roleFounded) {
										throw new BadCredentialsException("The user[" + authentication + "] has not enoguh privileges");
									}
								} else {
									if (interceptURL.getHasNoRole() != null && interceptURL.getHasNoRole().getHasNoRoleTypeItemCount() > 0) {
										for (HasNoRoleTypeItem hasNoRoleTypeItem : interceptURL.getHasNoRole().getHasNoRoleTypeItem()) {
											if (authentication != null && authentication.getAuthorities() != null) {
												boolean roleFounded = false;
												for (GrantedAuthority grantedAuthority : authentication.getAuthorities()) {
													if (grantedAuthority.getAuthority() != null && hasNoRoleTypeItem != null
															&& grantedAuthority.getAuthority().equalsIgnoreCase(hasNoRoleTypeItem.getRole())) {
														roleFounded = true;
														break;
													}
												}
												if (roleFounded) {
													throw new BadCredentialsException("The user[" + authentication + "] has not enoguh privileges");
												}
											}
										}
									}
								}
							}
						}
					}
				}
			}
		}

		filterChain.doFilter(servletRequest, servletResponse);
	}

	private static Authentication getAuthentication(HttpSession httpSession) {
		Authentication currentUser = (Authentication) httpSession.getAttribute(Globals.SES_AUTHENTICATION);

		return currentUser;
	}

	private es.alvsanand.webpage.security.config.SecurityConfig getSecurityConfigFromCache() {
		Cache cache = null;

		try {
			CacheFactory cacheFactory = CacheManager.getInstance().getCacheFactory();
			cache = cacheFactory.createCache(Collections.emptyMap());

			return (es.alvsanand.webpage.security.config.SecurityConfig) cache.get(es.alvsanand.webpage.security.config.SecurityConfig.class
					.getName());
		} catch (CacheException cacheException) {
			logger.error("Error in getting SecurityConfig from cache.", cacheException);

			return null;
		} catch (Exception exception) {
			if (cache != null) {
				cache.remove(es.alvsanand.webpage.security.config.SecurityConfig.class.getName());
			}
			return null;
		}
	}

	private void putSecurityConfigFromCache(es.alvsanand.webpage.security.config.SecurityConfig securityConfig) {
		Cache cache;

		try {
			CacheFactory cacheFactory = CacheManager.getInstance().getCacheFactory();
			cache = cacheFactory.createCache(Collections.emptyMap());
			cache.put(es.alvsanand.webpage.security.config.SecurityConfig.class.getName(), securityConfig);
		} catch (CacheException cacheException) {
			logger.error("Error in putting SecurityConfig from cache.", cacheException);
		}
	}
}
