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
package es.alvsanand.webpage.services.admin;

import java.util.List;

import es.alvsanand.webpage.db.QueryBean;
import es.alvsanand.webpage.model.Article;
import es.alvsanand.webpage.model.ArticleVersion;
import es.alvsanand.webpage.model.Comment;
import es.alvsanand.webpage.model.Rating;
import es.alvsanand.webpage.model.Tag;
import es.alvsanand.webpage.model.bulkUpload.ArticleBulkUpload;
import es.alvsanand.webpage.services.ServiceException;

/**
 * This services realizes testing operations.
 *
 * @author alvaro.santos
 * @date 18/11/2009
 *
 */
public interface CmsAdminService {
	public void saveOrUpdateTag(Tag tag) throws ServiceException;
	public List<Tag> getTags(QueryBean queryBean) throws ServiceException;
	public Tag getTag(Tag tag) throws ServiceException;
	public void deleteTag(Tag tag) throws ServiceException;
	public int getTagCount(String name) throws ServiceException;
	public int getTagCount() throws ServiceException;
	
	public void saveOrUpdateArticle(Article article) throws ServiceException;
	public void saveOrUpdateArticles(List<Article> articleList) throws ServiceException;
	public List<Article> getArticles(QueryBean queryBean) throws ServiceException;
	public Article getArticle(Article article) throws ServiceException;
	public ArticleVersion getArticleVersion(ArticleVersion articleVersion) throws ServiceException;
	public void deleteArticle(Article article) throws ServiceException;
	public int getArticleCount(String name) throws ServiceException;
	public int getArticleCount() throws ServiceException;
	
	public void saveOrUpdateComment(Comment comment) throws ServiceException;
	public void deleteComment(Comment comment) throws ServiceException;
	
	public void saveOrUpdateRating(Rating rating) throws ServiceException;
}
