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
package es.alvsanand.webpage.db.dao.cms;

import java.util.Date;
import java.util.List;

import es.alvsanand.webpage.db.QueryBean;
import es.alvsanand.webpage.db.dao.DAOException;
import es.alvsanand.webpage.model.Article;
import es.alvsanand.webpage.model.ArticleVersion;
import es.alvsanand.webpage.model.Comment;
import es.alvsanand.webpage.model.Rating;
import es.alvsanand.webpage.model.Tag;

/**
 * Service for retrieving Articles from DB
 *
 * @author alvaro.santos
 * @date 20/11/2009
 *
 */
public interface ArticleDAO {        
    public List<Article> getArticles(QueryBean queryBean) throws DAOException;
    public List<Article> getActivatedArticles(QueryBean queryBean) throws DAOException;
    public List<Article> getBasicActivatedArticles(QueryBean queryBean) throws DAOException;
    public List<Article> getActivatedArticlesByTag(Tag tag, QueryBean queryBean) throws DAOException;
    public List<Article> getActivatedArticlesByDates(Date beginDate, Date endDate, QueryBean queryBean) throws DAOException;
    public Article getArticle(Article article) throws DAOException;
    public ArticleVersion getArticleVersion(ArticleVersion articleVersion) throws DAOException;
    public Article getArticleWithLastArticleVersion(Article article) throws DAOException;
    public List<Article> getArticlesByName(String name) throws DAOException;
    public Article getArticleByName(Article article) throws DAOException;
    public int getArticleCount() throws DAOException;
    public int getActivatedArticleCount() throws DAOException;
    public int getArticleCountByName(String name) throws DAOException;
    public void saveOrUpdateArticle(Article article) throws DAOException;
    public void deleteArticle(Article article) throws DAOException;
    public void saveOrUpdateArticles(List<Article> articleList) throws DAOException;
    
    public void saveOrUpdateComment(Comment comment) throws DAOException;
    public void deleteComment(Comment comment) throws DAOException;
    
    public List<Article> searchArticles(String query) throws DAOException;
    
    public void saveOrUpdateRating(Rating rating) throws DAOException;
}
