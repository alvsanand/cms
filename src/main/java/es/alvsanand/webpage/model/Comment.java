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

import com.google.appengine.api.datastore.Text;

import es.alvsanand.webpage.common.XMLUtils;


/**
 * The persistent class for the COMMENT database table.
 * 
 */
@Entity
@Table(name = "Comment")
public class Comment implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1522482205282183659L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Extension(vendorName = "datanucleus", key = "gae.encoded-pk",value = "true")
    @Column(columnDefinition = "idComment")
    private String idComment;

	@Column(columnDefinition = "data")
    private Text data;

    @Temporal( TemporalType.DATE)
	@Column(columnDefinition = "date")
    private Date date;

    @Column(columnDefinition = "commentNumber")
    private int commentNumber;

    @Column(columnDefinition = "title")
    private String title;

    @Column(columnDefinition = "idArticle")
	private String idArticle;

    @Column(columnDefinition = "idUser")
	private String idUser;

    @Transient
	private User user;

    public Comment() {
    }
	
	public String getIdComment() {
		return idComment;
	}

	public void setIdComment(String idComment) {
		this.idComment = idComment;
	}
	
	public String getIdArticle() {
		return (getArticle()!=null)?getArticle().getIdArticle():null;
	}

	public void setIdArticle(String idArticle) {
		this.idArticle = idArticle;
	}

	public Text getData() {
		return this.data;
	}

	public void setData(Text data) {
		this.data = data;
	}

	public Date getDate() {
		return this.date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Article getArticle() {
		return new Article(this.idArticle);
	}

	public void setArticle(Article article) {
		if(article!=null)
			this.idArticle = article.getIdArticle();
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

	public int getCommentNumber() {
		return commentNumber;
	}

	public void setCommentNumber(int commentNumber) {
		this.commentNumber = commentNumber;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((data == null) ? 0 : data.hashCode());
		result = prime * result + ((date == null) ? 0 : date.hashCode());
		result = prime * result + ((idArticle == null) ? 0 : idArticle.hashCode());
		result = prime * result + ((idComment == null) ? 0 : idComment.hashCode());
		result = prime * result + ((idUser == null) ? 0 : idUser.hashCode());
		result = prime * result + commentNumber;
		result = prime * result + ((title == null) ? 0 : title.hashCode());
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
		Comment other = (Comment) obj;
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
		if (idArticle == null) {
			if (other.idArticle != null) {
				return false;
			}
		} else if (!idArticle.equals(other.idArticle)) {
			return false;
		}
		if (idComment == null) {
			if (other.idComment != null) {
				return false;
			}
		} else if (!idComment.equals(other.idComment)) {
			return false;
		}
		if (idUser == null) {
			if (other.idUser != null) {
				return false;
			}
		} else if (!idUser.equals(other.idUser)) {
			return false;
		}
		if (commentNumber != other.commentNumber) {
			return false;
		}
		if (title == null) {
			if (other.title != null) {
				return false;
			}
		} else if (!title.equals(other.title)) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "Comment [idComment=" + idComment + ", data=" + ((data!=null && data.getValue()!=null)?data.toString():"") + ", date=" + date + ", commentNumber=" + commentNumber + ", title=" + title
				+ ", idArticle=" + idArticle + ", idUser=" + idUser + "]";
	}	

	public String getDataValue() {
		return XMLUtils.getFullArticleData(this.data);
	}

	public String getResumeDataValue() {
		return XMLUtils.getResumeArticleData(this.data);
	}
}