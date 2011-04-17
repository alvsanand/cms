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
package es.alvsanand.webpage.services.cms;


import java.io.Serializable;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.cache.Cache;
import javax.cache.CacheException;
import javax.cache.CacheFactory;
import javax.cache.CacheManager;

import es.alvsanand.webpage.common.Globals;
import es.alvsanand.webpage.common.Logger;
import es.alvsanand.webpage.db.QueryBean;
import es.alvsanand.webpage.db.QueryBean.OrderBean;
import es.alvsanand.webpage.db.dao.DAOException;
import es.alvsanand.webpage.db.dao.cms.ArticleDAO;
import es.alvsanand.webpage.db.dao.cms.ArticleDAOImpl;
import es.alvsanand.webpage.model.Article;
import es.alvsanand.webpage.model.Tag;
import es.alvsanand.webpage.services.ServiceException;
import es.alvsanand.webpage.web.beans.cms.SearchBean;

/**
 * This class implements the service Tag
 *
 * @author alvaro.santos
 * @date 18/11/2009
 *
 */
public class SearchCmsServiceImpl implements SearchCmsService{
	
	private final static Logger logger = new Logger(SearchCmsServiceImpl.class);
	
	private ArticleDAO articleDAO = new ArticleDAOImpl();

	/**
	 * Return the value of the field articleDAO
	 *
	 * @return the articleDAO
	 */
	public ArticleDAO getArticleDAO() {
		return articleDAO;
	}

	/**
	 * Set the value of the field articleDAO
	 *
	 * @param articleDAO the articleDAO to set
	 */
	public void setArticleDAO(ArticleDAO articleDAO) {
		this.articleDAO = articleDAO;
	}

	/* (non-Javadoc)
	 * @see es.alvsanand.webpage.services.cms.SearchCmsService#getArticlesByTag(es.alvsanand.webpage.model.Tag)
	 */
	public List<Article> getArticlesByTag(Tag tag) throws ServiceException{
		try {
			logger.debug("Getting activated articles[Tag=" + tag +"]");
			
			List<Article> articles = getActivatedArticlesByTagFromCache(tag);
			if(articles==null){
				QueryBean queryBean = new QueryBean();

				List<OrderBean> orderBeans = new java.util.ArrayList<OrderBean>();
				for(String order: SearchBean.DEFAULT_SORT_FIELDS){				
					OrderBean orderBean = new OrderBean(false, order);				
					orderBeans.add(orderBean);
				}
				queryBean.setOrderBeans(orderBeans);
				
				
				articles = articleDAO.getActivatedArticlesByTag(tag, queryBean);
				
				putActivatedArticlesByTagFromCache(articles, tag);
			}
			
			return articles;
			
		} catch (DAOException e) {
			logger.error("Error getting articles by Tag",e);
			
			throw new ServiceException(e);
		}
	}
	
	/* (non-Javadoc)
	 * @see es.alvsanand.webpage.services.cms.SearchCmsService#searchArticles(java.lang.String)
	 */
	public List<Article> searchArticles(String query) throws ServiceException{
		try {
			logger.debug("Searching articles query[query=" + query +"]");
			
			List<Article> articles = getActivatedArticlesBySearchFromCache(query);
			if(articles==null){
				articles = articleDAO.searchArticles(query);

				Comparator<Article> comparator = Collections.reverseOrder();

				Collections.sort(articles, comparator);
				
				putActivatedArticlesBySearchFromCache(articles, query);
			}
			
			return articles;
			
		} catch (Exception e) {
			logger.error("Error getting articles by Search",e);
			
			throw new ServiceException(e);
		}
	}
	
	/* (non-Javadoc)
	 * @see es.alvsanand.webpage.services.cms.SearchCmsService#getArticlesByCalendar(java.util.Date, java.util.Date)
	 */
	public List<Article> getArticlesByDates(Date beginDate, Date endDate) throws ServiceException{
		try {
			Dates dates = new Dates();
			
			dates.setBeginDate(beginDate);
			dates.setEndDate(endDate);
			
			logger.debug("Getting activated articles[beginDate=" + beginDate +",endDate=" + endDate + "]");
			
			List<Article> articles = getActivatedArticlesByDatesFromCache(dates);
			if(articles==null){
				QueryBean queryBean = new QueryBean();				
				
				articles = articleDAO.getActivatedArticlesByDates(beginDate, endDate, queryBean);
				
				putActivatedArticlesByDatesFromCache(articles, dates);
			}
			
			return articles;
			
		} catch (DAOException e) {
			logger.error("Error getting articles by Calendar",e);
			
			throw new ServiceException(e);
		}
	}
	
