/*
 * Copyright 2008-2010 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package es.alvsanand.webpage.services.security;

import java.util.HashMap;
import java.util.Map;

import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import com.ocpsoft.pretty.PrettyContext;
import com.ocpsoft.pretty.faces.config.mapping.UrlMapping;
import com.ocpsoft.pretty.faces.util.PrettyURLBuilder;

import es.alvsanand.webpage.common.Globals;
import es.alvsanand.webpage.common.Logger;
import es.alvsanand.webpage.db.dao.DAOException;
import es.alvsanand.webpage.db.dao.admin.UserDAO;
import es.alvsanand.webpage.db.dao.admin.UserDAOImpl;
import es.alvsanand.webpage.model.Role;
import es.alvsanand.webpage.model.User;
import es.alvsanand.webpage.model.UserState;
import es.alvsanand.webpage.model.security.Authentication;
import es.alvsanand.webpage.model.security.GrantedAuthority;
import es.alvsanand.webpage.model.security.GrantedAuthorityImpl;
import es.alvsanand.webpage.model.security.UserDetails;
import es.alvsanand.webpage.model.security.UserDetailsImpl;
import es.alvsanand.webpage.security.exception.AuthenticationException;
import es.alvsanand.webpage.security.exception.BadCredentialsException;
import es.alvsanand.webpage.security.exception.DataRetrievalFailureException;
import es.alvsanand.webpage.security.exception.UsernameDisabledException;
import es.alvsanand.webpage.security.exception.UsernameNotAcceptedException;
import es.alvsanand.webpage.security.exception.UsernameNotFoundException;
import es.alvsanand.webpage.services.ServiceException;
import es.alvsanand.webpage.web.beans.session.LoginBean;

/**
 * 
 * 
 * @author alvaro.santos
 * @date 30/11/2009
 * 
 */
public class LoginServiceImpl implements LoginService {
	private final static Logger logger = new Logger(LoginServiceImpl.class);

	private UserDAO userDAO = new UserDAOImpl();

	private es.alvsanand.webpage.services.session.UserService userService = new es.alvsanand.webpage.services.session.UserServiceImpl();

	public void loginByUsername(String username, String password) throws AuthenticationException {
		UserDetails userDetails = null;
		try {
			logger.debug("Logging in username[" + ((username != null) ? username : "") + "]");

			User user = userDAO.getUserByLoginName(username);

			if (user == null) {
				throw new UsernameNotFoundException("User not found: " + username);
			}

			if (user.getState() == UserState.REGISTERED.ordinal()) {
				throw new UsernameNotAcceptedException("User has not been accepted: " + username);
			}

			if (user.getState() == UserState.DISABLED.ordinal()) {
				throw new UsernameDisabledException("User has been disabled: " + username);
			}

			if (password == null || !password.equalsIgnoreCase(user.getPassword())) {
				throw new BadCredentialsException("Password is not correct: " + username);
			}

			java.util.Collection<GrantedAuthority> grantedAuthoritys = new java.util.ArrayList<GrantedAuthority>();
			if (user.getRole() != null) {
				grantedAuthoritys.add(GrantedAuthorityImpl.APPLICATION_ROLES_MAP.get(user.getRole().getName()));
			}

			userDetails = new UserDetailsImpl(user.getLoginName(), user.getPassword(), grantedAuthoritys);
			userDetails.setAuthenticated(true);

			putAuthentication(userDetails);

			if (user != null) {
				user.setLastLogindate(new java.util.Date());
				userDAO.saveOrUpdateUser(user);

				user = userDAO.getUserByLoginName(userDetails.getName());

				putUser(user);
			}
		} catch (DAOException daoException) {
			logger.error("Error loading username", daoException);

			throw new DataRetrievalFailureException("Error getting username", daoException);
		}
	}

	private static void putUser(User user) {
		if(FacesContext.getCurrentInstance()!=null && FacesContext.getCurrentInstance().getExternalContext().getSessionMap()!=null){
			FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put(Globals.SES_USER, user);
		}
	}

	private static void putAuthentication(Authentication authentication) {
		if(FacesContext.getCurrentInstance()!=null && FacesContext.getCurrentInstance().getExternalContext().getSessionMap()!=null){
			FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put(Globals.SES_AUTHENTICATION, authentication);
		}
	}

	public void logout() {
		User actualUser = (User) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get(Globals.SES_USER);

		logger.debug("Logging out[" + ((actualUser != null) ? actualUser : "") + "]");

		FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
	}

