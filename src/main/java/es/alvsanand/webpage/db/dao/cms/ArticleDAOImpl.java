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

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;

import org.compass.core.CompassHits;
import org.compass.core.CompassSearchSession;
import org.datanucleus.store.query.AbstractQueryResult;

import es.alvsanand.webpage.db.QueryBean;
import es.alvsanand.webpage.db.dao.DAOException;
import es.alvsanand.webpage.db.dao.DAOHelper;
import es.alvsanand.webpage.model.Article;
import es.alvsanand.webpage.model.ArticleState;
import es.alvsanand.webpage.model.ArticleVersion;
import es.alvsanand.webpage.model.Comment;
import es.alvsanand.webpage.model.Rating;
import es.alvsanand.webpage.model.Tag;
import es.alvsanand.webpage.model.User;

/**
 * Implementation of the <code>es.alvsanand.webpage.db.dao.cms.ArticleDAO</code>
 *
 * @author alvaro.santos
 * @date 28/07/2010
 *
 */
public class ArticleDAOImpl extends DAOHelper implements ArticleDAO{
	/**
	 * Name of the query for delete an article
	 */
	private final static String DELETE_ARTICLE_NQ = "DELETE from Article article WHERE article.idArticle = :idArticle";
	/**
	 * Name of the query for delete an article
	 */
	private final static String DELETE_ARTICLEVERSIONS_ARTICLE_NQ = "DELETE from ArticleVersion articleVersion WHERE articleVersion.idArticle = :idArticle";
	/**
	 * Name of the query for delete an article
	 */
	private final static String DELETE_TAGS_ARTICLE_NQ = "DELETE from Tag tag WHERE tag.idArticle = :idArticle";
	/**
	 * Name of the query for delete a comment of an article
	 */
	private final static String DELETE_COMMENTS_ARTICLE_NQ = "DELETE from Comment comment WHERE comment.idArticle = :idArticle";
	/**
	 * Name of the query for delete a comment
	 */
	private final static String DELETE_COMMENT_NQ = "DELETE from Comment comment WHERE comment.idComment = :idComment";
	/**
	 * Name of the query for delete an rating of an article
	 */
	private final static String DELETE_RATING_ARTICLE_NQ = "DELETE from Rating rating WHERE rating.idArticle = :idArticle";
	/**
	 * Name of the query for obtaining an article by idArticle
	 */
	private final static String FIND_ARTICLE_NQ = "SELECT article FROM Article article WHERE article.idArticle = :idArticle";
	/**
	 * Name of the query for obtaining an articleVersion by idArticleVersion
	 */
	private final static String FIND_ARTICLE_VERSION_NQ = "SELECT articleVersion FROM ArticleVersion articleVersion WHERE articleVersion.idArticleVersion = :idArticleVersion";
	/**
	 * Name of the query for selecting all articles
	 */
	private final static String FIND_ALL_ARTICLE_NQ = "SELECT article FROM Article article";
	/**
	 * Name of the query for selecting all articles by Tag
	 */
	private final static String FIND_ALL_ARTICLE_BY_TAG_NQ = "SELECT tag.idArticle FROM Tag tag WHERE tag.name = :name and tag.asociated = true";
	/**
	 * Name of the query for selecting all articles by dates
	 */
	private final static String FIND_ALL_ACTIVATED_ARTICLE_BY_DATES_NQ = "SELECT article FROM Article article WHERE article.date > :beginDate and article.date < :endDate and article.state = 1  ORDER BY date DESC";
	/**
	 * Name of the query for selecting all articles
	 */
	private final static String FIND_ALL_ACTIVATED_ARTICLE_NQ = "SELECT article FROM Article article WHERE article.state = 1";
	/**
	 * Name of the query for selecting all articles
	 */
	private final static String FIND_ALL_BASIC_ACTIVATED_ARTICLE_NQ = "SELECT article.idArticle, article.name, article.date FROM Article article WHERE article.state = 1";	
	