	private List<Article> getActivatedArticlesByTagFromCache(Tag tag){
		Cache cache = null;

		try {
			CacheFactory cacheFactory = CacheManager.getInstance().getCacheFactory();
			cache = cacheFactory.createCache(Collections.emptyMap());
			
			Map<String,List<Article>> articlesMap = (Map<String,List<Article>>)cache.get(Globals.ACTIVATED_ARTICLES_BY_TAG_CACHE_NAME);
			
			if(articlesMap==null)
				return null;
			
			
			List<Article> articles = articlesMap.get(tag.getName());
			
			if(articles==null){				
				return null;
			}
			else{
				logger.debug("Found activated Articles List by Tag from Cache");
				
				return articles;
			}
		} catch (CacheException cacheException) {
			logger.error("Error in getting activated Articles List by Tag from cache.",cacheException);
			
			return null;
		}
		catch(Exception exception){
			if(cache!=null){
				cache.remove(Globals.ACTIVATED_ARTICLES_BY_TAG_CACHE_NAME);
			}
			return null;
		}
	}
	
	private void putActivatedArticlesByTagFromCache(List<Article> articles, Tag tag){
		Cache cache = null;

		try {
			CacheFactory cacheFactory = CacheManager.getInstance().getCacheFactory();
			cache = cacheFactory.createCache(Collections.emptyMap());
			
			Map<String,List<Article>> articlesMap = (Map<String,List<Article>>)cache.get(Globals.ACTIVATED_ARTICLES_BY_TAG_CACHE_NAME);
			
			if(articlesMap==null){
				articlesMap = new HashMap<String,List<Article>>();
			}
			
			articlesMap.put(tag.getName(), articles);

			cache.put(Globals.ACTIVATED_ARTICLES_BY_TAG_CACHE_NAME, articlesMap);
			
			logger.debug("Saved activated articles by Tag to cache");
		} catch (CacheException cacheException) {
			logger.error("Error in putting activated Articles List by Tag from cache.",cacheException);
		}
		catch(Exception exception){
			logger.error("Error in putting activated Articles List by Tag from cache.",exception);
			if(cache!=null){
				cache.remove(Globals.ACTIVATED_ARTICLES_BY_TAG_CACHE_NAME);
			}
		}
	}
	
	private List<Article> getActivatedArticlesBySearchFromCache(String query){
		Cache cache = null;

		try {
			CacheFactory cacheFactory = CacheManager.getInstance().getCacheFactory();
			cache = cacheFactory.createCache(Collections.emptyMap());
			
			Map<String,List<Article>> articlesMap = (Map<String,List<Article>>)cache.get(Globals.ACTIVATED_ARTICLES_BY_TAG_CACHE_NAME);
			
			if(articlesMap==null)
				return null;
			
			List<Article> articles = articlesMap.get(query);
			
			if(articles==null){				
				return null;
			}
			else{
				logger.debug("Found activated Articles List by Search from Cache");
				
				return articles;
			}
		} catch (CacheException cacheException) {
			logger.error("Error in getting activated Articles List by Search from cache.",cacheException);
			
			return null;
		}
		catch(Exception exception){
			logger.error("Error in getting activated Articles List by Search from cache.",exception);
			
			if(cache!=null){
				cache.remove(Globals.ACTIVATED_ARTICLES_BY_TAG_CACHE_NAME);
			}
			return null;
		}
	}
	
