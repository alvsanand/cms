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
import java.util.Date;
import java.util.List;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Text;
import com.google.appengine.tools.development.testing.LocalDatastoreServiceTestConfig;
import com.google.appengine.tools.development.testing.LocalServiceTestHelper;

import es.alvsanand.webpage.model.Article;
import es.alvsanand.webpage.model.ArticleVersion;
import es.alvsanand.webpage.model.Comment;
import es.alvsanand.webpage.model.Rating;
import es.alvsanand.webpage.model.Tag;
import es.alvsanand.webpage.model.User;
import es.alvsanand.webpage.services.admin.CmsAdminService;
import es.alvsanand.webpage.services.admin.CmsAdminServiceImpl;

public class CmsAdminServiceTest {
	private final LocalServiceTestHelper helper;	

	public CmsAdminServiceTest(){
		LocalDatastoreServiceTestConfig localServiceTestConfig = new LocalDatastoreServiceTestConfig();
		
		localServiceTestConfig.setNoStorage(true);
		
		helper = new LocalServiceTestHelper(localServiceTestConfig);
	}
	
	private CmsAdminService cmsAdminService;
	
    @Before
    public void setUp() {
        helper.setUp();
        
        cmsAdminService = new CmsAdminServiceImpl();
    }

    @After
    public void tearDown() {
        helper.tearDown();
    }

    @Test
    public void testSaveTag() throws Exception {
        Tag tag = new Tag();
        tag.setName("TagTest");
    	
        cmsAdminService.saveOrUpdateTag(tag);	    	
    	
    	tag = new Tag();
        tag.setName("TagTest");
        
    	Assert.assertNotNull(cmsAdminService.getTag(tag));
    }

    @Test
    public void testDeleteTagWithSameName() throws Exception {        
    	for(int i=0; i<5; i++){
	    	Tag tag = new Tag();
	        tag.setName("TagTest");
	        tag.setDescription("TagTest - Article" + i);
	    	
	        cmsAdminService.saveOrUpdateTag(tag);
    	}
    	
    	Tag tag = new Tag();
        tag.setName("TagTest");
        
        cmsAdminService.deleteTag(tag);
        
        DatastoreService ds = DatastoreServiceFactory.getDatastoreService();
        Assert.assertEquals(0, ds.prepare(new Query("Tag")).countEntities(FetchOptions.Builder.withLimit(100)));
    }

    @Test
    public void testGetTagCount() throws Exception {
        Tag tag = new Tag();
        tag.setName("TagTest");
    	
        cmsAdminService.saveOrUpdateTag(tag);	

        DatastoreService ds = DatastoreServiceFactory.getDatastoreService();
        Assert.assertEquals(ds.prepare(new Query("Tag")).countEntities(FetchOptions.Builder.withLimit(100)), cmsAdminService.getTagCount());
    }

    @Test
    public void testSaveArticle() throws Exception {
        Article article = new Article();
        article.setName("ArticleTest");
    	
        cmsAdminService.saveOrUpdateArticle(article);	

        Assert.assertNotNull(cmsAdminService.getArticle(article));
    }

    @Test
    public void testGetArticleCount() throws Exception {
        Article article = new Article();
        article.setName("ArticleTest");
    	
        cmsAdminService.saveOrUpdateArticle(article);	

        DatastoreService ds = DatastoreServiceFactory.getDatastoreService();
        Assert.assertEquals(ds.prepare(new Query("Article")).countEntities(FetchOptions.Builder.withLimit(100)), cmsAdminService.getArticleCount());
    }

