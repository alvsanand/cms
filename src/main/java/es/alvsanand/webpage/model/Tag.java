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
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.datanucleus.jpa.annotations.Extension;

import es.alvsanand.webpage.common.Globals;


@Entity
@Table(name = "Tag")
public class Tag implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 7574490457786175104L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Extension(vendorName = "datanucleus", key = "gae.encoded-pk",value = "true")
    @Column(columnDefinition = "idTag")
    private String idTag;

	@Column(columnDefinition = "name")
    private String name;

	@Column(columnDefinition = "description")
    private String description;

	@Column(columnDefinition = "asociated")
    private boolean asociated;

	@Column(columnDefinition = "idArticle")
	private String idArticle;

    public Tag() {
    }
    
	public Tag(String name, String description) {
		this.name = name;
		this.description = description;
	}
	
	public String getIdTag() {
		return idTag;
	}

	public void setIdTag(String idTag) {
		this.idTag = idTag;
	}

	public String getName() {
		return this.name;
	}

	public String getEncodedName() throws UnsupportedEncodingException {
		return URLEncoder.encode(this.name, Globals.CHARACTER_ENCODING);
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setEncodedName(String encodedName) throws UnsupportedEncodingException {
		this.name = URLDecoder.decode(encodedName, Globals.CHARACTER_ENCODING);
	}

	public Article getArticle() {
		return new Article(this.idArticle);
	}

	public void setArticle(Article article) {
		if(article!=null)
			this.idArticle = article.getIdArticle();
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public boolean isAsociated() {
		return asociated;
	}

	public void setAsociated(boolean asociated) {
		this.asociated = asociated;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (asociated ? 1231 : 1237);
		result = prime * result + ((description == null) ? 0 : description.hashCode());
		result = prime * result + ((idArticle == null) ? 0 : idArticle.hashCode());
		result = prime * result + ((idTag == null) ? 0 : idTag.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
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
		Tag other = (Tag) obj;
		if (asociated != other.asociated) {
			return false;
		}
		if (description == null) {
			if (other.description != null) {
				return false;
			}
		} else if (!description.equals(other.description)) {
			return false;
		}
		if (idArticle == null) {
			if (other.idArticle != null) {
				return false;
			}
		} else if (!idArticle.equals(other.idArticle)) {
			return false;
		}
		if (idTag == null) {
			if (other.idTag != null) {
				return false;
			}
		} else if (!idTag.equals(other.idTag)) {
			return false;
		}
		if (name == null) {
			if (other.name != null) {
				return false;
			}
		} else if (!name.equals(other.name)) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "Tag [idTag=" + idTag + ", name=" + name + ", description=" + description + ", asociated=" + asociated + ", idArticle=" + idArticle
				+ "]";
	}	
}