	private void putActivatedArticlesBySearchFromCache(List<Article> articles, String query){
		Cache cache = null;

		try {
			CacheFactory cacheFactory = CacheManager.getInstance().getCacheFactory();
			cache = cacheFactory.createCache(Collections.emptyMap());
			
			Map<String,List<Article>> articlesMap = (Map<String,List<Article>>)cache.get(Globals.ACTIVATED_ARTICLES_BY_TAG_CACHE_NAME);
			
			if(articlesMap==null){
				articlesMap = new HashMap<String,List<Article>>();
			}
			
			articlesMap.put(query, articles);

			cache.put(Globals.ACTIVATED_ARTICLES_BY_TAG_CACHE_NAME, articlesMap);
			
			logger.debug("Saved activated articles by Search to cache");
		} catch (CacheException cacheException) {
			logger.error("Error in putting Articles List by Search from cache.",cacheException);
		}
		catch(Exception exception){
			logger.error("Error in putting Articles List by Search from cache.",exception);
			if(cache!=null){
				cache.remove(Globals.ACTIVATED_ARTICLES_BY_TAG_CACHE_NAME);
			}
		}
	}
	
	private List<Article> getActivatedArticlesByDatesFromCache(Dates dates){
		Cache cache = null;

		try {
			CacheFactory cacheFactory = CacheManager.getInstance().getCacheFactory();
			cache = cacheFactory.createCache(Collections.emptyMap());
			
			Map<Dates,List<Article>> articlesMap = (Map<Dates,List<Article>>)cache.get(Globals.ACTIVATED_ARTICLES_BY_DATES_CACHE_NAME);
			
			if(articlesMap==null)
				return null;
			
			
			List<Article> articles = articlesMap.get(dates);
			
			if(articles==null){				
				return null;
			}
			else{
				logger.debug("Found activated Articles List by Dates from Cache");
				
				return articles;
			}
		} catch (CacheException cacheException) {
			logger.error("Error in getting activated Articles List by Dates from cache.",cacheException);
			
			return null;
		}
		catch(Exception exception){
			if(cache!=null){
				cache.remove(Globals.ACTIVATED_ARTICLES_BY_DATES_CACHE_NAME);
			}
			return null;
		}
	}
	
	private void putActivatedArticlesByDatesFromCache(List<Article> articles, Dates dates){
		Cache cache = null;

		try {
			CacheFactory cacheFactory = CacheManager.getInstance().getCacheFactory();
			cache = cacheFactory.createCache(Collections.emptyMap());
			
			Map<Dates,List<Article>> articlesMap = (Map<Dates,List<Article>>)cache.get(Globals.ACTIVATED_ARTICLES_BY_DATES_CACHE_NAME);
			
			if(articlesMap==null){
				articlesMap = new HashMap<Dates,List<Article>>();
			}
			
			articlesMap.put(dates, articles);

			cache.put(Globals.ACTIVATED_ARTICLES_BY_DATES_CACHE_NAME, articlesMap);
			
			logger.debug("Saved activated articles by Dates to cache");
		} catch (CacheException cacheException) {
			logger.error("Error in putting activated Articles List by Dates from cache.",cacheException);
		}
		catch(Exception exception){
			logger.error("Error in putting activated Articles List by Dates from cache.",exception);
			if(cache!=null){
				cache.remove(Globals.ACTIVATED_ARTICLES_BY_DATES_CACHE_NAME);
			}
		}
	}
	
	private static class Dates implements Serializable{
		/**
		 * 
		 */
		private static final long serialVersionUID = -7403934164545146320L;
		
		private Date beginDate;
		private Date endDate;
		
		public Date getBeginDate() {
			return beginDate;
		}
		public void setBeginDate(Date beginDate) {
			this.beginDate = beginDate;
		}
		public Date getEndDate() {
			return endDate;
		}
		public void setEndDate(Date endDate) {
			this.endDate = endDate;
		}
		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + ((beginDate == null) ? 0 : beginDate.hashCode());
			result = prime * result + ((endDate == null) ? 0 : endDate.hashCode());
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
			Dates other = (Dates) obj;
			if (beginDate == null) {
				if (other.beginDate != null) {
					return false;
				}
			} else if (!beginDate.equals(other.beginDate)) {
				return false;
			}
			if (endDate == null) {
				if (other.endDate != null) {
					return false;
				}
			} else if (!endDate.equals(other.endDate)) {
				return false;
			}
			return true;
		}
		@Override
		public String toString() {
			return "Dates [beginDate=" + beginDate + ", endDate=" + endDate + "]";
		}
	}
}