	/**
	 * Name of the query for selecting all articles
	 */
	private final static String FIND_ACTIVATED_ARTICLE_IN_LIST_NQ = "SELECT article FROM Article article WHERE article.state = 1 and article.idArticle in {0}";
	/**
	 * Name of the query for selecting all articles by the name
	 */
	private final static String FIND_ALL_BYNAME_ARTICLE_NQ = "SELECT article FROM Article article WHERE article.name = :name";
	/**
	 * Name of the query for obtaining the articleVersions of an article
	 */
	private final static String FIND_ARTICLE_VERSIONS_ARTICLE_NQ = "SELECT articleVersion FROM ArticleVersion articleVersion WHERE articleVersion.idArticle = :idArticle ORDER BY date DESC";
	/**
	 * Name of the query for obtaining the user of an article
	 */
	private final static String FIND_USER_ARTICLE_NQ = "SELECT user FROM User user WHERE user.idUser = :idUser";
	/**
	 * Name of the query for obtaining the comments of an article
	 */
	private final static String FIND_COMMENTS_ARTICLE_NQ = "SELECT comment FROM Comment comment WHERE comment.idArticle = :idArticle ORDER BY date ASC";
	/**
	 * Name of the query for obtaining the Tag of an article
	 */
	private final static String FIND_TAGS_ARTICLE_NQ = "SELECT tag FROM Tag tag WHERE tag.idArticle = :idArticle ORDER BY idTag DESC";
	/**
	 * Name of the query for obtaining the count of articles
	 */
	private final static String COUNT_ARTICLE_NQ = "SELECT count(article) FROM Article article";
	/**
	 * Name of the query for obtaining the count of articles
	 */
	private final static String COUNT_ACTIVATED_ARTICLE_NQ = "SELECT count(article) FROM Article article WHERE article.state = 1";
	/**
	 * Name of the query for obtaining the count of articles
	 */
	private final static String COUNT_ARTICLE_BYNAME_NQ = "SELECT count(article) FROM Article article WHERE article.name = :name";
	/**
	 * Name of the query for obtaining the Rating of an article
	 */
	private final static String FIND_RATINGS_ARTICLE_NQ = "SELECT rating FROM Rating rating WHERE rating.idArticle = :idArticle";
	/**
	 * Name of the query for obtaining the count of comments of an article
	 */
	private final static String COUNT_ARTICLE_COMMENTS_NQ = "SELECT count(comment) FROM Comment comment WHERE comment.idArticle = :idArticle";

	/* (non-Javadoc)
	 * @see es.alvsanand.webpage.db.dao.cms.ArticleDAO#getArticles(es.alvsanand.webpage.db.QueryBean)
	 */
	public List<Article> getArticles(QueryBean queryBean) throws DAOException{		
		try{			
			EntityManager entityManager = getEntityManager();
			
			Query q = getQuery(entityManager, FIND_ALL_ARTICLE_NQ, queryBean);
			Object resultObj = q.getResultList();
			
			List<Article> result = new java.util.ArrayList<Article>((List<Article>)resultObj);
			
			entityManager.close();
			
			return result;
		}
		catch(NoResultException noResultException){
			return null;
		}
		catch(Throwable e){
			throw new DAOException("Error getting Article beans: " + e.getMessage(), e);
		}
	}

	/* (non-Javadoc)
	 * @see es.alvsanand.webpage.db.dao.cms.ArticleDAO#getActivatedArticles(es.alvsanand.webpage.db.QueryBean)
	 */
	public List<Article> getActivatedArticles(QueryBean queryBean) throws DAOException{		
		try{			
			EntityManager entityManager = getEntityManager();
			
			Query q = getQuery(entityManager, FIND_ALL_ACTIVATED_ARTICLE_NQ, queryBean);
			Object resultObj = q.getResultList();
			
			List<Article> result = new java.util.ArrayList<Article>((List<Article>)resultObj);
			
			for(Article article: result){
				article.setTags(getTags(article, entityManager));
				article.setUser(getUser(article, entityManager));
				article.setCommentCount(getArticleCommentsCount(article));
			}
			
			entityManager.close();
			
			return result;
		}
		catch(NoResultException noResultException){
			return null;
		}
		catch(Throwable e){
			throw new DAOException("Error getting Article beans: " + e.getMessage(), e);
		}
	}

	/* (non-Javadoc)
	 * @see es.alvsanand.webpage.db.dao.cms.ArticleDAO#getBasicActivatedArticles(es.alvsanand.webpage.db.QueryBean)
	 */
	public List<Article> getBasicActivatedArticles(QueryBean queryBean) throws DAOException{		
		try{			
			EntityManager entityManager = getEntityManager();
			
			Query q = getQuery(entityManager, FIND_ALL_BASIC_ACTIVATED_ARTICLE_NQ, queryBean);
			Object resultObj = q.getResultList();
			
			List<Article> result = new java.util.ArrayList<Article>();
			
			Iterator<Object[]> iterator = ((AbstractQueryResult)resultObj).iterator();

            while(iterator.hasNext())
            {
            	Object[] entries = iterator.next();
            	
            	Article article = new Article();
            	
            	article.setIdArticle((String)entries[0]);
            	article.setName((String)entries[1]);
            	article.setDate((Date)entries[2]);
            	
            	result.add(article);
            }
			
			entityManager.close();
			
			return result;
		}
		catch(NoResultException noResultException){
			return null;
		}
		catch(Throwable e){
			throw new DAOException("Error getting Article beans: " + e.getMessage(), e);
		}
	}

