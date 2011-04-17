package es.alvsanand.webpage.services.security;

import es.alvsanand.webpage.security.exception.AuthenticationException;

public interface LoginService {
	public abstract void loginByUsername(String username, String password) throws AuthenticationException;
	public abstract void logout();
	public abstract void loginGoogleSSO() throws AuthenticationException;
	public abstract void logoutGoogleSSO();
	public abstract String getloginGoogleSSOURL() throws AuthenticationException;
	public abstract String getLogoutGoogleSSOURL();
}