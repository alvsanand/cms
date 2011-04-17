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
package es.alvsanand.webpage.web.beans.cms;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import org.apache.commons.lang.StringUtils;

import es.alvsanand.webpage.AlvsanandException;
import es.alvsanand.webpage.common.Globals;
import es.alvsanand.webpage.common.Logger;
import es.alvsanand.webpage.model.Article;
import es.alvsanand.webpage.model.Tag;
import es.alvsanand.webpage.services.cms.SearchCmsService;
import es.alvsanand.webpage.services.cms.SearchCmsServiceImpl;

@SessionScoped
@ManagedBean(name="searchBean")
public class SearchBean implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 4278071046272339705L;

	private transient static final Logger logger = new Logger(SearchBean.class);
	
	private transient static final String LIST_SEARCH_ARTICLES_VIEW_ID = "/xhtml/articlesByQuery.jsf";
	
	private transient static final String LIST_DATES_ARTICLES_VIEW_ID = "/xhtml/articlesByTag.jsf";
	
	public transient static final String[] DEFAULT_SORT_FIELDS = {"date"};

	private transient SearchCmsService searchCmsService;
	
	private String tagName;
	
	private String query;

	private List<Article> articles;	
	
	private String date;

	public SearchBean(){
	}

	public String getTagName() {
		return tagName;
	}

	public void setTagName(String tagName) {
		this.tagName = tagName;
	}

	public String getQuery() {
		return query;
	}

	public void setQuery(String query) {
		this.query = query;
	}

	public String getEncodedQuery() throws UnsupportedEncodingException {
		return URLEncoder.encode(this.query, Globals.CHARACTER_ENCODING);
	}

	public void setEncodedQuery(String encodedQuery) throws UnsupportedEncodingException {
		this.query = URLDecoder.decode(encodedQuery, Globals.CHARACTER_ENCODING);
	}

	public List<Article> getArticles() {
		return articles;
	}

	public void setArticles(List<Article> articles) {
		this.articles = articles;
	}

	public SearchCmsService getSearchCmsService() {
		if(searchCmsService==null){
			 searchCmsService = new SearchCmsServiceImpl();
		}
		return searchCmsService;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	// JSF methods

	public void searchArticlesByTagName() throws AlvsanandException, UnsupportedEncodingException {
		logger.info("Launched SearchBean.searchArticlesByTagName[" + tagName + "]");
		
		Tag tag = new Tag();
		tag.setEncodedName(tagName);

		articles = getSearchCmsService().getArticlesByTag(tag);
	}

	public void searchArticlesByQueryNoNiew() throws AlvsanandException {
		logger.info("Launched SearchBean.searchArticlesByQueryNoNiew[" + query + "]");
		
		articles = getSearchCmsService().searchArticles(query);	
	}

	public String searchArticlesByQuery() throws AlvsanandException {
		logger.info("Launched SearchBean.searchArticlesByQuery[" + query + "]");
		
		articles = getSearchCmsService().searchArticles(query);	
		
		return LIST_SEARCH_ARTICLES_VIEW_ID;
	}

	public boolean isSearchEmpty() {
		if(articles==null || articles.size()==0){
			return true;
		}
		else{
			return false;
		}
	}

	public void searchArticlesByDate() throws AlvsanandException {
		logger.info("Launched SearchBean.searchArticlesByDate[date=" + date + "]");
		
		Calendar calendar = new GregorianCalendar();       
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 1);
        
        if(StringUtils.isNotEmpty(date)){        
        	String[] tmp = date.split(Globals.DATE_SEPARATOR);
        	
        	if(tmp.length>0){
        		Date beginDate = null;
        		Date endDate = null;
        		
        		switch(tmp.length){
        		case 1:
        			calendar.set(Calendar.YEAR, Integer.parseInt(tmp[0]));
        			calendar.set(Calendar.MONTH, 0);
        			calendar.set(Calendar.DAY_OF_MONTH, 1);
        			
        			beginDate = calendar.getTime();
        			
        			calendar.add(Calendar.YEAR, 1);        			
        			endDate = calendar.getTime();
        			
        			break;
        		case 2:
        			calendar.set(Calendar.YEAR, Integer.parseInt(tmp[0]));
        			calendar.set(Calendar.MONTH, Integer.parseInt(tmp[1])-1);
        			calendar.set(Calendar.DAY_OF_MONTH, 1);
        			
        			beginDate = calendar.getTime();
        			
        			calendar.add(Calendar.MONTH, 1);        			
        			endDate = calendar.getTime();
        			
        			break;
        		default:
        			calendar.set(Calendar.YEAR, Integer.parseInt(tmp[0]));
        			calendar.set(Calendar.MONTH, Integer.parseInt(tmp[1])-1);
        			calendar.set(Calendar.DAY_OF_MONTH, Integer.parseInt(tmp[2]));
        			
        			beginDate = calendar.getTime();
        			
        			calendar.add(Calendar.DAY_OF_YEAR, 1);        			
        			endDate = calendar.getTime();
        			
        			break;
        		}
        		
        		articles = getSearchCmsService().getArticlesByDates(beginDate, endDate);	
        	}
		
			
        }
	}
}
