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


/**
 * The persistent class for the COMMENT database table.
 * 
 */
@Entity
@Table(name = "Rating")
public class Rating implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -5105311399429604769L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Extension(vendorName = "datanucleus", key = "gae.encoded-pk",value = "true")
    @Column(columnDefinition = "idRating")
    private String idRating;

    @Temporal( TemporalType.DATE)
	@Column(columnDefinition = "date")
    private Date date;

    @Column(columnDefinition = "ratingNumber")
    private double ratingNumber;

    @Column(columnDefinition = "idArticle")
	private String idArticle;

    @Column(columnDefinition = "idUser")
	private String idUser;

    @Transient
	private User user;

    @Transient
	private Article article;

    public Rating() {
    }
	
	public String getIdRating() {
		return idRating;
	}

	public void setIdRating(String idRating) {
		this.idRating = idRating;
	}
	
	public String getIdArticle() {
		return (getArticle()!=null)?getArticle().getIdArticle():null;
	}

	public void setIdArticle(String idArticle) {
		this.idArticle = idArticle;
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
	
	public Article getArticle() {
		if(this.article==null){
			this.article = new Article(this.idArticle);
		}
		
		return article;
	}

	public void setArticle(Article article) {
		if(article!=null){
			this.idArticle = article.getIdArticle();
			this.article = article;
		}
	}

	public double getRatingNumber() {
		return ratingNumber;
	}

	public void setRatingNumber(double ratingNumber) {
		this.ratingNumber = ratingNumber;
	}

	@Override
	public String toString() {
		return "Rating [idRating=" + idRating + ", date=" + date + ", ratingNumber=" + ratingNumber + ", idArticle=" + idArticle + ", idUser="
				+ idUser + ", user=" + user+ "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((article == null) ? 0 : article.hashCode());
		result = prime * result + ((date == null) ? 0 : date.hashCode());
		result = prime * result + ((idArticle == null) ? 0 : idArticle.hashCode());
		result = prime * result + ((idRating == null) ? 0 : idRating.hashCode());
		result = prime * result + ((idUser == null) ? 0 : idUser.hashCode());
		long temp;
		temp = Double.doubleToLongBits(ratingNumber);
		result = prime * result + (int) (temp ^ (temp >>> 32));
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
		Rating other = (Rating) obj;
		if (article == null) {
			if (other.article != null) {
				return false;
			}
		} else if (!article.equals(other.article)) {
			return false;
		}
		if (date == null) {
			if (other.date != null) {
				return false;
			}
		} else if (!date.equals(other.date)) {
			return false;
		}
		if (idArticle == null) {
			if (other.idArticle != null) {
				return false;
			}
		} else if (!idArticle.equals(other.idArticle)) {
			return false;
		}
		if (idRating == null) {
			if (other.idRating != null) {
				return false;
			}
		} else if (!idRating.equals(other.idRating)) {
			return false;
		}
		if (idUser == null) {
			if (other.idUser != null) {
				return false;
			}
		} else if (!idUser.equals(other.idUser)) {
			return false;
		}
		if (Double.doubleToLongBits(ratingNumber) != Double.doubleToLongBits(other.ratingNumber)) {
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
}