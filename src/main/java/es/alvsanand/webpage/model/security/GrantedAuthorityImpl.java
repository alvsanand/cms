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
package es.alvsanand.webpage.model.security;

/**
 *
 *
 * @author alvaro.santos
 * @date 03/12/2009
 *
 */
public class GrantedAuthorityImpl implements java.io.Serializable, es.alvsanand.webpage.model.security.GrantedAuthority{	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -1116805885030991769L;
	/**
	 * 
	 */
	private String authority;
	/**
	 * 
	 */
	private String description;
	
	public final static GrantedAuthorityImpl ROLE_DEVELOPER = new GrantedAuthorityImpl("ROLE_DEVELOPER", "Like admin but for developing purposes");
	public final static GrantedAuthorityImpl ROLE_ADMIN = new GrantedAuthorityImpl("ROLE_ADMIN", "Manage the webpage");
	public final static GrantedAuthorityImpl ROLE_PUBLISHER_USER = new GrantedAuthorityImpl("ROLE_PUBLISHER_USER", "Create articles and make comments, view articles and comments");
	public final static GrantedAuthorityImpl ROLE_GENERIC_USER = new GrantedAuthorityImpl("ROLE_GENERIC_USER", "Make comments, View articles and comments");
	
	public final static java.util.List<String> APPLICATION_ROLES;
	static{
		APPLICATION_ROLES = new java.util.ArrayList<String>();
		APPLICATION_ROLES.add(ROLE_DEVELOPER.getAuthority());
		APPLICATION_ROLES.add(ROLE_ADMIN.getAuthority());
		APPLICATION_ROLES.add(ROLE_PUBLISHER_USER.getAuthority());
		APPLICATION_ROLES.add(ROLE_GENERIC_USER.getAuthority());
	}
	
	public final static java.util.Map<String, GrantedAuthorityImpl> APPLICATION_ROLES_MAP;
	static{
		APPLICATION_ROLES_MAP = new java.util.HashMap<String, GrantedAuthorityImpl>();
		APPLICATION_ROLES_MAP.put(ROLE_DEVELOPER.getAuthority(), ROLE_DEVELOPER);
		APPLICATION_ROLES_MAP.put(ROLE_ADMIN.getAuthority(), ROLE_ADMIN);
		APPLICATION_ROLES_MAP.put(ROLE_PUBLISHER_USER.getAuthority(), ROLE_PUBLISHER_USER);
		APPLICATION_ROLES_MAP.put(ROLE_GENERIC_USER.getAuthority(), ROLE_GENERIC_USER);
	}
	
	/**
	 * 
	 */
	public GrantedAuthorityImpl() {
	}
	
	/**
	 * @param authority
	 * @param description
	 */
	public GrantedAuthorityImpl(String authority, String description) {
		this.authority = authority;
		this.description = description;
	}
	
	/**
	 * Return the authority of the field authority
	 *
	 * @return the authority
	 */
	public String getAuthority() {
		return authority;
	}
	/**
	 * Set the authority of the field authority
	 *
	 * @param authority the authority to set
	 */
	public void setAuthority(String authority) {
		this.authority = authority;
	}
	/**
	 * Return the value of the field description
	 *
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}
	/**
	 * Set the value of the field description
	 *
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/* (non-Javadoc)
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 */
	public int compareTo(Object obj) {
		if (this == obj)
			return 0;
		if (obj == null)
			return 1;
		if (!(obj instanceof es.alvsanand.webpage.model.security.GrantedAuthority))
			return 1;
		
		es.alvsanand.webpage.model.security.GrantedAuthority authority = (es.alvsanand.webpage.model.security.GrantedAuthority)obj;
		
		if(authority.getAuthority()==null && this.getAuthority()==null){
			return 0;
		}
		if(authority.getAuthority()==null){
			return 1;
		}
		if(this.getAuthority()==null){
			return -1;
		}
		
		if(APPLICATION_ROLES.indexOf(authority.getAuthority())>APPLICATION_ROLES.indexOf(this.getAuthority())){
			return -1;
		}
		
		if(APPLICATION_ROLES.indexOf(authority.getAuthority())<APPLICATION_ROLES.indexOf(this.getAuthority())){
			return 1;
		}
		
		return 0;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((authority == null) ? 0 : authority.hashCode());
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		GrantedAuthorityImpl other = (GrantedAuthorityImpl) obj;
		if (authority == null) {
			if (other.authority != null)
				return false;
		} else if (!authority.equals(other.authority))
			return false;
		return true;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "ApplicationRole [" + (authority != null ? "authority=" + authority + ", " : "")
				+ (description != null ? "description=" + description : "") + "]";
	}
}
