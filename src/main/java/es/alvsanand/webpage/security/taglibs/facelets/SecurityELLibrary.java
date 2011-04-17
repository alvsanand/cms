package es.alvsanand.webpage.security.taglibs.facelets;

import java.util.Collection;
import java.util.Set;
import java.util.TreeSet;

import javax.faces.context.FacesContext;

import es.alvsanand.webpage.common.Globals;
import es.alvsanand.webpage.model.security.Authentication;
import es.alvsanand.webpage.model.security.GrantedAuthority;

public class SecurityELLibrary {
	public SecurityELLibrary() {
	}

	private static Set<String> parseAuthorities(String grantedRoles) {
		Set<String> parsedAuthorities = new TreeSet<String>();
		if (grantedRoles == null || grantedRoles.isEmpty()) {
			return parsedAuthorities;
		}

		String[] parsedAuthoritiesArr;
		if (grantedRoles.contains(",")) {
			parsedAuthoritiesArr = grantedRoles.split(",");
		} else {
			parsedAuthoritiesArr = new String[] { grantedRoles };
		}

		for (String auth : parsedAuthoritiesArr)
			parsedAuthorities.add(auth.trim());
		return parsedAuthorities;
	}

	private static GrantedAuthority[] getUserAuthorities() {
		Authentication currentUser = (Authentication) FacesContext.getCurrentInstance().getExternalContext().getSessionMap()
				.get(Globals.SES_AUTHENTICATION);
		if (currentUser == null)
			return new GrantedAuthority[0];

		Collection<GrantedAuthority> authorities = currentUser.getAuthorities();
		if (authorities == null)
			return new GrantedAuthority[0];

		return authorities.toArray(new GrantedAuthority[] {});
	}

	public static boolean ifAnyGranted(final String grantedRoles) {
		Set<String> parsedAuthorities = parseAuthorities(grantedRoles);
		if (parsedAuthorities.isEmpty())
			return false;

		GrantedAuthority[] authorities = getUserAuthorities();

		for (GrantedAuthority authority : authorities) {
			if (parsedAuthorities.contains(authority.getAuthority()))
				return true;
		}
		return false;
	}

	public static boolean ifAllGranted(final String requiredRoles) {
		Set<String> requiredAuthorities = parseAuthorities(requiredRoles);
		if (requiredAuthorities.isEmpty())
			return false;

		GrantedAuthority[] authoritiesArray = getUserAuthorities();

		Set<String> grantedAuthorities = new TreeSet<String>();
		for (GrantedAuthority authority : authoritiesArray) {
			grantedAuthorities.add(authority.getAuthority());
		}

		for (String requiredAuthority : requiredAuthorities) {
			if (!grantedAuthorities.contains(requiredAuthority)) {
				return false;
			}
		}
		return true;
	}

	public static boolean ifNotGranted(final String notGrantedRoles) {
		Set<String> parsedAuthorities = parseAuthorities(notGrantedRoles);
		if (parsedAuthorities.isEmpty())
			return true;

		GrantedAuthority[] authorities = getUserAuthorities();

		for (GrantedAuthority authority : authorities) {
			if (parsedAuthorities.contains(authority.getAuthority()))
				return false;
		}
		return true;
	}
}