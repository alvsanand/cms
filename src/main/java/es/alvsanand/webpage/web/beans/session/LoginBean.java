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
package es.alvsanand.webpage.web.beans.session;

import java.io.Serializable;
import java.security.NoSuchAlgorithmException;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import org.apache.commons.codec.binary.Base64;

import es.alvsanand.webpage.common.Logger;
import es.alvsanand.webpage.security.exception.AuthenticationException;
import es.alvsanand.webpage.services.security.CryptographyService;
import es.alvsanand.webpage.services.security.CryptographyServiceImpl;
import es.alvsanand.webpage.services.security.LoginService;
import es.alvsanand.webpage.services.security.LoginServiceImpl;

/**
 * 
 * 
 * @author alvaro.santos
 * @date 30/11/2009
 * 
 */
@RequestScoped
@ManagedBean(name="loginBean")
public class LoginBean implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 8778520431388212339L;

	private transient static final Logger logger = new Logger(LoginBean.class);
	
	private transient static final String LOGIN_VIEW_ID = "pretty:home";
	private transient static final String LOGOUT_VIEW_ID = "pretty:home";
	
	public transient static final String GOOGLE_SSO_LOGIN_URL = "pretty:loginGoogleSSO";
	public transient static final String GOOGLE_SSO_LOGOUT_URL = "pretty:logoutGoogleSSO";
	
	private transient CryptographyService cryptographyService;
	
	private transient LoginService loginService;
	
	private String password;
	
	private String username;

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public CryptographyService getCryptographyService() {
		if(cryptographyService==null){
			cryptographyService = new CryptographyServiceImpl();
		}
		return cryptographyService;
	}

	public LoginService getLoginService() {
		if(loginService==null){
			loginService = new LoginServiceImpl();
		}
		return loginService;
	}

	public String getloginGoogleSSOURL() throws AuthenticationException{
		logger.info("Launched LoginBean.getloginGoogleSSOURL");
		
		return getLoginService().getloginGoogleSSOURL();
	}

	public String getLogoutGoogleSSOURL() throws AuthenticationException{
		logger.info("Launched LoginBean.getLogoutGoogleSSOURL");
		
		return getLoginService().getLogoutGoogleSSOURL();
	}

	//JSF methods

	public String login() throws AuthenticationException{
		logger.info("Launched LoginBean.login[" + username + "]");
		
		String passwordDigested = null;
		if(getPassword()!=null){
			try {
				passwordDigested = new String(Base64.encodeBase64(getCryptographyService().digest(getPassword().getBytes())));
			} catch (NoSuchAlgorithmException noSuchAlgorithmException) {
			}
		}
		
		getLoginService().loginByUsername(username, passwordDigested);
		
		return LOGIN_VIEW_ID;
	}

	public String logout() throws AuthenticationException{
		logger.info("Launched LoginBean.logout");
		
		getLoginService().logout();
		
		return LOGOUT_VIEW_ID;
	}

	public String loginGoogleSSO() throws AuthenticationException{
		logger.info("Launched LoginBean.loginGoogleSSO");
		
		getLoginService().loginGoogleSSO();
		
		return LOGIN_VIEW_ID;
	}

	public String logoutGoogleSSO() throws AuthenticationException{
		logger.info("Launched LoginBean.logoutGoogleSSO");
		
		getLoginService().logoutGoogleSSO();
		
		return LOGOUT_VIEW_ID;
	}
}