	/* (non-Javadoc)
	 * @see es.alvsanand.webpage.db.dao.cms.ArticleDAO#getActivatedArticlesByCalendar(java.util.Calendar, int, es.alvsanand.webpage.db.QueryBean)
	 */
	public List<Article> getActivatedArticlesByDates(Date beginDate, Date endDate, QueryBean queryBean) throws DAOException{		
		try{
			if(beginDate==null || endDate==null){
				throw new IllegalArgumentException();
			}	
			EntityManager entityManager = getEntityManager();
			
			Query q = getQuery(entityManager, FIND_ALL_ACTIVATED_ARTICLE_BY_DATES_NQ, queryBean);
			q.setParameter("beginDate", beginDate);
			q.setParameter("endDate", endDate);
			Object resultObj = q.getResultList();
			
			List<Article> result = new java.util.ArrayList<Article>((List<Article>)resultObj);
			
			for(Article article: result){
				article.setTags(getTags(article, entityManager));
				article.setUser(getUser(article, entityManager));
				article.setCommentCount(getArticleCommentsCount(article));
			}
			
			entityManager.close();
			
			return result;
		}
		catch(NoResultException noResultException){
			return null;
		}
		catch(Throwable e){
			throw new DAOException("Error getting Article beans: " + e.getMessage(), e);
		}
	}

	/* (non-Javadoc)
	 * @see es.alvsanand.webpage.db.dao.cms.ArticleDAO#getActivatedArticlesByTag(es.alvsanand.webpage.model.Tag, es.alvsanand.webpage.db.QueryBean)
	 */
	public List<Article> getActivatedArticlesByTag(Tag tag, QueryBean queryBean) throws DAOException{		
		try{
			if(tag==null || tag.getName()==null){
				throw new IllegalArgumentException();
			}
			
			EntityManager entityManager = getEntityManager();
			
			Query q = getQuery(entityManager, FIND_ALL_ARTICLE_BY_TAG_NQ, null);
			q.setParameter("name", tag.getName());
			
			Object resultObj = q.getResultList();
			
			List<String> idArticles = new java.util.ArrayList<String>((List<String>)resultObj);
			
			if(idArticles==null || idArticles.size()==0){
				return null;
			}
			
			StringBuffer sB = new StringBuffer();			
			sB.append("( ");			
			for(int i=0; i<idArticles.size() && i<20; i++){
				sB.append("'" + idArticles.get(i) + "'");
				
				if(i+1<idArticles.size()){
					sB.append(" ,");
				}
			}			
			sB.append(" )");
			
			q = getQuery(entityManager, MessageFormat.format(FIND_ACTIVATED_ARTICLE_IN_LIST_NQ, new String[]{sB.toString()}), queryBean);
			
			resultObj = q.getResultList();
			
			List<Article> result = new java.util.ArrayList<Article>((List<Article>)resultObj);
			
			for(Article article: result){
				article.setTags(getTags(article, entityManager));
				article.setUser(getUser(article, entityManager));
				article.setCommentCount(getArticleCommentsCount(article));
			}
			
			entityManager.close();
			
			return result;
		}
		catch(NoResultException noResultException){
			return null;
		}
		catch(Throwable e){
			throw new DAOException("Error getting Article beans: " + e.getMessage(), e);
		}
	}

	public List<Article> searchArticles(String query) throws DAOException{		
		try{
			if(query==null){
				throw new IllegalArgumentException();
			}			
			List<Article> articles = new ArrayList<Article>();
			
			CompassSearchSession compassSearchSession = DAOHelper.getCompass().openSearchSession();
			
			CompassHits compassHits = compassSearchSession.find(query);
			
			for(int i=0; i<compassHits.length(); i++){
				if(compassHits.data(i) instanceof Article && ((Article)compassHits.data(i)).getState()==ArticleState.ENABLED.ordinal()){
					articles.add((Article)compassHits.data(i));
				}
			}
			
			compassSearchSession.close();			

			
			EntityManager entityManager = getEntityManager();
			
			for(Article article: articles){
				article.setTags(getTags(article, entityManager));
				article.setUser(getUser(article, entityManager));
				article.setCommentCount(getArticleCommentsCount(article));
			}
			
			entityManager.close();
			
			return articles;
		}
		catch(NoResultException noResultException){
			return null;
		}
		catch(Throwable e){
			throw new DAOException("Error search Article beans: " + e.getMessage(), e);
		}
	}