    @Test
    public void testDeleteArticle() throws Exception {
    	Article article = new Article();
        article.setName("ArticleTest");
        article.setTitle("ArticleTest");
        article.setDate(new Date());
        article.setData(new Text("Article data"));
        
        List<ArticleVersion> articleVersions = new ArrayList<ArticleVersion>();
    	for(int i=0; i<5; i++){
	    	ArticleVersion articleVersion = new ArticleVersion();
	    	articleVersion.setTitle("ArticleVersion" + i);
	    	articleVersion.setDate(new Date());
	    	articleVersion.setData(new Text("Article data - Modified " + i));
	        
	    	articleVersions.add(articleVersion);
    	}
    	article.setArticleVersions(articleVersions);
        
        List<Comment> comments = new ArrayList<Comment>();
    	for(int i=0; i<5; i++){
	    	Comment comment = new Comment();
	    	comment.setTitle("Comment" + i);
	    	comment.setDate(new Date());
	    	comment.setData(new Text("Comment data " + i));
	        
	    	comments.add(comment);
    	}
    	article.setComments(comments);
        
        List<Tag> tags = new ArrayList<Tag>();
    	for(int i=0; i<5; i++){
	    	Tag tag = new Tag();
	    	tag.setName("Tag" + i);
	        
	    	tags.add(tag);
    	}
    	article.setTags(tags);
        
        List<Rating> ratings = new ArrayList<Rating>();
    	for(int i=0; i<5; i++){
	    	Rating rating = new Rating();
	    	rating.setRatingNumber(10);
	        
	    	ratings.add(rating);
    	}
    	article.setRatings(ratings);
    	
    	cmsAdminService.saveOrUpdateArticle(article);
        
        cmsAdminService.deleteArticle(article);
                
        DatastoreService ds = DatastoreServiceFactory.getDatastoreService();
        Assert.assertEquals(0, ds.prepare(new Query("Article")).countEntities(FetchOptions.Builder.withLimit(100)));
        Assert.assertEquals(0, ds.prepare(new Query("ArticleVersion")).countEntities(FetchOptions.Builder.withLimit(100)));
        Assert.assertEquals(0, ds.prepare(new Query("Tag")).countEntities(FetchOptions.Builder.withLimit(100)));
        Assert.assertEquals(0, ds.prepare(new Query("Comment")).countEntities(FetchOptions.Builder.withLimit(100)));
        Assert.assertEquals(0, ds.prepare(new Query("Rating")).countEntities(FetchOptions.Builder.withLimit(100)));
    }

    @Test
    public void testSaveComment() throws Exception {
    	User user = new User();
        user.setIdUser("Foo");
    	
        Article article = new Article();
        article.setName("ArticleTest");
        article.setTitle("ArticleTest");
        article.setDate(new Date());
        article.setData(new Text("Article data"));
        
        cmsAdminService.saveOrUpdateArticle(article);
        
        Comment comment = new Comment();
    	comment.setTitle("Comment");
    	comment.setDate(new Date());
    	comment.setData(new Text("Comment data"));
    	comment.setArticle(article);
    	comment.setUser(user);
        
        cmsAdminService.saveOrUpdateComment(comment);

        DatastoreService ds = DatastoreServiceFactory.getDatastoreService();
        Assert.assertEquals(1, ds.prepare(new Query("Comment")).countEntities(FetchOptions.Builder.withLimit(100)));
    }

    @Test
    public void testDeleteComment() throws Exception {
    	User user = new User();
        user.setIdUser("Foo");
    	
    	Article article = new Article();
        article.setName("ArticleTest");
        article.setTitle("ArticleTest");
        article.setDate(new Date());
        article.setData(new Text("Article data"));
        
        cmsAdminService.saveOrUpdateArticle(article);
        
        Comment comment = new Comment();
    	comment.setTitle("Comment");
    	comment.setDate(new Date());
    	comment.setData(new Text("Comment data"));
    	comment.setArticle(article);
    	comment.setUser(user);
        
        cmsAdminService.saveOrUpdateComment(comment);
        
        cmsAdminService.deleteComment(comment);
                
        DatastoreService ds = DatastoreServiceFactory.getDatastoreService();
        Assert.assertEquals(1, ds.prepare(new Query("Article")).countEntities(FetchOptions.Builder.withLimit(100)));
        Assert.assertEquals(0, ds.prepare(new Query("Comment")).countEntities(FetchOptions.Builder.withLimit(100)));
    }

    @Test
    public void testSaveRating() throws Exception {
    	User user = new User();
        user.setIdUser("Foo");
    	
        Article article = new Article();
        article.setName("ArticleTest");
        article.setTitle("ArticleTest");
        article.setDate(new Date());
        article.setData(new Text("Article data"));
        
        cmsAdminService.saveOrUpdateArticle(article);
        
        Rating rating = new Rating();
    	rating.setDate(new Date());
    	rating.setRatingNumber(1);
    	rating.setArticle(article);
    	rating.setUser(user);
        
        cmsAdminService.saveOrUpdateRating(rating);

        DatastoreService ds = DatastoreServiceFactory.getDatastoreService();
        Assert.assertEquals(1, ds.prepare(new Query("Rating")).countEntities(FetchOptions.Builder.withLimit(100)));
    }
}
