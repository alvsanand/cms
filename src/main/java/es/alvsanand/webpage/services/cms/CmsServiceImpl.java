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



import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.cache.Cache;
import javax.cache.CacheException;
import javax.cache.CacheFactory;
import javax.cache.CacheManager;

import org.apache.commons.lang.StringUtils;

import es.alvsanand.webpage.common.Globals;
import es.alvsanand.webpage.common.Logger;
import es.alvsanand.webpage.common.photo.Album;
import es.alvsanand.webpage.common.photo.Photo;
import es.alvsanand.webpage.db.QueryBean;
import es.alvsanand.webpage.db.QueryBean.OrderBean;
import es.alvsanand.webpage.db.dao.DAOException;
import es.alvsanand.webpage.db.dao.admin.AvatarDAO;
import es.alvsanand.webpage.db.dao.admin.AvatarDAOImpl;
import es.alvsanand.webpage.db.dao.cms.ArticleDAO;
import es.alvsanand.webpage.db.dao.cms.ArticleDAOImpl;
import es.alvsanand.webpage.db.dao.cms.TagDAO;
import es.alvsanand.webpage.db.dao.cms.TagDAOImpl;
import es.alvsanand.webpage.model.Article;
import es.alvsanand.webpage.model.Avatar;
import es.alvsanand.webpage.model.Tag;
import es.alvsanand.webpage.model.User;
import es.alvsanand.webpage.services.ServiceException;
import es.alvsanand.webpage.services.admin.ImageAdminService;
import es.alvsanand.webpage.services.admin.ImageAdminServiceImpl;
import es.alvsanand.webpage.web.beans.cms.HomeBean;

/**
 * This class implements the service Tag
 *
 * @author alvaro.santos
 * @date 18/11/2009
 *
 */
public class CmsServiceImpl implements CmsService{
	
	private final static Logger logger = new Logger(CmsServiceImpl.class);
	
	private ArticleDAO articleDAO = new ArticleDAOImpl();
	
	private TagDAO tagDAO = new TagDAOImpl();
	
	private AvatarDAO avatarDAO = new AvatarDAOImpl();
	
	private ImageAdminService imageAdminService = new ImageAdminServiceImpl();

	/* (non-Javadoc)
	 * @see es.alvsanand.webpage.services.cms.CmsService#getArticles(int)
	 */
	public List<Article> getArticles(int offset) throws ServiceException{
		try {
			logger.debug("Getting activated articles[offset=" + offset +"]");
			
			List<Article> articles = getActivatedArticlesFromCache(offset);
			if(articles==null){				
				QueryBean queryBean = new QueryBean();
				queryBean.setLimit(HomeBean.ARTICLE_PER_PAGE);
				queryBean.setOffset(offset);
				
				List<OrderBean> orderBeans = new java.util.ArrayList<OrderBean>();
				for(String order: HomeBean.DEFAULT_SORT_FIELDS){				
					OrderBean orderBean = new OrderBean(false, order);				
					orderBeans.add(orderBean);
				}
				queryBean.setOrderBeans(orderBeans);
				
				articles = articleDAO.getActivatedArticles(queryBean);
				
				putActivatedArticlesFromCache(articles, offset);
			}
			
			return articles;
			
		} catch (DAOException e) {
			logger.error("Error getting activated articles",e);
			
			throw new ServiceException(e);
		}
	}

	/* (non-Javadoc)
	 * @see es.alvsanand.webpage.services.cms.CmsService#getArticle(es.alvsanand.webpage.model.Article)
	 */
	public Article getArticle(Article _article) throws ServiceException{
		try {
			logger.debug("Getting activated article[" + _article.getName() +"]");
			
			Article article = getActivatedArticleFromCache(_article);
			if(article==null){
				if(_article!=null && StringUtils.isNotEmpty(_article.getIdArticle())){
					article = articleDAO.getArticle(_article);
				}
				else{
					article = articleDAO.getArticleByName(_article);
				}
				
				putActivatedArticleFromCache(article);
			}
			
			return article;
			
		} catch (DAOException e) {
			logger.error("Error getting activated article",e);
			
			throw new ServiceException(e);
		}
	}