	/* (non-Javadoc)
	 * @see es.alvsanand.webpage.db.dao.cms.ArticleDAO#getArticle(es.alvsanand.webpage.model.Article)
	 */
	public Article getArticle(Article article) throws DAOException{
		try{
			if(article==null || article.getIdArticle()==null){
				throw new IllegalArgumentException();
			}
			EntityManager entityManager = getEntityManager();
			
			Query q = getQuery(entityManager, FIND_ARTICLE_NQ, null);
			q.setParameter("idArticle", article.getIdArticle());
			
			Object resultObj = q.getSingleResult();
			
			Article articleDest = (Article)resultObj;
			
			if(articleDest!=null){
				articleDest.setArticleVersions(getArticleVersions(articleDest, entityManager));
				articleDest.setTags(getTags(articleDest, entityManager));
				articleDest.setUser(getUser(articleDest, entityManager));
				articleDest.setComments(getComments(articleDest, entityManager));
				articleDest.setRatings(getRatings(articleDest, entityManager));
			}
			
			entityManager.close();
			
			return articleDest;
		}
		catch(NoResultException noResultException){
			return null;
		}
		catch(Throwable e){
			throw new DAOException("Error getting Article bean by Key: " + e.getMessage(), e);
		}
	}

	private Article getArticleWithoutDependencies(Article article) throws DAOException{
		try{
			if(article==null || article.getIdArticle()==null){
				throw new IllegalArgumentException();
			}
			EntityManager entityManager = getEntityManager();
			
			Query q = getQuery(entityManager, FIND_ARTICLE_NQ, null);
			q.setParameter("idArticle", article.getIdArticle());
			
			Object resultObj = q.getSingleResult();
			
			Article articleDest = (Article)resultObj;
			
			entityManager.close();
			
			return articleDest;
		}
		catch(NoResultException noResultException){
			return null;
		}
		catch(Throwable e){
			throw new DAOException("Error getting Article bean by Key: " + e.getMessage(), e);
		}
	}

	public ArticleVersion getArticleVersion(ArticleVersion articleVersion) throws DAOException{
		try{
			if(articleVersion==null || articleVersion.getIdArticleVersion()==null){
				throw new IllegalArgumentException();
			}
			EntityManager entityManager = getEntityManager();
			
			Query q = getQuery(entityManager, FIND_ARTICLE_VERSION_NQ, null);
			q.setParameter("idArticleVersion", articleVersion.getIdArticleVersion());
			
			Object resultObj = q.getSingleResult();
			
			ArticleVersion articleVersionDest = (ArticleVersion)resultObj;
			
			entityManager.close();
			
			return articleVersionDest;
		}
		catch(NoResultException noResultException){
			return null;
		}
		catch(Throwable e){
			throw new DAOException("Error getting Article bean by Key: " + e.getMessage(), e);
		}
	}

	public Article getArticleWithLastArticleVersion(Article article) throws DAOException{
		try{
			if(article==null || article.getIdArticle()==null){
				throw new IllegalArgumentException();
			}
			EntityManager entityManager = getEntityManager();
			
			Query q = getQuery(entityManager, FIND_ARTICLE_NQ, null);
			q.setParameter("idArticle", article.getIdArticle());
			
			Object resultObj = q.getSingleResult();
			
			Article articleDest = (Article)resultObj;
			
			if(articleDest!=null){
				articleDest.setArticleVersions(getLastArticleVersion(articleDest, entityManager));
			}
			
			entityManager.close();
			
			return articleDest;
		}
		catch(NoResultException noResultException){
			return null;
		}
		catch(Throwable e){
			throw new DAOException("Error getting Article bean by Key: " + e.getMessage(), e);
		}
	}

	private List<ArticleVersion> getLastArticleVersion(Article article, EntityManager entityManager) throws DAOException{
		try{
			if(article==null || article.getIdArticle()==null){
				throw new IllegalArgumentException();
			}
			Query q = getQuery(entityManager, FIND_ARTICLE_VERSIONS_ARTICLE_NQ, null);
			q.setParameter("idArticle", article.getIdArticle());
			q.setMaxResults(1);
			q.setFirstResult(0);
			
			Object resultObj = q.getResultList();
			
			List<ArticleVersion> result = new java.util.ArrayList<ArticleVersion>((List<ArticleVersion>)resultObj);
			
			return result;
		}
		catch(NoResultException noResultException){
			return null;
		}
		catch(Throwable e){
			throw new DAOException("Error getting User bean by Key: " + e.getMessage(), e);
		}
	}

