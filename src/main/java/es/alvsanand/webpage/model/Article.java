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

import org.compass.annotations.Searchable;
import org.compass.annotations.SearchableId;
import org.compass.annotations.SearchableProperty;
import org.datanucleus.jpa.annotations.Extension;

import com.google.appengine.api.datastore.Text;

import es.alvsanand.webpage.common.XMLUtils;

/**
 * The persistent class for the ARTICLE database table.
 * 
 */
@Entity
@Table(name = "Article")
@Searchable
public class Article implements Serializable, Comparable<Article> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1069916118993466847L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Extension(vendorName = "datanucleus", key = "gae.encoded-pk", value = "true")
	@Column(columnDefinition = "idArticle")
	@SearchableId
	private String idArticle;

	@Temporal(TemporalType.DATE)
	@Column(columnDefinition = "date")
	@SearchableProperty
    private Date date;

	@Temporal(TemporalType.DATE)
	@Column(columnDefinition = "lastModifieddate")
	private Date modifiedDate;

	@Column(columnDefinition = "name")
	private String name;

	@Column(columnDefinition = "state")
	@SearchableProperty
	private int state;

	@Column(columnDefinition = "data")
	@SearchableProperty(converter="textConverter")
    private Text data;

    @Column(columnDefinition = "title")
    @SearchableProperty
    private String title;

    @Column(columnDefinition = "idUser")
	private String idUser;

    @Transient
	private int commentCount;

    @Transient
	private User user;

	@Transient
	private List<ArticleVersion> articleVersions = new java.util.ArrayList<ArticleVersion>();

	@Transient
	private List<Comment> comments = new java.util.ArrayList<Comment>();

	@Transient
	private List<Tag> tags = new java.util.ArrayList<Tag>();

	@Transient
	private List<Rating> ratings = new java.util.ArrayList<Rating>();

	public Article() {
	}

	public Article(String idArticle) {
		this.idArticle = idArticle;
	}

	public String getIdArticle() {
		return this.idArticle;
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

	public Date getModifiedDate() {
		return modifiedDate;
	}

	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Text getData() {
		return this.data;
	}

	public void setData(Text data) {
		this.data = data;
	}

	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
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

	public List<ArticleVersion> getArticleVersions() {
		return this.articleVersions;
	}

	public void setArticleVersions(List<ArticleVersion> articleVersions) {
		this.articleVersions = articleVersions;
	}

	public List<Tag> getTags() {
		return this.tags;
	}

	public void setTags(List<Tag> tags) {
		this.tags = tags;
	}

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}

	public List<Comment> getComments() {
		return comments;
	}

	public void setComments(List<Comment> comments) {
		this.comments = comments;
	}

	public List<Rating> getRatings() {
		return this.ratings;
	}

	public void setRatings(List<Rating> ratings) {
		this.ratings = ratings;
	}
	
	public boolean getCanBeDisabled(){
		ArticleState articleState = ArticleState.getArticleState(state);
		ArticleState[] possibleArticleState = articleState.getPossibleArticleState();
		
		for(ArticleState state: possibleArticleState){
			if(ArticleState.DISABLED.equals(state)){
				return true;
			}	
		}
		
		return false;
	}
	
	public boolean getCanBeEnabled(){
		ArticleState articleState = ArticleState.getArticleState(state);
		ArticleState[] possibleArticleState = articleState.getPossibleArticleState();
		
		for(ArticleState state: possibleArticleState){
			if(ArticleState.ENABLED.equals(state)){
				return true;
			}	
		}
		
		return false;
	}

	public String getDataValue() {
		return XMLUtils.getFullArticleData(this.data);
	}

	public String getResumeDataValue() {
		return XMLUtils.getResumeArticleData(this.data);
	}

	@Override
	public int compareTo(Article article) {
		if(article==null){
			return 1;
		}
		if(article.getDate()==null && this.getDate()!=null){
			return 1;
		}
		if(article.getDate()!=null && this.getDate()==null){
			return -1;
		}
		if(article.getDate()!=null && this.getDate()!=null && article.getDate().compareTo(this.getDate())!=0){
			return this.getDate().compareTo(article.getDate());
		}
		if(article.getIdArticle()==null && this.getIdArticle()!=null){
			return 1;
		}
		if(article.getIdArticle()!=null && this.getIdArticle()==null){
			return -1;
		}
		if(article.getIdArticle()!=null && this.getIdArticle()!=null && article.getIdArticle().compareTo(this.getIdArticle())!=0){
			return this.getIdArticle().compareTo(article.getIdArticle());
		}
			
		return 0;
	}
	
	public Double getRatingMean(){
		if(ratings!=null && ratings.size()>0){
			double ratingMean = 0.0;
			
			for(Rating rating: ratings){
				ratingMean += rating.getRatingNumber();
			}
			ratingMean = ratingMean / ratings.size();
			
			return ratingMean;
		}
		else{
			return 0.0;
		}
	}
	
	public void setRatingMean(Double ratingMean){
	}

	public int getCommentCount() {
		return commentCount;
	}

	public void setCommentCount(int commentCount) {
		this.commentCount = commentCount;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((articleVersions == null) ? 0 : articleVersions.hashCode());
		result = prime * result + commentCount;
		result = prime * result + ((comments == null) ? 0 : comments.hashCode());
		result = prime * result + ((data == null) ? 0 : data.hashCode());
		result = prime * result + ((date == null) ? 0 : date.hashCode());
		result = prime * result + ((idArticle == null) ? 0 : idArticle.hashCode());
		result = prime * result + ((idUser == null) ? 0 : idUser.hashCode());
		result = prime * result + ((modifiedDate == null) ? 0 : modifiedDate.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((ratings == null) ? 0 : ratings.hashCode());
		result = prime * result + state;
		result = prime * result + ((tags == null) ? 0 : tags.hashCode());
		result = prime * result + ((title == null) ? 0 : title.hashCode());
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
		Article other = (Article) obj;
		if (articleVersions == null) {
			if (other.articleVersions != null) {
				return false;
			}
		} else if (!articleVersions.equals(other.articleVersions)) {
			return false;
		}
		if (commentCount != other.commentCount) {
			return false;
		}
		if (comments == null) {
			if (other.comments != null) {
				return false;
			}
		} else if (!comments.equals(other.comments)) {
			return false;
		}
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
		if (idUser == null) {
			if (other.idUser != null) {
				return false;
			}
		} else if (!idUser.equals(other.idUser)) {
			return false;
		}
		if (modifiedDate == null) {
			if (other.modifiedDate != null) {
				return false;
			}
		} else if (!modifiedDate.equals(other.modifiedDate)) {
			return false;
		}
		if (name == null) {
			if (other.name != null) {
				return false;
			}
		} else if (!name.equals(other.name)) {
			return false;
		}
		if (ratings == null) {
			if (other.ratings != null) {
				return false;
			}
		} else if (!ratings.equals(other.ratings)) {
			return false;
		}
		if (state != other.state) {
			return false;
		}
		if (tags == null) {
			if (other.tags != null) {
				return false;
			}
		} else if (!tags.equals(other.tags)) {
			return false;
		}
		if (title == null) {
			if (other.title != null) {
				return false;
			}
		} else if (!title.equals(other.title)) {
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
		return "Article [idArticle=" + idArticle + ", date=" + date + ", modifiedDate=" + modifiedDate + ", name=" + name + ", state=" + state
				+ ", data=" + data + ", title=" + title + ", idUser=" + idUser + ", commentCount=" + commentCount + ", user=" + user
				+ ", articleVersions=" + articleVersions + ", comments=" + comments + ", tags=" + tags + ", ratings=" + ratings + "]";
	}
}