	/* (non-Javadoc)
	 * @see es.alvsanand.webpage.services.cms.CmsService#getArticlesCount()
	 */
	public int getArticlesCount() throws ServiceException{
		try {
//			logger.debug("Getting activated articles count.");
			
			int count = getActivatedArticlesCountFromCache();
			
			if(count==-1){
				count = articleDAO.getActivatedArticleCount();
				
				putActivatedArticlesCountFromCache(count);
			}
			
			return count;
			
		} catch (DAOException e) {
			logger.error("Error getting articles count",e);
			
			throw new ServiceException(e);
		}
	}
	
	/* (non-Javadoc)
	 * @see es.alvsanand.webpage.services.cms.CmsService#existsArticleTree()
	 */
	public boolean existsArticleTree() throws ServiceException{
		logger.debug("Checking if activated articles tree exists");
		
		Map<Date, Map<Date, Map<Date, List<Article>>>> articleTree = getActivatedArticlesTreeFromCache();
		if(articleTree==null){				
			return false;
		}
		else{
			return true;
		}
	}
	
	/* (non-Javadoc)
	 * @see es.alvsanand.webpage.services.cms.CmsService#getArticleTree()
	 */
	public Map<Date, Map<Date, Map<Date, List<Article>>>> getArticleTree() throws ServiceException{
		try {
			logger.debug("Getting activated articles tree");
			
			Map<Date, Map<Date, Map<Date, List<Article>>>> articleTree = getActivatedArticlesTreeFromCache();
			if(articleTree==null){				
				QueryBean queryBean = new QueryBean();
				
				List<OrderBean> orderBeans = new java.util.ArrayList<OrderBean>();
				for(String order: HomeBean.DEFAULT_SORT_FIELDS){				
					OrderBean orderBean = new OrderBean(true, order);				
					orderBeans.add(orderBean);
				}
				queryBean.setOrderBeans(orderBeans);
				
				List<Article> articles = articleDAO.getBasicActivatedArticles(queryBean);
				
				articleTree = convertListToDateTree(articles);
				
				putActivatedArticlesTreeFromCache(articleTree);
			}
			
			return articleTree;
			
		} catch (DAOException e) {
			logger.error("Error getting activated articles tree",e);
			
			throw new ServiceException(e);
		}
	}
	
	private Map<Date, Map<Date, Map<Date, List<Article>>>> convertListToDateTree(List<Article> articles){
		Map<Date, Map<Date, Map<Date, List<Article>>>> articleTree = new TreeMap<Date, Map<Date, Map<Date, List<Article>>>>();
		
		if(articles!=null && articles.size()>0){
			for(Article article: articles){
				Calendar calendar = GregorianCalendar.getInstance();
				calendar.setTime(article.getDate());
		        
				calendar.set(Calendar.MINUTE, 0);
		        calendar.set(Calendar.SECOND, 0);
		        calendar.set(Calendar.MILLISECOND, 1);
		        
				Date dayDate = new Date(calendar.getTimeInMillis());
				
		        calendar.set(Calendar.DAY_OF_MONTH, 1);  
				
				Date monthDate = new Date(calendar.getTimeInMillis());
		        
				calendar.set(Calendar.DAY_OF_YEAR, 1);
				
				Date yearDate = new Date(calendar.getTimeInMillis());
								
				Map<Date, Map<Date, List<Article>>> yearMap = articleTree.get(yearDate);
				if(yearMap==null){
					yearMap = new TreeMap<Date, Map<Date, List<Article>>>();
					
					articleTree.put(yearDate, yearMap);
				}
								
				Map<Date, List<Article>> monthMap = yearMap.get(monthDate);
				if(monthMap==null){
					monthMap = new TreeMap<Date, List<Article>>();
					
					yearMap.put(monthDate, monthMap);
				}
				
				List<Article> dayList = monthMap.get(dayDate);
				if(dayList==null){
					dayList = new ArrayList<Article>();
					
					monthMap.put(dayDate, dayList);
				}
				
				dayList.add(article);
			}
		}
		
		return articleTree;
	}
	