	private List<ArticleVersion> getArticleVersions(Article article, EntityManager entityManager) throws DAOException{
		try{
			if(article==null || article.getIdArticle()==null){
				throw new IllegalArgumentException();
			}
			Query q = getQuery(entityManager, FIND_ARTICLE_VERSIONS_ARTICLE_NQ, null);
			q.setParameter("idArticle", article.getIdArticle());
			
			Object resultObj = q.getResultList();
			
			List<ArticleVersion> result = new java.util.ArrayList<ArticleVersion>((List<ArticleVersion>)resultObj);
			
			return result;
		}
		catch(NoResultException noResultException){
			return null;
		}
		catch(Throwable e){
			throw new DAOException("Error getting User bean by Key: " + e.getMessage(), e);
		}
	}

	private List<Tag> getTags(Article article, EntityManager entityManager) throws DAOException{
		try{
			if(article==null || article.getIdArticle()==null){
				throw new IllegalArgumentException();
			}
			Query q = getQuery(entityManager, FIND_TAGS_ARTICLE_NQ, null);
			q.setParameter("idArticle", article.getIdArticle());
			
			Object resultObj = q.getResultList();
			
			List<Tag> result = new java.util.ArrayList<Tag>((List<Tag>)resultObj);
			
			return result;
		}
		catch(NoResultException noResultException){
			return null;
		}
		catch(Throwable e){
			throw new DAOException("Error getting User bean by Key: " + e.getMessage(), e);
		}
	}

	private List<Comment> getComments(Article article, EntityManager entityManager) throws DAOException{
		try{
			if(article==null || article.getIdArticle()==null){
				throw new IllegalArgumentException();
			}
			Query q = getQuery(entityManager, FIND_COMMENTS_ARTICLE_NQ, null);
			q.setParameter("idArticle", article.getIdArticle());
			
			Object resultObj = q.getResultList();
			
			List<Comment> result = new java.util.ArrayList<Comment>((List<Comment>)resultObj);
			
			for(Comment comment: result){
				comment.setUser(getUser(comment, entityManager));
			}
			
			return result;
		}
		catch(NoResultException noResultException){
			return null;
		}
		catch(Throwable e){
			throw new DAOException("Error getting User bean by Key: " + e.getMessage(), e);
		}
	}

	private List<Rating> getRatings(Article article, EntityManager entityManager) throws DAOException{
		try{
			if(article==null || article.getIdArticle()==null){
				throw new IllegalArgumentException();
			}
			Query q = getQuery(entityManager, FIND_RATINGS_ARTICLE_NQ, null);
			q.setParameter("idArticle", article.getIdArticle());
			
			Object resultObj = q.getResultList();
			
			List<Rating> result = new java.util.ArrayList<Rating>((List<Rating>)resultObj);
			
			return result;
		}
		catch(NoResultException noResultException){
			return null;
		}
		catch(Throwable e){
			throw new DAOException("Error getting User bean by Key: " + e.getMessage(), e);
		}
	}

	private List<Comment> getLastComment(Article article, EntityManager entityManager) throws DAOException{
		try{
			if(article==null || article.getIdArticle()==null){
				throw new IllegalArgumentException();
			}
			Query q = getQuery(entityManager, FIND_COMMENTS_ARTICLE_NQ, null);
			q.setParameter("idArticle", article.getIdArticle());
			q.setMaxResults(1);
			q.setFirstResult(0);
			
			Object resultObj = q.getResultList();
			
			List<Comment> result = new java.util.ArrayList<Comment>((List<Comment>)resultObj);
			
			return result;
		}
		catch(NoResultException noResultException){
			return null;
		}
		catch(Throwable e){
			throw new DAOException("Error getting User bean by Key: " + e.getMessage(), e);
		}
	}

	private User getUser(Article article, EntityManager entityManager) throws DAOException{
		try{
			if(article==null){
				throw new IllegalArgumentException();
			}
			if(article.getUser()==null || article.getUser().getIdUser()==null){
				return null;
			}
			
			Query q = getQuery(entityManager, FIND_USER_ARTICLE_NQ, null);
			q.setParameter("idUser", article.getUser().getIdUser());
			
			Object resultObj = q.getSingleResult();
			
			return (User)resultObj;
		}
		catch(NoResultException noResultException){
			return null;
		}
		catch(Throwable e){
			throw new DAOException("Error getting User bean by Key: " + e.getMessage(), e);
		}
	}

