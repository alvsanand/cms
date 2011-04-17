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

import com.google.appengine.api.datastore.Blob;


/**
 * The persistent class for the AVATAR database table.
 * 
 */
@Entity
@Table(name = "Avatar")
public class Avatar implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 8506381469954635431L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Extension(vendorName = "datanucleus", key = "gae.encoded-pk",value = "true")
    @Column(columnDefinition = "idAvatar")
    private String idAvatar;

	@Column(columnDefinition = "data")
    private Blob data;

    @Temporal( TemporalType.DATE)
	@Column(columnDefinition = "date")
    private Date date;

    @Column(columnDefinition = "idUser")
	private String idUser;

    @Column(columnDefinition = "mediaType")
	private String mediaType;

    @Transient
	private User user;

    public Avatar() {
    }
	
	public String getIdAvatar() {
		return idAvatar;
	}

	public void setIdAvatar(String idAvatar) {
		this.idAvatar = idAvatar;
	}

	public Blob getData() {
		return this.data;
	}

	public void setData(Blob data) {
		this.data = data;
	}

	public Date getDate() {
		return this.date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getIdUser() {
		return idUser;
	}

	public void setIdUser(String idUser) {
		this.idUser = idUser;
	}

	public String getMediaType() {
		return mediaType;
	}

	public void setMediaType(String mediaType) {
		this.mediaType = mediaType;
	}
	
	public User getUser() {
		if(this.user==null){
			this.user = new User(this.idUser);
		}
		
		return user;
	}

	public void setUser(User user) {
		if(user!=null){
			this.idUser = user.getIdUser();
			this.user = user;
		}
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((data == null) ? 0 : data.hashCode());
		result = prime * result + ((date == null) ? 0 : date.hashCode());
		result = prime * result + ((idAvatar == null) ? 0 : idAvatar.hashCode());
		result = prime * result + ((idUser == null) ? 0 : idUser.hashCode());
		result = prime * result + ((mediaType == null) ? 0 : mediaType.hashCode());
		result = prime * result + ((user == null) ? 0 : user.hashCode());
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
		Avatar other = (Avatar) obj;
		if (data == null) {
			if (other.data != null) {
				return false;
			}
		} else if (!data.equals(other.data)) {
			return false;
		}
		if (date == null) {
			if (other.date != null) {
				return false;
			}
		} else if (!date.equals(other.date)) {
			return false;
		}
		if (idAvatar == null) {
			if (other.idAvatar != null) {
				return false;
			}
		} else if (!idAvatar.equals(other.idAvatar)) {
			return false;
		}
		if (idUser == null) {
			if (other.idUser != null) {
				return false;
			}
		} else if (!idUser.equals(other.idUser)) {
			return false;
		}
		if (mediaType == null) {
			if (other.mediaType != null) {
				return false;
			}
		} else if (!mediaType.equals(other.mediaType)) {
			return false;
		}
		if (user == null) {
			if (other.user != null) {
				return false;
			}
		} else if (!user.equals(other.user)) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "Avatar [idAvatar=" + idAvatar + ", data=" + data + ", date=" + date + ", idUser=" + idUser + ", mediaType=" + mediaType + "]";
	}
}