	/* (non-Javadoc)
	 * @see es.alvsanand.webpage.services.cms.CmsService#getTagMap()
	 */
	public Map<Tag, Integer> getTagMap() throws ServiceException{
		try {
			logger.debug("Getting activated tag map");
			
			Map<Tag, Integer> tagMap = getActivatedTagMapFromCache();
			if(tagMap==null){				
				tagMap = tagDAO.getTagCountSortedByName();
				
				putActivatedTagMapFromCache(tagMap);
			}
			
			return tagMap;
			
		} catch (DAOException e) {
			logger.error("Error getting activated articles tree",e);
			
			throw new ServiceException(e);
		}
	}
	
	/* (non-Javadoc)
	 * @see es.alvsanand.webpage.services.cms.CmsService#getAlbums()
	 */
	public List<Album> getAlbums() throws ServiceException{
		try {
			logger.debug("Getting gallery albums");
			
			List<Album> galleryAlbums = getAlbumsFromCache();
			if(galleryAlbums==null){				
				List<Album> albumEntriesTmp = imageAdminService.getAlbums();
				
				galleryAlbums= new ArrayList<Album>();
				if(albumEntriesTmp!=null){
					for(Album album: albumEntriesTmp){
						if(album.getDescription()!=null && album.getDescription().indexOf(Globals.GALLERY_ALBUM_MEDIA_KEYWORD)==0){
							Album galleryAlbum = new Album();
							
							galleryAlbum.setId(album.getId());
							galleryAlbum.setDescription(album.getDescription().substring(Globals.GALLERY_ALBUM_MEDIA_KEYWORD.length()));
							galleryAlbum.setTitle(album.getTitle());
							galleryAlbum.setDate(album.getDate());
							
							List<Photo> photoEntries = imageAdminService.getPhotos(album);
							
							List<Photo> galleryPhotos = new ArrayList<Photo>();
							for(Photo photo: photoEntries){
								Photo galleryPhoto = new Photo();
								
								galleryPhoto.setId(photo.getId());
								galleryPhoto.setDescription(photo.getDescription());
								galleryPhoto.setTitle(photo.getTitle());
								galleryPhoto.setThumbnailUrl(photo.getThumbnailUrl());
								galleryPhoto.setUrl(photo.getUrl());
								galleryPhoto.setDate(photo.getDate());
								
								galleryPhotos.add(galleryPhoto);
							}
							
							Collections.sort(galleryPhotos);
							Collections.reverse(galleryPhotos);
							
							galleryAlbum.setPhotos(galleryPhotos);
							
							galleryAlbums.add(galleryAlbum);
						}
					}
					
					Collections.sort(galleryAlbums);
					Collections.reverse(galleryAlbums);
				}				
				
				putAlbumsFromCache(galleryAlbums);
			}
			
			return galleryAlbums;
			
		} catch (ServiceException e) {
			logger.error("Error getting gallery albums",e);
			
			throw new ServiceException(e);
		}
	}

	public Avatar getAvatar(User user) throws ServiceException{
		try {
			logger.debug("Getting avatar of user[" + user +"]");
			
			Avatar avatar = getAvatarFromCache(user);
			if(avatar==null){
				avatar = avatarDAO.getAvatarByUser(user);
				
				if(avatar!=null){
					putActivatedAvatarFromCache(avatar);
				}
			}
			
			return avatar;
			
		} catch (DAOException e) {
			logger.error("Error getting activated avatar",e);
			
			throw new ServiceException(e);
		}
	}
	
	private List<Article> getActivatedArticlesFromCache(int offset){
		Cache cache = null;

		try {
			CacheFactory cacheFactory = CacheManager.getInstance().getCacheFactory();
			cache = cacheFactory.createCache(Collections.emptyMap());
			
			Map<Integer,List<Article>> articlesMap = (Map<Integer,List<Article>>)cache.get(Globals.ACTIVATED_ARTICLES_CACHE_NAME);
			
			if(articlesMap==null){				
				return null;
			}
			else{
				logger.debug("Found activated articles From Cache");
			}
			
			return articlesMap.get(new Integer(offset));
		} catch (CacheException cacheException) {
			logger.error("Error in getting activated Articles List from cache.",cacheException);
			
			return null;
		}
		catch(Exception exception){
			logger.error("Error in getting activated Articles List from cache.",exception);
			
			if(cache!=null){
				cache.remove(Globals.ACTIVATED_ARTICLES_CACHE_NAME);
			}
			return null;
		}
	}
	
