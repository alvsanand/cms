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
import java.util.List;
import java.util.Properties;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.google.appengine.api.images.Image;
import com.google.appengine.api.images.ImagesServiceFactory;
import com.google.appengine.repackaged.com.google.common.util.Base64;
import com.google.appengine.tools.development.testing.LocalDatastoreServiceTestConfig;
import com.google.appengine.tools.development.testing.LocalServiceTestHelper;

import es.alvsanand.webpage.common.AlvsanandProperties;
import es.alvsanand.webpage.common.Globals;
import es.alvsanand.webpage.common.photo.Album;
import es.alvsanand.webpage.common.photo.Photo;
import es.alvsanand.webpage.services.admin.ImageAdminService;
import es.alvsanand.webpage.services.admin.ImageAdminServiceImpl;

public class ImageAdminServiceTest {
	private final static String BIG_PHOTO_DEMO_FILE = "/foo.jpg";
	private final static String PHOTO_DEMO_DATA = "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";

	private final static String PHOTO_DEMO_MEDIA_TYPE = "image/gif";

	private final LocalServiceTestHelper helper;

	private ImageAdminService imageAdminService;

	public ImageAdminServiceTest() {
		LocalDatastoreServiceTestConfig localServiceTestConfig = new LocalDatastoreServiceTestConfig();

		localServiceTestConfig.setNoStorage(true);

		helper = new LocalServiceTestHelper(localServiceTestConfig);
	}

	@Before
	public void setUp() throws Exception {
		helper.setUp();

		imageAdminService = new ImageAdminServiceImpl();

		Properties properties = new Properties();
		properties.loadFromXML(ImageAdminServiceTest.class.getResourceAsStream("/" + AlvsanandProperties.CONFIG_FILE_NAME));

		AlvsanandProperties.setProperties(properties);
	}

	@After
	public void tearDown() {
		helper.tearDown();
	}

	@Test
	public void testDeleteAlbumEntries() throws Exception {
		// List<Album> albumEntries = imageAdminService.getAlbums();
		//
		// for(Album album: albumEntries){
		// imageAdminService.deleteAlbum(album);
		// }
	}

	// @Test
	public void testPhoto() throws Exception {
		String albumName = "Foo Album" + Math.random();

		Album newAlbum = new Album();
		newAlbum.setTitle(albumName);

		newAlbum = imageAdminService.addAlbum(newAlbum);

		String photoName = "Foo Photo" + Math.random();

		Photo newPhoto = new Photo();
		newPhoto.setTitle(photoName);
		newPhoto.setAlbumId(newAlbum.getId());

		newPhoto = imageAdminService.addPhoto(newPhoto, Base64.decode(PHOTO_DEMO_DATA), PHOTO_DEMO_MEDIA_TYPE);

		List<Photo> photoEntries = imageAdminService.getPhotos(newAlbum);

		boolean founded = false;
		for (Photo photo : photoEntries) {
			if (photo.getId().equals(newPhoto.getId())) {
				founded = true;
				break;
			}
		}

		imageAdminService.deletePhoto(newPhoto);
		imageAdminService.deleteAlbum(newAlbum);

		if (!founded) {
			throw new Exception("Photo not founded");
		}
	}

	// @Test
	public void testAlbum() throws Exception {
		String albumName = "Foo Album" + Math.random();

		Album newAlbum = new Album();
		newAlbum.setTitle(albumName);

		newAlbum = imageAdminService.addAlbum(newAlbum);

		List<Album> albumEntries = imageAdminService.getAlbums();

		boolean founded = false;
		for (Album album : albumEntries) {
			if (album.getId().equals(newAlbum.getId())) {
				founded = true;
				break;
			}
		}

		imageAdminService.deleteAlbum(newAlbum);

		if (!founded) {
			throw new Exception("Album not founded");
		}
	}

	@Test
	public void testCreateAvatarImage() throws Exception {
		byte[] imageData = imageAdminService.createAvatarImage(readBigTestImage());

		Image image = ImagesServiceFactory.makeImage(imageData);

		if(image.getHeight()>image.getWidth()){
			Assert.assertEquals(Integer.parseInt(AlvsanandProperties.getProperty(Globals.AVATAR_IMAGE_HEIGHT_CONFIG_KEY)), image.getHeight());
			}
		else{
			Assert.assertEquals(Integer.parseInt(AlvsanandProperties.getProperty(Globals.AVATAR_IMAGE_WIDTH_CONFIG_KEY)), image.getWidth());
		}		
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
