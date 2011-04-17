package es.alvsanand.webpage.model.security;

import java.io.Serializable;
import java.util.Collection;



public interface UserDetails extends Serializable, Authentication {
    Collection<GrantedAuthority> getAuthorities();

    String getPassword();

    String getUsername();
}