	private void putActivatedArticlesFromCache(List<Article> articles, int offset){
		Cache cache = null;
		
		if(articles==null){
			return;
		}

		try {
			CacheFactory cacheFactory = CacheManager.getInstance().getCacheFactory();
			cache = cacheFactory.createCache(Collections.emptyMap());
			
			Map<Integer,List<Article>> articlesMap = (Map<Integer,List<Article>>)cache.get(Globals.ACTIVATED_ARTICLES_CACHE_NAME);
			
			if(articlesMap==null){
				articlesMap = new HashMap<Integer,List<Article>>();
			}
			
			articlesMap.put(new Integer(offset), articles);
			cache.put(Globals.ACTIVATED_ARTICLES_CACHE_NAME, articlesMap);	
			
			logger.debug("Saved activated articles list to cache");
		} catch (CacheException cacheException) {
			logger.error("Error in putting activated articles list to cache.",cacheException);
		}
		catch(Exception exception){
			logger.error("Error in putting activated articles list to cache.",exception);
			if(cache!=null){
				cache.remove(Globals.ACTIVATED_ARTICLES_CACHE_NAME);
			}
		}
	}
	
	private int getActivatedArticlesCountFromCache(){
		Cache cache = null;

		try {
			CacheFactory cacheFactory = CacheManager.getInstance().getCacheFactory();
			cache = cacheFactory.createCache(Collections.emptyMap());
			
			Integer count = (Integer)cache.get(Globals.ACTIVATED_ARTICLES_COUNT_CACHE_NAME);
			
			if(count==null){				
				return -1;
			}
			else{
//				logger.debug("Found activated articles count From Cache");
				
				return count.intValue();
			}
		} catch (CacheException cacheException) {
			logger.error("Error in getting activated Articles count from cache.",cacheException);
			
			return -1;
		}
		catch(Exception exception){
			logger.error("Error in getting activated Articles count from cache.",exception);
			
			if(cache!=null){
				cache.remove(Globals.ACTIVATED_ARTICLES_COUNT_CACHE_NAME);
			}
			return -1;
		}
	}
	
	private void putActivatedArticlesCountFromCache(int count){
		Cache cache;

		try {
			CacheFactory cacheFactory = CacheManager.getInstance().getCacheFactory();
			cache = cacheFactory.createCache(Collections.emptyMap());
			cache.put(Globals.ACTIVATED_ARTICLES_COUNT_CACHE_NAME, new Integer(count));
		} catch (CacheException cacheException) {
			logger.error("Error in putting activated Articles count to cache.",cacheException);
		}
	}
	
	private Map<Date, Map<Date, Map<Date, List<Article>>>> getActivatedArticlesTreeFromCache(){
		Cache cache = null;

		try {
			CacheFactory cacheFactory = CacheManager.getInstance().getCacheFactory();
			cache = cacheFactory.createCache(Collections.emptyMap());
			
			Map<Date, Map<Date, Map<Date, List<Article>>>> articlesMap = (Map<Date, Map<Date, Map<Date, List<Article>>>>)cache.get(Globals.ACTIVATED_ARTICLES_TREE_CACHE_NAME);
			
			return articlesMap;
		} catch (CacheException cacheException) {
			logger.error("Error in getting activated Articles Tree from cache.",cacheException);
			
			return null;
		}
		catch(Exception exception){
			logger.error("Error in getting activated Articles Tree from cache.",exception);
			
			if(cache!=null){
				cache.remove(Globals.ACTIVATED_ARTICLES_TREE_CACHE_NAME);
			}
			return null;
		}
	}
	
