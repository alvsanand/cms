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
package es.alvsanand.webpage.web.beans.admin;

import java.io.Serializable;
import java.util.Date;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import com.google.appengine.api.datastore.Text;

import es.alvsanand.webpage.AlvsanandException;
import es.alvsanand.webpage.common.Globals;
import es.alvsanand.webpage.common.Logger;
import es.alvsanand.webpage.model.Article;
import es.alvsanand.webpage.model.Comment;
import es.alvsanand.webpage.model.User;
import es.alvsanand.webpage.services.admin.CmsAdminService;
import es.alvsanand.webpage.services.admin.CmsAdminServiceImpl;

@SessionScoped
@ManagedBean(name="commentBean")
public class CommentBean implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 4606319332673219295L;

	private transient static final Logger logger = new Logger(CommentBean.class);
	
	private transient static final String VIEW_ARTICLE_VIEW_ID = "pretty:viewArticle";

	private transient CmsAdminService cmsAdminService;

	private String idComment;

	private String idArticle;

	private String title;

	private String data;

	private Date date;

	public CommentBean(){
	}

	public String getIdComment() {
		return idComment;
	}

	public void setIdComment(String idComment) {
		this.idComment = idComment;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getTitle() {
		return title;
	}

	public void setData(String data) {
		this.data = data;
	}

	public String getData() {
		return data;
	}

	public String getIdArticle() {
		return idArticle;
	}

	public void setIdArticle(String idArticle) {
		this.idArticle = idArticle;
	}

	public CmsAdminService getCmsAdminService() {
		if(cmsAdminService==null){
			cmsAdminService = new CmsAdminServiceImpl();
		}
		return cmsAdminService;
	}

	// JSF methods

	public String saveComment() throws AlvsanandException {
		logger.info("Launched CommentBean.saveComment[article=" + idArticle + "]");
		
		User actualUser = (User)FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get(Globals.SES_USER);
		
		Comment comment = new Comment();
		
		comment.setArticle(new Article(idArticle));
		comment.setData(new Text(data));
		comment.setDate(new Date());
		comment.setTitle(title);
		comment.setUser(actualUser);
		
		getCmsAdminService().saveOrUpdateComment(comment);
		
		this.idComment = null;
		this.idArticle = null;
		this.title = null;
		this.data = null;
		this.date = null;
		
		return VIEW_ARTICLE_VIEW_ID;
	}

	public String deleteComment() throws AlvsanandException {
		logger.info("Launched CommentBean.deleteComment[" + idComment + "]");
		
		Comment comment = new Comment();
		comment.setIdComment(idComment);
		comment.setArticle(new Article(idArticle));

		getCmsAdminService().deleteComment(comment);

		return VIEW_ARTICLE_VIEW_ID;
	}
}
