package es.alvsanand.webpage.security.exception;

import es.alvsanand.webpage.AlvsanandException;
import es.alvsanand.webpage.model.security.Authentication;

public abstract class AuthenticationException extends AlvsanandException {
    /**
	 * 
	 */
	private static final long serialVersionUID = 5426511355894084599L;
	
	private Authentication authentication;
    private Object extraInformation;

    public AuthenticationException(String msg, Throwable t) {
        super(msg, t);
    }

    public AuthenticationException(String msg) {
        super(msg);
    }

    public AuthenticationException(String msg, Object extraInformation) {
        super(msg);
        this.extraInformation = extraInformation;
    }

    public Authentication getAuthentication() {
        return authentication;
    }

    public void setAuthentication(Authentication authentication) {
        this.authentication = authentication;
    }

    public Object getExtraInformation() {
        return extraInformation;
    }

    public void clearExtraInformation() {
        this.extraInformation = null;
    }
}