	private void putActivatedArticlesTreeFromCache(Map<Date, Map<Date, Map<Date, List<Article>>>> articles){
		Cache cache = null;
		
		if(articles==null){
			return;
		}

		try {
			CacheFactory cacheFactory = CacheManager.getInstance().getCacheFactory();
			cache = cacheFactory.createCache(Collections.emptyMap());
			
			cache.put(Globals.ACTIVATED_ARTICLES_TREE_CACHE_NAME, articles);	
			
			logger.debug("Saved activated articles Tree to cache");
		} catch (CacheException cacheException) {
			logger.error("Error in putting activated articles Tree to cache.",cacheException);
		}
		catch(Exception exception){
			logger.error("Error in putting activated articles Tree to cache.",exception);
			if(cache!=null){
				cache.remove(Globals.ACTIVATED_ARTICLES_TREE_CACHE_NAME);
			}
		}
	}
	
	private Map<Tag, Integer> getActivatedTagMapFromCache(){
		Cache cache = null;

		try {
			CacheFactory cacheFactory = CacheManager.getInstance().getCacheFactory();
			cache = cacheFactory.createCache(Collections.emptyMap());
			
			Map<Tag, Integer> tagMap = (Map<Tag, Integer>)cache.get(Globals.ACTIVATED_TAG_MAP_CACHE_NAME);
			
			return tagMap;
		} catch (CacheException cacheException) {
			logger.error("Error in getting activated tags Tree from cache.",cacheException);
			
			return null;
		}
		catch(Exception exception){
			logger.error("Error in getting activated tags from cache.",exception);
			
			if(cache!=null){
				cache.remove(Globals.ACTIVATED_TAG_MAP_CACHE_NAME);
			}
			return null;
		}
	}
	
	private void putActivatedTagMapFromCache(Map<Tag, Integer> tags){
		Cache cache = null;
		
		if(tags==null){
			return;
		}

		try {
			CacheFactory cacheFactory = CacheManager.getInstance().getCacheFactory();
			cache = cacheFactory.createCache(Collections.emptyMap());
			
			cache.put(Globals.ACTIVATED_TAG_MAP_CACHE_NAME, tags);	
			
			logger.debug("Saved activated tags to cache");
		} catch (CacheException cacheException) {
			logger.error("Error in putting activated tags to cache.",cacheException);
		}
		catch(Exception exception){
			logger.error("Error in putting activated tags to cache.",exception);
			if(cache!=null){
				cache.remove(Globals.ACTIVATED_TAG_MAP_CACHE_NAME);
			}
		}
	}
	
	private Article getActivatedArticleFromCache(Article article){
		Cache cache = null;

		try {
			CacheFactory cacheFactory = CacheManager.getInstance().getCacheFactory();
			cache = cacheFactory.createCache(Collections.emptyMap());
			
			Map<String, Article> articlesMap = (Map<String, Article>)cache.get(Globals.ACTIVATED_ARTICLES_BY_ID_CACHE_NAME);
			
			if(articlesMap==null){				
				return null;
			}
			else{
				logger.debug("Found activated Articles by id From Cache");
			}
			
			return articlesMap.get(article.getName());
		} catch (CacheException cacheException) {
			logger.error("Error in getting activated Articles by id from cache.",cacheException);
			
			return null;
		}
		catch(Exception exception){
			logger.error("Error in getting activated Articles by id from cache.",exception);
			
			if(cache!=null){
				cache.remove(Globals.ACTIVATED_ARTICLES_BY_ID_CACHE_NAME);
			}
			return null;
		}
	}
	
	private void putActivatedArticleFromCache(Article article){
		Cache cache = null;
		
		if(article==null){
			return;
		}

		try {
			CacheFactory cacheFactory = CacheManager.getInstance().getCacheFactory();
			cache = cacheFactory.createCache(Collections.emptyMap());
			
			Map<String, Article> articlesMap = (Map<String, Article>)cache.get(Globals.ACTIVATED_ARTICLES_BY_ID_CACHE_NAME);
			
			if(articlesMap==null){
				articlesMap = new HashMap<String, Article>();
			}
			
			articlesMap.put(article.getName(), article);
			cache.put(Globals.ACTIVATED_ARTICLES_BY_ID_CACHE_NAME, articlesMap);	
			
			logger.debug("Saved activated activated Articles by id to cache");
		} catch (CacheException cacheException) {
			logger.error("Error in putting activated Articles by id to cache.",cacheException);
		}
		catch(Exception exception){
			logger.error("Error in putting activated Articles by id to cache.",exception);
			if(cache!=null){
				cache.remove(Globals.ACTIVATED_ARTICLES_BY_ID_CACHE_NAME);
			}
		}
	}
	
