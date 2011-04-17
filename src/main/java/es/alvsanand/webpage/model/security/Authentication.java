package es.alvsanand.webpage.model.security;

import java.io.Serializable;
import java.security.Principal;
import java.util.Collection;


public interface Authentication extends Principal, Serializable {
    Collection<GrantedAuthority> getAuthorities();

    boolean isAuthenticated();

    void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException;
}