	private User getUser(Comment comment, EntityManager entityManager) throws DAOException{
		try{
			if(comment==null || comment.getUser()==null || comment.getUser().getIdUser()==null){
				throw new IllegalArgumentException();
			}
			Query q = getQuery(entityManager, FIND_USER_ARTICLE_NQ, null);
			q.setParameter("idUser", comment.getUser().getIdUser());
			
			Object resultObj = q.getSingleResult();
			
			return (User)resultObj;
		}
		catch(NoResultException noResultException){
			return null;
		}
		catch(Throwable e){
			throw new DAOException("Error getting User bean by Key: " + e.getMessage(), e);
		}
	}

	/* (non-Javadoc)
	 * @see es.alvsanand.webpage.db.dao.cms.ArticleDAO#getArticleCount()
	 */
	public int getArticleCount() throws DAOException{		
		try{
			EntityManager entityManager = getEntityManager();
			
			Query q = getQuery(entityManager, COUNT_ARTICLE_NQ, null);

			Object resultObj = q.getSingleResult();
			
			entityManager.close();
			
			return ((Integer)resultObj).intValue();
		}
		catch(NoResultException noResultException){
			return 0;
		}
		catch(Throwable e){
			throw new DAOException("Error getting count of Article beans: " + e.getMessage(), e);
		}
	}

	public int getActivatedArticleCount() throws DAOException{		
		try{
			EntityManager entityManager = getEntityManager();
			
			Query q = getQuery(entityManager, COUNT_ACTIVATED_ARTICLE_NQ, null);

			Object resultObj = q.getSingleResult();
			
			entityManager.close();
			
			return ((Integer)resultObj).intValue();
		}
		catch(NoResultException noResultException){
			return 0;
		}
		catch(Throwable e){
			throw new DAOException("Error getting count of activated Article beans: " + e.getMessage(), e);
		}
	}

	/* (non-Javadoc)
	 * @see es.alvsanand.webpage.db.dao.cms.ArticleDAO#getArticleCountByName(java.lang.String)
	 */
	public int getArticleCountByName(String name) throws DAOException{		
		try{
			if(name==null){
				throw new IllegalArgumentException();
			}
			EntityManager entityManager = getEntityManager();
			
			Query q = getQuery(entityManager, COUNT_ARTICLE_BYNAME_NQ, null);
			q.setParameter("name", name);

			Object resultObj = q.getSingleResult();
			
			entityManager.close();
			
			return ((Integer)resultObj).intValue();
		}
		catch(NoResultException noResultException){
			return 0;
		}
		catch(Throwable e){
			throw new DAOException("Error getting count of Article beans by name: " + e.getMessage(), e);
		}
	}
	
	private int getArticleCommentsCount(Article article) throws DAOException{		
		try{
			if(article==null || article.getIdArticle()==null){
				throw new IllegalArgumentException();
			}
			EntityManager entityManager = getEntityManager();
			
			Query q = getQuery(entityManager, COUNT_ARTICLE_COMMENTS_NQ, null);
			q.setParameter("idArticle", article.getIdArticle());

			Object resultObj = q.getSingleResult();
			
			entityManager.close();
			
			return ((Integer)resultObj).intValue();
		}
		catch(NoResultException noResultException){
			return 0;
		}
		catch(Throwable e){
			throw new DAOException("Error getting count of Article comments beans by idArticle: " + e.getMessage(), e);
		}
	}

	/* (non-Javadoc)
	 * @see es.alvsanand.webpage.db.dao.cms.ArticleDAO#getArticlesByName(java.lang.String)
	 */
	public List<Article> getArticlesByName(String name) throws DAOException{
		try{
			if(name==null){
				throw new IllegalArgumentException();
			}
			EntityManager entityManager = getEntityManager();
			
			Query q = getQuery(entityManager, FIND_ALL_BYNAME_ARTICLE_NQ, null);
			q.setParameter("name", name);
			Object resultObj = q.getResultList();
			
			List<Article> result = new java.util.ArrayList<Article>((List<Article>)resultObj);
			
			entityManager.close();
			
			return result;
		}
		catch(NoResultException noResultException){
			return null;
		}
		catch(Throwable e){
			throw new DAOException("Error getting Article bean by name: " + e.getMessage(), e);
		}		
	}

