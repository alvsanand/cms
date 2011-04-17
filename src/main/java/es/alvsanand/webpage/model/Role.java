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

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.datanucleus.jpa.annotations.Extension;


/**
 * The persistent class for the ARTICLEVERSION database table.
 * 
 */
@Entity
@Table(name = "Role")
public class Role implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3876798382814700836L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Extension(vendorName = "datanucleus", key = "gae.encoded-pk",value = "true")
    @Column(columnDefinition = "idRole")
    private String idRole;

	@Column(columnDefinition = "name")
    private String name;

	@Column(columnDefinition = "idUser")
	private String idUser;

    public Role() {
    }
	
	public String getIdRole() {
		return idRole;
	}

	public void setIdRole(String idRole) {
		this.idRole = idRole;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public User getUser() {
		return new User(this.idUser);
	}

	public void setUser(User user) {
		if(user!=null)
			this.idUser = user.getIdUser();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((idRole == null) ? 0 : idRole.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((idUser == null) ? 0 : idUser.hashCode());
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
		Role other = (Role) obj;
		if (idRole == null) {
			if (other.idRole != null) {
				return false;
			}
		} else if (!idRole.equals(other.idRole)) {
			return false;
		}
		if (name == null) {
			if (other.name != null) {
				return false;
			}
		} else if (!name.equals(other.name)) {
			return false;
		}
		if (idUser == null) {
			if (other.idUser != null) {
				return false;
			}
		} else if (!idUser.equals(other.idUser)) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "Role [idRole=" + idRole + ", name=" + name + ", idUser=" + idUser + "]";
	}
	
}