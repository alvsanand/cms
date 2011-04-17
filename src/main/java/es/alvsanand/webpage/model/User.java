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
package es.alvsanand.webpage.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.datanucleus.jpa.annotations.Extension;


/**
 * The persistent class for the USER database table.
 * 
 */
@Entity
@Table(name = "User")
public class User implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -6233459895987808972L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Extension(vendorName = "datanucleus", key = "gae.encoded-pk",value = "true")
    @Column(columnDefinition = "idUser")
    private String idUser;

    @Temporal( TemporalType.DATE)
	@Column(columnDefinition = "creationdate")
    private Date creationdate;

    @Temporal( TemporalType.DATE)
	@Column(columnDefinition = "lastLoginDate")
    private Date lastLoginDate;

    @Column(columnDefinition = "loginName")
    private String loginName;

    @Column(columnDefinition = "name")
    private String name;

    @Column(columnDefinition = "password")
    private String password;

    @Column(columnDefinition = "surname")
    private String surname;

    @Column(columnDefinition = "email")
    private String email;

    @Column(columnDefinition = "registrationHash")
    private String registrationHash;

    @Column(columnDefinition = "state")
    private int state;

    @Column(columnDefinition = "googleAcount")
    private boolean googleAcount;

    @Transient
	private List<ArticleVersion> articleVersions = new java.util.ArrayList<ArticleVersion>();

    @Transient
	private Role role;

    @Transient
	private Avatar avatar;

    public User() {
    	state = UserState.REGISTERED.ordinal();
    }

    public User(String idUser) {
    	this.idUser = idUser;
    	state = UserState.REGISTERED.ordinal();
    }

	public String getIdUser() {
		return this.idUser;
	}

	public void setIdUser(String idUser) {
		this.idUser = idUser;
	}

	public Date getCreationdate() {
		return this.creationdate;
	}

	public void setCreationdate(Date creationdate) {
		this.creationdate = creationdate;
	}

	public Date getLastLogindate() {
		return this.lastLoginDate;
	}

	public void setLastLogindate(Date lastLoginDate) {
		this.lastLoginDate = lastLoginDate;
	}

	public String getLoginName() {
		return this.loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getSurname() {
		return this.surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public List<ArticleVersion> getArticleVersions() {
		return this.articleVersions;
	}

	public void setArticleversions(List<ArticleVersion> articleVersions) {
		this.articleVersions = articleVersions;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public Avatar getAvatar() {
		return avatar;
	}

	public void setAvatar(Avatar avatar) {
		this.avatar = avatar;
	}

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}

	public Date getLastLoginDate() {
		return lastLoginDate;
	}

	public void setLastLoginDate(Date lastLoginDate) {
		this.lastLoginDate = lastLoginDate;
	}

	public String getRegistrationHash() {
		return registrationHash;
	}

	public void setRegistrationHash(String registrationHash) {
		this.registrationHash = registrationHash;
	}

	public boolean isGoogleAcount() {
		return googleAcount;
	}

	public void setGoogleAcount(boolean googleAcount) {
		this.googleAcount = googleAcount;
	}
	
	public boolean getCanBeAccepted(){
		UserState userState = UserState.getUserState(state);
		UserState[] possibleUserState = userState.getPossibleUserState();
		
		for(UserState state: possibleUserState){
			if(UserState.ACCEPTED.equals(state)){
				return true;
			}	
		}
		
		return false;
	}
	
	public boolean getCanBeRejected(){
		UserState userState = UserState.getUserState(state);
		UserState[] possibleUserState = userState.getPossibleUserState();
		
		for(UserState state: possibleUserState){
			if(UserState.REJECTED.equals(state)){
				return true;
			}	
		}
		
		return false;
	}
	
	public boolean getCanBeDisabled(){
		UserState userState = UserState.getUserState(state);
		UserState[] possibleUserState = userState.getPossibleUserState();
		
		for(UserState state: possibleUserState){
			if(UserState.DISABLED.equals(state)){
				return true;
			}	
		}
		
		return false;
	}
	
	public boolean getCanBeEnabled(){
		UserState userState = UserState.getUserState(state);
		UserState[] possibleUserState = userState.getPossibleUserState();
		
		for(UserState state: possibleUserState){
			if(UserState.ENABLED.equals(state)){
				return true;
			}	
		}
		
		return false;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((articleVersions == null) ? 0 : articleVersions.hashCode());
		result = prime * result + ((avatar == null) ? 0 : avatar.hashCode());
		result = prime * result + ((creationdate == null) ? 0 : creationdate.hashCode());
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		result = prime * result + (googleAcount ? 1231 : 1237);
		result = prime * result + ((idUser == null) ? 0 : idUser.hashCode());
		result = prime * result + ((lastLoginDate == null) ? 0 : lastLoginDate.hashCode());
		result = prime * result + ((loginName == null) ? 0 : loginName.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((password == null) ? 0 : password.hashCode());
		result = prime * result + ((registrationHash == null) ? 0 : registrationHash.hashCode());
		result = prime * result + ((role == null) ? 0 : role.hashCode());
		result = prime * result + state;
		result = prime * result + ((surname == null) ? 0 : surname.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		User other = (User) obj;
		if (articleVersions == null) {
			if (other.articleVersions != null) {
				return false;
			}
		} else if (!articleVersions.equals(other.articleVersions)) {
			return false;
		}
		if (avatar == null) {
			if (other.avatar != null) {
				return false;
			}
		} else if (!avatar.equals(other.avatar)) {
			return false;
		}
		if (creationdate == null) {
			if (other.creationdate != null) {
				return false;
			}
		} else if (!creationdate.equals(other.creationdate)) {
			return false;
		}
		if (email == null) {
			if (other.email != null) {
				return false;
			}
		} else if (!email.equals(other.email)) {
			return false;
		}
		if (googleAcount != other.googleAcount) {
			return false;
		}
		if (idUser == null) {
			if (other.idUser != null) {
				return false;
			}
		} else if (!idUser.equals(other.idUser)) {
			return false;
		}
		if (lastLoginDate == null) {
			if (other.lastLoginDate != null) {
				return false;
			}
		} else if (!lastLoginDate.equals(other.lastLoginDate)) {
			return false;
		}
		if (loginName == null) {
			if (other.loginName != null) {
				return false;
			}
		} else if (!loginName.equals(other.loginName)) {
			return false;
		}
		if (name == null) {
			if (other.name != null) {
				return false;
			}
		} else if (!name.equals(other.name)) {
			return false;
		}
		if (password == null) {
			if (other.password != null) {
				return false;
			}
		} else if (!password.equals(other.password)) {
			return false;
		}
		if (registrationHash == null) {
			if (other.registrationHash != null) {
				return false;
			}
		} else if (!registrationHash.equals(other.registrationHash)) {
			return false;
		}
		if (role == null) {
			if (other.role != null) {
				return false;
			}
		} else if (!role.equals(other.role)) {
			return false;
		}
		if (state != other.state) {
			return false;
		}
		if (surname == null) {
			if (other.surname != null) {
				return false;
			}
		} else if (!surname.equals(other.surname)) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "User [idUser=" + idUser + ", creationdate=" + creationdate + ", lastLoginDate=" + lastLoginDate + ", loginName=" + loginName
				+ ", name=" + name + ", password=" + password + ", surname=" + surname + ", email=" + email + ", registrationHash="
				+ registrationHash + ", state=" + state + ", googleAcount=" + googleAcount + ", role=" + role + "]";
	}
}