	/* (non-Javadoc)
	 * @see es.alvsanand.webpage.db.dao.cms.ArticleDAO#getArticleByName(es.alvsanand.webpage.model.Article)
	 */
	public Article getArticleByName(Article article) throws DAOException{
		try{
			if(article==null || article.getName()==null){
				throw new IllegalArgumentException();
			}
			EntityManager entityManager = getEntityManager();
			
			Query q = getQuery(entityManager, FIND_ALL_BYNAME_ARTICLE_NQ, null);
			q.setParameter("name", article.getName());
			Object resultObj = q.getSingleResult();
			
			Article articleDest = (Article)resultObj;
			
			if(articleDest!=null){
				articleDest.setArticleVersions(getArticleVersions(articleDest, entityManager));
				articleDest.setTags(getTags(articleDest, entityManager));
				articleDest.setUser(getUser(articleDest, entityManager));
				articleDest.setComments(getComments(articleDest, entityManager));
				articleDest.setRatings(getRatings(articleDest, entityManager));
			}
			
			entityManager.close();
			
			return articleDest;
		}
		catch(NoResultException noResultException){
			return null;
		}
		catch(Throwable e){
			throw new DAOException("Error getting Article bean by name: " + e.getMessage(), e);
		}		
	}

	/* (non-Javadoc)
	 * @see es.alvsanand.webpage.db.dao.cms.ArticleDAO#deleteArticle(es.alvsanand.webpage.model.Article)
	 */
	public void deleteArticle(Article article) throws DAOException {
		if(article!=null && article.getIdArticle()!=null){
			EntityManager entityManager = getEntityManager();
			
			try{
				//Deleting dependencies	
				
				Query q = getQuery(entityManager, DELETE_ARTICLEVERSIONS_ARTICLE_NQ, null);
				q.setParameter("idArticle", article.getIdArticle());
				q.executeUpdate();
				
				q = getQuery(entityManager, DELETE_TAGS_ARTICLE_NQ, null);
				q.setParameter("idArticle", article.getIdArticle());
				q.executeUpdate();
				
				q = getQuery(entityManager, DELETE_COMMENTS_ARTICLE_NQ, null);
				q.setParameter("idArticle", article.getIdArticle());
				q.executeUpdate();
				
				q = getQuery(entityManager, DELETE_RATING_ARTICLE_NQ, null);
				q.setParameter("idArticle", article.getIdArticle());
				q.executeUpdate();
				
				//Deleting Article
				q = getQuery(entityManager, DELETE_ARTICLE_NQ, null);
				q.setParameter("idArticle", article.getIdArticle());
				q.executeUpdate();
			}
			catch(Throwable e){
				throw new DAOException("Error deleting Article bean: " + e.getMessage(), e);
			}
			finally{				
				entityManager.close();
			}
		}
		else{
			throw new DAOException("Error deleting Article bean: the article is null or empty");
		}
	}

	/* (non-Javadoc)
	 * @see es.alvsanand.webpage.db.dao.cms.ArticleDAO#saveOrUpdateArticle(es.alvsanand.webpage.model.Article)
	 */
	public void saveOrUpdateArticle(Article article) throws DAOException {
		if(article!=null){
			EntityManager entityManager = getEntityManager();			
			
			boolean articleSaved = false;
			try{
				
				article = entityManager.merge(article);					
				entityManager.persist(article);
				
				articleSaved = true;
				
				//Saving or updating dependencies
				if(article.getArticleVersions()!=null){
					List<ArticleVersion> articleVersions = getLastArticleVersion(article, entityManager);
					
					int lastNumberversion = 0;
					if(articleVersions!=null && articleVersions.size()>0){
						lastNumberversion = articleVersions.get(0).getNumberversion();
					}
					
					int numberversion = lastNumberversion + 1;
					for(ArticleVersion articleVersion: article.getArticleVersions()){
						articleVersion.setArticle(article);
						articleVersion.setNumberversion(numberversion);
						
						articleVersion = entityManager.merge(articleVersion);					
						entityManager.persist(articleVersion);
						
						numberversion++;
					}
				}

				if(article.getTags()!=null){
					Query q = getQuery(entityManager, DELETE_TAGS_ARTICLE_NQ, null);
					q.setParameter("idArticle", article.getIdArticle());
					q.executeUpdate();
					
					for(Tag tag: article.getTags()){						
						tag.setArticle(article);
						
						tag = entityManager.merge(tag);					
						entityManager.persist(tag);
					}
				}

				if(article.getComments()!=null){
					Query q = getQuery(entityManager, DELETE_COMMENTS_ARTICLE_NQ, null);
					q.setParameter("idArticle", article.getIdArticle());
					q.executeUpdate();
					
					for(Comment comment: article.getComments()){						
						comment.setArticle(article);
						
						comment = entityManager.merge(comment);					
						entityManager.persist(comment);
					}
				}

				if(article.getRatings()!=null){
					Query q = getQuery(entityManager, DELETE_RATING_ARTICLE_NQ, null);
					q.setParameter("idArticle", article.getIdArticle());
					q.executeUpdate();
					
					for(Rating rating: article.getRatings()){						
						rating.setArticle(article);
						
						rating = entityManager.merge(rating);					
						entityManager.persist(rating);
					}
				}
			}
			catch(Throwable e){
				if(articleSaved){
					deleteArticle(article);
				}
				
				throw new DAOException("Error saving or updating Article bean: " + e.getMessage(), e);
			}
			finally{
				entityManager.close();
			}
		}
		else{
			throw new DAOException("Error saving or updating Article bean: the article is null or empty");
		}
	}

