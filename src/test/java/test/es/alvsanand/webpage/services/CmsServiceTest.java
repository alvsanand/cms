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

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.google.appengine.api.datastore.Blob;
import com.google.appengine.api.datastore.Text;
import com.google.appengine.tools.development.testing.LocalDatastoreServiceTestConfig;
import com.google.appengine.tools.development.testing.LocalServiceTestHelper;

import es.alvsanand.webpage.common.AlvsanandProperties;
import es.alvsanand.webpage.common.Globals;
import es.alvsanand.webpage.common.photo.Album;
import es.alvsanand.webpage.model.Article;
import es.alvsanand.webpage.model.ArticleState;
import es.alvsanand.webpage.model.Avatar;
import es.alvsanand.webpage.model.Tag;
import es.alvsanand.webpage.model.User;
import es.alvsanand.webpage.services.admin.CmsAdminService;
import es.alvsanand.webpage.services.admin.CmsAdminServiceImpl;
import es.alvsanand.webpage.services.admin.ImageAdminService;
import es.alvsanand.webpage.services.admin.ImageAdminServiceImpl;
import es.alvsanand.webpage.services.cms.CmsService;
import es.alvsanand.webpage.services.cms.CmsServiceImpl;
import es.alvsanand.webpage.services.session.UserService;
import es.alvsanand.webpage.services.session.UserServiceImpl;

public class CmsServiceTest {
	private final static String BIG_PHOTO_DEMO_FILE = "/foo.jpg";
	
	private final LocalServiceTestHelper helper;

	public CmsServiceTest() {
		LocalDatastoreServiceTestConfig localServiceTestConfig = new LocalDatastoreServiceTestConfig();

		localServiceTestConfig.setNoStorage(true);

		helper = new LocalServiceTestHelper(localServiceTestConfig);
	}

	private CmsService cmsService;	
	
	private CmsAdminService cmsAdminService;

	private ImageAdminService imageAdminService;

	private UserService userService;

	@Before
	public void setUp() throws Exception{
		helper.setUp();

		cmsService = new CmsServiceImpl();
		
        cmsAdminService = new CmsAdminServiceImpl();

		imageAdminService = new ImageAdminServiceImpl();
		
		userService = new UserServiceImpl();
		
		Properties properties = new Properties();
		properties.loadFromXML(ImageAdminServiceTest.class.getResourceAsStream("/" + AlvsanandProperties.CONFIG_FILE_NAME));
		
		AlvsanandProperties.setProperties(properties);
        
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
        
		for(int i=0; i<15; i++){
	    	Article article = new Article();
	        article.setName("ArticleTest" + i);
	        article.setTitle("ArticleTest" + i);
	        article.setDate(calendar.getTime());
	        article.setData(new Text("Article data + i"));
	        article.setState(ArticleState.ENABLED.ordinal());
	        

	    	Tag tag = new Tag();
	    	tag.setName("Tag"+((i+1)%5));
	    	tag.setAsociated(true);
	    	
	    	List<Tag> tags = new ArrayList<Tag>();
	    	tags.add(tag);
	    	
	    	article.setTags(tags);
	    	
	    	cmsAdminService.saveOrUpdateArticle(article);
	    	
	    	calendar.add(Calendar.MONTH, 1);
		}
		
	}

	@Test
	public void testGetArticlesCount() throws Exception {
		Assert.assertEquals(15, cmsService.getArticlesCount());
	}

	@Test
	public void testGetArticles() throws Exception {
		List<Article> articles = cmsService.getArticles(0);
		
		Assert.assertNotNull(articles);
		Assert.assertEquals(10, articles.size());
		
		articles = cmsService.getArticles(10);
		
		Assert.assertNotNull(articles);
		Assert.assertEquals(5, articles.size());
	}

	@Test
	public void testGetArticlesTree() throws Exception {
		Map<Date, Map<Date, Map<Date, List<Article>>>> articleTree = cmsService.getArticleTree();
		
		Assert.assertNotNull(articleTree);
		
		Assert.assertEquals(2, articleTree.size());
		
		Calendar calendar = new GregorianCalendar();
		calendar.set(Calendar.YEAR, 2008);
        calendar.set(Calendar.DAY_OF_YEAR, 1);        
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 1);

        calendar.set(Calendar.MONTH, 2);
        
        Date date = new Date(calendar.getTimeInMillis());
        
		Assert.assertNotNull(articleTree.get(date));
		Assert.assertEquals(1, articleTree.get(date).get(date).size());

		Assert.assertNotNull(articleTree.get(date).get(date));
        Assert.assertEquals(1, articleTree.get(date).get(date).get(date).size());
	}

	@Test
	public void testGetTagMap() throws Exception {
		Map<Tag, Integer> tagMap = cmsService.getTagMap();
		
		Assert.assertNotNull(tagMap);
		
		Assert.assertEquals(5, tagMap.size());
		
		Tag tag = new Tag();
		tag.setName("Tag0");
		
		Assert.assertNotNull(tagMap.get(tag));
		Assert.assertEquals(3, tagMap.get(tag).intValue());
	}

//	@Test
	public void testGetGalleryAlbums() throws Exception {
		String albumName = "Foo Album" + Math.random();
		
		Album newAlbum = new Album();
		newAlbum.setTitle(albumName);
		newAlbum.setDescription(Globals.GALLERY_ALBUM_MEDIA_KEYWORD + "Description of " + albumName);
		
		newAlbum = imageAdminService.addAlbum(newAlbum);
		
		
		albumName = "Foo Album" + Math.random();
		
		newAlbum = new Album();
		newAlbum.setTitle(albumName);
		newAlbum.setDescription("Description of " + albumName);
		
		newAlbum = imageAdminService.addAlbum(newAlbum);
		
		List<Album> albumEntries = imageAdminService.getAlbums();
		
		List<es.alvsanand.webpage.common.photo.Album> galleryAlbums = cmsService.getAlbums();
		
		for(Album album: albumEntries){
			imageAdminService.deleteAlbum(album);
		}
		
		Assert.assertEquals(1, galleryAlbums.size());
	}

	@Test
	public void testGetAvatar() throws Exception {
		Avatar _avatar = new Avatar();
		_avatar.setDate(new Date());		
		_avatar.setData(new Blob(readBigTestImage()));
		
		User user = new User();
		user.setLoginName("foo");
		user.setAvatar(_avatar);
		
		userService.saveUser(user);
		
		
		Avatar avatar = cmsService.getAvatar(user);
		
		Assert.assertNotNull(avatar);
	}

	private byte[] readBigTestImage() throws IOException {
		InputStream inputStream = ImageAdminServiceTest.class.getResourceAsStream(BIG_PHOTO_DEMO_FILE);

		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

		byte[] buf = new byte[1024];

		int length;

		while ((length = inputStream.read(buf)) > 0) {
			outputStream.write(buf, 0, length);
		}

		inputStream.close();

		outputStream.close();

		return outputStream.toByteArray();
	}
}
