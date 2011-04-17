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
package test.es.alvsanand.webpage.services;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.google.appengine.api.datastore.Text;
import com.google.appengine.tools.development.testing.LocalDatastoreServiceTestConfig;
import com.google.appengine.tools.development.testing.LocalServiceTestHelper;

import es.alvsanand.webpage.model.Article;
import es.alvsanand.webpage.model.ArticleState;
import es.alvsanand.webpage.model.Tag;
import es.alvsanand.webpage.services.admin.CmsAdminService;
import es.alvsanand.webpage.services.admin.CmsAdminServiceImpl;
import es.alvsanand.webpage.services.cms.SearchCmsService;
import es.alvsanand.webpage.services.cms.SearchCmsServiceImpl;

public class SearchCmsServiceTest {
	private final LocalServiceTestHelper helper;

	public SearchCmsServiceTest() {
		LocalDatastoreServiceTestConfig localServiceTestConfig = new LocalDatastoreServiceTestConfig();

		localServiceTestConfig.setNoStorage(true);

		helper = new LocalServiceTestHelper(localServiceTestConfig);
	}

	private SearchCmsService searchCmsService;	
	private CmsAdminService cmsAdminService;

	@Before
	public void setUp() throws Exception{
		helper.setUp();

		searchCmsService = new SearchCmsServiceImpl();
        cmsAdminService = new CmsAdminServiceImpl();
        
        initTestData();
	}

	@After
	public void tearDown() {
		helper.tearDown();
	}
	
	private void initTestData() throws Exception {
		Calendar calendar = new GregorianCalendar();
		calendar.set(Calendar.YEAR, 2008);
        calendar.set(Calendar.DAY_OF_YEAR, 1);        
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 1);
		
		for(int h=0; h<3; h++){	        
			for(int i=0; i<10; i++){
				Date date = calendar.getTime();
				
				Tag tag = new Tag();
		        tag.setName("Tag" + h);
		        tag.setAsociated(true);
		        
		        List<Tag> tags = new ArrayList<Tag>();
		        tags.add(tag);
		        
		    	Article article = new Article();
		        article.setName("ArticleTest" + i + "-" + h);
		        article.setTitle("ArticleTest" + i + "-" + h);
		        article.setDate(date);
		        article.setData(new Text("Article data " + i + "-" + h));
		        article.setState(ArticleState.ENABLED.ordinal());
		        
		        article.setTags(tags);
		    	
		    	cmsAdminService.saveOrUpdateArticle(article);
		    	
				calendar.add(Calendar.MONTH, 1);
			}
			calendar.add(Calendar.YEAR, 1);
			
			calendar.set(Calendar.MONTH, 0);
		}		
	}

	@Test
	public void testGetArticlesByTag() throws Exception {
		Tag tag = new Tag();
        tag.setName("Tag0");
		
		List<Article> articles = searchCmsService.getArticlesByTag(tag);
		
		Assert.assertNotNull(articles);
		Assert.assertEquals(10, articles.size());
	}

	@Test
	public void testSearchArticles() throws Exception {
		List<Article> articles = searchCmsService.searchArticles("*");
		
		Assert.assertNotNull(articles);
		Assert.assertEquals(30, articles.size());
		
		articles = searchCmsService.searchArticles("ArticleTest0*");
		
		Assert.assertNotNull(articles);
		Assert.assertEquals(3, articles.size());
		
		articles = searchCmsService.searchArticles("ArticleTest0-0");
		
		Assert.assertNotNull(articles);
		Assert.assertEquals(1, articles.size());
	}

	@Test
	public void testGetArticlesByYear() throws Exception {
		Calendar calendar = new GregorianCalendar();
        calendar.set(Calendar.YEAR, 2008);
        calendar.set(Calendar.DAY_OF_YEAR, 1);        
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
		
        Date beginDate = calendar.getTime();
        
        calendar.add(Calendar.YEAR, 1);
        
        Date endDate = calendar.getTime();
        
		List<Article> articles = searchCmsService.getArticlesByDates(beginDate, endDate);
		
		Assert.assertNotNull(articles);
		Assert.assertEquals(10, articles.size());
	}

	@Test
	public void testGetArticlesByMonth() throws Exception {
		Calendar calendar = new GregorianCalendar();
        calendar.set(Calendar.YEAR, 2008);
        calendar.set(Calendar.DAY_OF_YEAR, 1);        
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
		
        Date beginDate = calendar.getTime();
        
        calendar.add(Calendar.MONTH, 3);
        
        Date endDate = calendar.getTime();
        
		List<Article> articles = searchCmsService.getArticlesByDates(beginDate, endDate);
		
		Assert.assertNotNull(articles);
		Assert.assertEquals(3, articles.size());
	}
}