	private List<Album> getAlbumsFromCache(){
		Cache cache = null;

		try {
			CacheFactory cacheFactory = CacheManager.getInstance().getCacheFactory();
			cache = cacheFactory.createCache(Collections.emptyMap());
			
			List<Album> galleryAlbums = (List<Album>)cache.get(Globals.GALLERY_ALBUMS_CACHE_NAME);
			
			return galleryAlbums;
		} catch (CacheException cacheException) {
			logger.error("Error in getting gallery Albums from cache.",cacheException);
			
			return null;
		}
		catch(Exception exception){
			logger.error("Error in getting gallery Albums from cache.",exception);
			
			if(cache!=null){
				cache.remove(Globals.GALLERY_ALBUMS_CACHE_NAME);
			}
			return null;
		}
	}
	
	private void putAlbumsFromCache(List<Album> albumEntries){
		Cache cache = null;
		
		if(albumEntries==null){
			return;
		}

		try {
			CacheFactory cacheFactory = CacheManager.getInstance().getCacheFactory();
			cache = cacheFactory.createCache(Collections.emptyMap());
			
			cache.put(Globals.GALLERY_ALBUMS_CACHE_NAME, albumEntries);	
			
			logger.debug("Saved gallery Albums to cache");
		} catch (CacheException cacheException) {
			logger.error("Error in putting gallery Albums to cache.",cacheException);
		}
		catch(Exception exception){
			logger.error("Error in putting gallery Albums to cache.",exception);
			if(cache!=null){
				cache.remove(Globals.GALLERY_ALBUMS_CACHE_NAME);
			}
		}
	}
	
	private Avatar getAvatarFromCache(User user){
		Cache cache = null;

		try {
			CacheFactory cacheFactory = CacheManager.getInstance().getCacheFactory();
			cache = cacheFactory.createCache(Collections.emptyMap());
			
			Map<String, Avatar> avatarsMap = (Map<String, Avatar>)cache.get(Globals.AVATAR_BY_ID_USER_CACHE_NAME);
			
			if(avatarsMap==null){				
				return null;
			}
			else{
				logger.debug("Found Avatar by user From Cache");
			}
			
			return avatarsMap.get(user.getIdUser());
		} catch (CacheException cacheException) {
			logger.error("Error in getting Avatar by user from cache.",cacheException);
			
			return null;
		}
		catch(Exception exception){
			logger.error("Error in getting Avatar by user from cache.",exception);
			
			if(cache!=null){
				cache.remove(Globals.ACTIVATED_ARTICLES_BY_ID_CACHE_NAME);
			}
			return null;
		}
	}
	
	private void putActivatedAvatarFromCache(Avatar avatar){
		Cache cache = null;
		
		if(avatar==null){
			return;
		}

		try {
			CacheFactory cacheFactory = CacheManager.getInstance().getCacheFactory();
			cache = cacheFactory.createCache(Collections.emptyMap());
			
			Map<String, Avatar> avatarsMap = (Map<String, Avatar>)cache.get(Globals.AVATAR_BY_ID_USER_CACHE_NAME);
			
			if(avatarsMap==null){
				avatarsMap = new HashMap<String, Avatar>();
			}
			
			avatarsMap.put(avatar.getIdUser(), avatar);
			cache.put(Globals.AVATAR_BY_ID_USER_CACHE_NAME, avatarsMap);	
			
			logger.debug("Saved activated Avatar by user to cache");
		} catch (CacheException cacheException) {
			logger.error("Error in putting Avatar by user to cache.",cacheException);
		}
		catch(Exception exception){
			logger.error("Error in putting Avatar by user to cache.",exception);
			if(cache!=null){
				cache.remove(Globals.AVATAR_BY_ID_USER_CACHE_NAME);
			}
		}
	}
}