	public void loginGoogleSSO() throws AuthenticationException {
		UserDetails userDetails = null;
		try {			
			UserService googleUserService = UserServiceFactory.getUserService();
			
			com.google.appengine.api.users.User googelUser = googleUserService.getCurrentUser();
			
			logger.debug("Logging GoogleSSO in username[" + ((googelUser != null) ? googelUser : "") + "]");
			
			if (googelUser == null) {
				throw new UsernameNotFoundException("User not found in GoogleSSO");
			}				
			
			String username = googelUser.getEmail();
			
			User user = userDAO.getUserByLoginName(username);

			if (user == null) {				
				user = new User();

				user.setLoginName(username);
				user.setName(googelUser.getNickname());
				user.setPassword("UNUSED");
				user.setEmail(username);

				Role role = new Role();
				role.setName(GrantedAuthorityImpl.ROLE_GENERIC_USER.getAuthority());
				user.setRole(role);
				user.setState(UserState.ACCEPTED.getValue());
				user.setGoogleAcount(true);
				
				userService.saveUser(user);
			}

			if (user.getState() == UserState.REGISTERED.ordinal()) {
				throw new UsernameNotAcceptedException("User has not been accepted: " + username);
			}

			if (user.getState() == UserState.DISABLED.ordinal()) {
				throw new UsernameDisabledException("User has been disabled: " + username);
			}

			java.util.Collection<GrantedAuthority> grantedAuthoritys = new java.util.ArrayList<GrantedAuthority>();
			if (user.getRole() != null) {
				grantedAuthoritys.add(GrantedAuthorityImpl.APPLICATION_ROLES_MAP.get(user.getRole().getName()));
			}

			userDetails = new UserDetailsImpl(user.getLoginName(), user.getPassword(), grantedAuthoritys);
			userDetails.setAuthenticated(true);

			putAuthentication(userDetails);

			if (user != null) {
				user.setLastLogindate(new java.util.Date());
				userDAO.saveOrUpdateUser(user);

				user = userDAO.getUserByLoginName(userDetails.getName());

				putUser(user);
			}
		} catch (DAOException daoException) {
			logger.error("Error  loading GoogleSSO username", daoException);

			throw new DataRetrievalFailureException("Error getting username", daoException);
		} catch (ServiceException serviceException) {
			logger.error("Error  loading GoogleSSO username", serviceException);

			throw new DataRetrievalFailureException("Error getting username", serviceException);
		}
	}

	public void logoutGoogleSSO() {
		User actualUser = (User) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get(Globals.SES_USER);

		logger.debug("Logging GoogleSSO out [" + ((actualUser != null) ? actualUser : "") + "]");

		FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
	}

	@Override
	public String getloginGoogleSSOURL() throws AuthenticationException {
		logger.debug("Getting login GoogleSSO URL");
		
		UserService googleUserService = UserServiceFactory.getUserService();
		
		HttpServletRequest request = (HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest();
		HttpServletResponse response = (HttpServletResponse)FacesContext.getCurrentInstance().getExternalContext().getResponse();
		
		PrettyContext context = PrettyContext.getCurrentInstance(request);
		PrettyURLBuilder builder = new PrettyURLBuilder();
		
		UrlMapping mapping = context.getConfig().getMappingById(LoginBean.GOOGLE_SSO_LOGIN_URL);
		String targetURL = builder.build(mapping, (Map<String, String[]>)new HashMap<String, String[]>());
		
		targetURL = response.encodeRedirectURL(targetURL);
		
		return googleUserService.createLoginURL(targetURL) ;
	}

	@Override
	public String getLogoutGoogleSSOURL() {
		logger.debug("Getting logout GoogleSSO URL");
		
		UserService googleUserService = UserServiceFactory.getUserService();		
		
		HttpServletRequest request = (HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest();
		HttpServletResponse response = (HttpServletResponse)FacesContext.getCurrentInstance().getExternalContext().getResponse();
		
		PrettyContext context = PrettyContext.getCurrentInstance(request);
		PrettyURLBuilder builder = new PrettyURLBuilder();
		
		UrlMapping mapping = context.getConfig().getMappingById(LoginBean.GOOGLE_SSO_LOGOUT_URL);
		String targetURL = builder.build(mapping, (Map<String, String[]>)new HashMap<String, String[]>());
		
		targetURL = response.encodeRedirectURL(targetURL);
		
		return googleUserService.createLogoutURL(targetURL) ;
	}
}