	/* (non-Javadoc)
	 * @see es.alvsanand.webpage.db.dao.cms.ArticleDAO#saveOrUpdateArticles(java.util.List)
	 */
	public void saveOrUpdateArticles(List<Article> articleList) throws DAOException {
		//Transactions are not permitted for multiple saves
		if(articleList!=null){
			for(Article article: articleList){
				saveOrUpdateArticle(article);
			}
		}
		else{
			throw new DAOException("Error saving or updating Article bean: the article is null or empty");
		}
	}

	/* (non-Javadoc)
	 * @see es.alvsanand.webpage.db.dao.cms.ArticleDAO#deleteArticle(es.alvsanand.webpage.model.Article)
	 */
	public void deleteComment(Comment comment) throws DAOException {
		if(comment!=null && comment.getIdComment()!=null){
			EntityManager entityManager = getEntityManager();
			
			try{
				Query q = getQuery(entityManager, DELETE_COMMENT_NQ, null);
				q.setParameter("idComment", comment.getIdComment());
				q.executeUpdate();
			}
			catch(Throwable e){
				throw new DAOException("Error deleting Comment bean: " + e.getMessage(), e);
			}
			finally{				
				entityManager.close();
			}
		}
		else{
			throw new DAOException("Error deleting Comment bean: the comment is null or empty");
		}
	}

	/* (non-Javadoc)
	 * @see es.alvsanand.webpage.db.dao.cms.ArticleDAO#saveOrUpdateComment(es.alvsanand.webpage.model.Comment)
	 */
	public void saveOrUpdateComment(Comment comment) throws DAOException {
		if(comment!=null && comment.getIdUser()!=null && comment.getIdArticle()!=null){
			EntityManager entityManager = getEntityManager();			
			
			boolean commentSaved = false;
			try{				
				List<Comment> comments = getLastComment(new Article(comment.getIdArticle()), entityManager);
				
				int lastCommentNumber = 0;
				if(comments!=null && comments.size()>0){
					lastCommentNumber = comments.get(0).getCommentNumber();
				}
				
				int commentNumber = lastCommentNumber + 1;
				comment.setCommentNumber(commentNumber);
				
				comment = entityManager.merge(comment);					
				entityManager.persist(comment);
			}
			catch(Throwable e){
				if(commentSaved){
					deleteComment(comment);
				}
				
				throw new DAOException("Error saving or updating Comment bean: " + e.getMessage(), e);
			}
			finally{
				entityManager.close();
			}
		}
		else{
			throw new DAOException("Error saving or updating Comment bean: the comment is null or empty");
		}
	}
	
	/* (non-Javadoc)
	 * @see es.alvsanand.webpage.db.dao.cms.ArticleDAO#saveOrUpdateRating(es.alvsanand.webpage.model.Rating)
	 */
	public void saveOrUpdateRating(Rating rating) throws DAOException {
		if(rating!=null && rating.getIdUser()!=null && rating.getIdArticle()!=null){
			EntityManager entityManager = getEntityManager();			
			
			try{				
				rating = entityManager.merge(rating);					
				entityManager.persist(rating);				
			}
			catch(Throwable e){
				throw new DAOException("Error saving or updating Rating bean: " + e.getMessage(), e);
			}
			finally{
				entityManager.close();
			}
		}
		else{
			throw new DAOException("Error saving or updating Rating bean: the rating is null or empty");
		}
	}
}
