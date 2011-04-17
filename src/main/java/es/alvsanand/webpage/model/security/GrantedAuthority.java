package es.alvsanand.webpage.model.security;

import java.io.Serializable;

public interface GrantedAuthority extends Serializable {
    String getAuthority();
}
