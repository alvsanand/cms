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
package es.alvsanand.webpage.web.beans.admin;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.net.FileNameMap;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;

import org.apache.commons.lang.StringUtils;
import org.primefaces.event.FileUploadEvent;

import es.alvsanand.webpage.AlvsanandException;
import es.alvsanand.webpage.common.FacesUtils;
import es.alvsanand.webpage.common.Globals;
import es.alvsanand.webpage.common.Logger;
import es.alvsanand.webpage.common.MessageResources;
import es.alvsanand.webpage.common.photo.Album;
import es.alvsanand.webpage.common.photo.Photo;
import es.alvsanand.webpage.services.admin.ImageAdminService;
import es.alvsanand.webpage.services.admin.ImageAdminServiceImpl;

@SessionScoped
@ManagedBean(name = "imageBean")
public class ImageBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5993666590870210230L;

	private transient static final Logger logger = new Logger(ImageBean.class);

	private transient static final String LIST_ALBUMS_VIEW_ID = "pretty:listAlbums";
	private transient static final String LIST_PHOTOS_VIEW_ID = "/xhtml/secured/admin/images/listPhoto.xhtml";

	private transient ImageAdminService imageAdminService;

	private String photoId;

	private String photoTitle;

	private String photoDescription;

	private String albumId;

	private String albumTitle;

	private String albumDescription;

	private List<Album> albums;

	private List<Photo> photos;

	private byte[] photoData;
	
	private String photoMediaType;
	
	private boolean galleryAlbum;

	public ImageBean() {
	}

	public ImageAdminService getImageAdminService() {
		if (imageAdminService == null) {
			imageAdminService = new ImageAdminServiceImpl();
		}
		return imageAdminService;
	}

	public String getPhotoId() {
		return photoId;
	}

	public void setPhotoId(String photoId) {
		this.photoId = photoId;
	}

	public String getPhotoTitle() {
		return photoTitle;
	}

	public void setPhotoTitle(String photoTitle) {
		this.photoTitle = photoTitle;
	}

	public String getPhotoDescription() {
		return photoDescription;
	}

	public void setPhotoDescription(String photoDescription) {
		this.photoDescription = photoDescription;
	}

	public String getAlbumId() {
		return albumId;
	}

	public void setAlbumId(String albumId) {
		this.albumId = albumId;
	}

	public String getAlbumTitle() {
		return albumTitle;
	}

	public void setAlbumTitle(String albumTitle) {
		this.albumTitle = albumTitle;
	}

	public String getAlbumDescription() {
		return albumDescription;
	}

	public void setAlbumDescription(String albumDescription) {
		this.albumDescription = albumDescription;
	}

	public List<Album> getAlbums() {
		return albums;
	}

	public void setAlbums(List<Album> albums) {
		this.albums = albums;
	}

	public List<Photo> getPhotos() {
		return photos;
	}

	public void setPhotos(List<Photo> photos) {
		this.photos = photos;
	}

	public byte[] getPhotoData() {
		return photoData;
	}

	public void setPhotoData(byte[] photoData) {
		this.photoData = photoData;
	}

	public String getPhotoMediaType() {
		return photoMediaType;
	}

	public void setPhotoMediaType(String photoMediaType) {
		this.photoMediaType = photoMediaType;
	}

	public boolean isGalleryAlbum() {
		return galleryAlbum;
	}

	public void setGalleryAlbum(boolean galleryAlbum) {
		this.galleryAlbum = galleryAlbum;
	}

	// JSF methods

	public void loadAlbums() throws AlvsanandException {
		logger.info("Launched AlbumBean.loadAlbums");
		
		albums = getImageAdminService().getAlbums();
		photos = null;
		
		albumId = null;
		albumTitle = null;
		albumDescription = null;
		galleryAlbum = false;
		
		photoId = null;
		photoTitle = null;
		photoDescription = null;
		
		photoData = null;
		photoMediaType = null;
	}

	public void newAlbum() throws AlvsanandException {
		logger.info("Launched AlbumBean.newAlbum");

		albumId = null;
		albumTitle = null;
		albumDescription = null;
		galleryAlbum = false;
	}

	public void editAlbum() throws AlvsanandException {
		logger.info("Launched AlbumBean.editAlbum[" + albumId + "]");

		Album _album = new Album();
		_album.setId(albumId);

		Album album = getImageAdminService().getAlbum(_album);

		if(album.getDescription()!=null && album.getDescription().indexOf(Globals.GALLERY_ALBUM_MEDIA_KEYWORD)==0){
			albumDescription = album.getDescription().substring(Globals.GALLERY_ALBUM_MEDIA_KEYWORD.length());
			galleryAlbum = true;
		}
		else{
			albumDescription = album.getDescription();	
			galleryAlbum = false;		
		}
		
		albumTitle = album.getTitle();
	}

	public String deleteAlbum() throws AlvsanandException {
		logger.info("Launched AlbumBean.deleteAlbum[" + albumId + "]");

		Album album = new Album();
		album.setId(albumId);

		getImageAdminService().deleteAlbum(album);

		return LIST_ALBUMS_VIEW_ID;
	}

	public void addAlbum() throws AlvsanandException {
		logger.info("Launched AlbumBean.addAlbum[" + albumTitle + "]");

		Album album = new Album();
		album.setTitle(albumTitle);
		album.setId(albumId);
		
		if(galleryAlbum){
			album.setDescription(Globals.GALLERY_ALBUM_MEDIA_KEYWORD + albumDescription);
		}
		else{
			album.setDescription(albumDescription);
		}
		
		getImageAdminService().addAlbum(album);
		
		loadAlbums();
	}

	public void updateAlbum() throws AlvsanandException {
		logger.info("Launched AlbumBean.updateAlbum[" + albumTitle + "]");

		Album album = new Album();
		album.setId(albumId);
		album.setTitle(albumTitle);
		
		if(galleryAlbum){
			album.setDescription(Globals.GALLERY_ALBUM_MEDIA_KEYWORD + albumDescription);
		}
		else{
			album.setDescription(albumDescription);
		}
		
		getImageAdminService().updateAlbum(album);
		
		loadAlbums();
	}

	public String loadPhotos() throws AlvsanandException {
		logger.info("Launched PhotoBean.loadPhotos");

		Album album = new Album();
		album.setId(albumId);
		
		photos = getImageAdminService().getPhotos(album);

		albumTitle = album.getTitle();
		
		photoId = null;
		photoTitle = null;
		photoDescription = null;
		
		photoData = null;
		photoMediaType = null;
		
		return LIST_PHOTOS_VIEW_ID;
	}

	public void loadPhotosWithoutView() throws AlvsanandException {
		logger.info("Launched PhotoBean.loadPhotos");

		Album album = new Album();
		album.setId(albumId);
		
		photos = getImageAdminService().getPhotos(album);

		albumTitle = album.getTitle();
		
		photoId = null;
		photoTitle = null;
		photoDescription = null;
		
		photoData = null;
		photoMediaType = null;
	}

	public void newPhoto() throws AlvsanandException {
		logger.info("Launched PhotoBean.newPhoto");

		photoId = null;
		photoTitle = null;
		photoDescription = null;
		
		photoData = null;
		photoMediaType = null;
	}

	public void editPhoto() throws AlvsanandException {
		logger.info("Launched PhotoBean.editPhoto[" + photoId + "]");

		Photo _photo = new Photo();
		_photo.setId(photoId);
		_photo.setAlbumId(albumId);
		
		Photo photo = getImageAdminService().getPhoto(_photo);

		photoDescription = photo.getDescription();
		photoTitle = photo.getTitle();
		
		photoData = null;
		photoMediaType = null;
	}

	public String deletePhoto() throws AlvsanandException {
		logger.info("Launched PhotoBean.deletePhoto[" + photoId + "]");

		Photo _photo = new Photo();
		_photo.setId(photoId);
		_photo.setAlbumId(albumId);
		
		Photo photo = getImageAdminService().getPhoto(_photo);

		getImageAdminService().deletePhoto(photo);

		return LIST_PHOTOS_VIEW_ID;
	}

	public void addPhoto() throws AlvsanandException {
		logger.info("Launched PhotoBean.addPhoto[" + photoTitle + "]");

		if(photoData==null){
			FacesMessage message = new FacesMessage();
			message.setDetail(MessageResources.getMessage(MessageResources.ERROR_RESOURCE_BUNDLE_NAME, "error.admin.image.fileUpload.detail", null));
			message.setSummary(MessageResources.getMessage(MessageResources.ERROR_RESOURCE_BUNDLE_NAME, "error.admin.image.fileUpload.summary", null));
			message.setSeverity(FacesMessage.SEVERITY_ERROR);
			
			FacesContext.getCurrentInstance().addMessage(null, message);
			
			return;
		}
		
		Photo photo = new Photo();
		photo.setTitle(photoTitle);
		photo.setDescription(photoDescription);
		photo.setAlbumId(albumId);
		
		getImageAdminService().addPhoto(photo, photoData, photoMediaType);
		
		loadPhotos();
	}

	public void updatePhoto() throws AlvsanandException {
		logger.info("Launched PhotoBean.updatePhoto[" + photoTitle + "]");

		Photo photo = new Photo();
		
		photo.setId(photoId);
		photo.setAlbumId(albumId);
		photo.setTitle(photoTitle);
		photo.setDescription(photoDescription);
		photo.setAlbumId(albumId);
		
		getImageAdminService().updatePhoto(photo);
		
		loadPhotos();
	}

	public void handleFileUpload(FileUploadEvent fileUploadEvent) {
		logger.info("Uploaded: {" + fileUploadEvent.getFile().getFileName() + "}");

		FacesContext context = FacesContext.getCurrentInstance();
		UIComponent fileUploadComponent = FacesUtils.findComponent(context.getViewRoot(), "fileUpload");
		
		InputStream inputStream = null;
		try {
			inputStream = fileUploadEvent.getFile().getInputstream();

			ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

			byte[] buf = new byte[1024];

			int length;

			while ((length = inputStream.read(buf)) > 0) {
				outputStream.write(buf, 0, length);
			}

			inputStream.close();

			photoData = outputStream.toByteArray();
			
			outputStream.close();
			
			FileNameMap fileNameMap = URLConnection.getFileNameMap();
			
			photoMediaType = fileNameMap.getContentTypeFor(fileUploadEvent.getFile().getFileName());
			
			FacesMessage message = new FacesMessage();
			message.setDetail(MessageResources.getMessage(MessageResources.ADMIN_RESOURCE_BUNDLE_NAME, "admin.uploadedFile.message.detail", null));
			message.setSummary(MessageResources.getMessage(MessageResources.ADMIN_RESOURCE_BUNDLE_NAME, "admin.uploadedFile.message.summary", null));
			message.setSeverity(FacesMessage.SEVERITY_INFO);
			
			FacesContext.getCurrentInstance().addMessage(null, message);
		} catch (IOException ioException) {
			FacesMessage message = new FacesMessage();
			message.setDetail(MessageResources.getMessage(MessageResources.ERROR_RESOURCE_BUNDLE_NAME, "error.form.validation.loadArticleBulkUpload.badFile.detail", null));
			message.setSummary(MessageResources.getMessage(MessageResources.ERROR_RESOURCE_BUNDLE_NAME, "error.form.validation.loadArticleBulkUpload.badFile.summary", null));
			message.setSeverity(FacesMessage.SEVERITY_ERROR);

			FacesContext.getCurrentInstance().addMessage(fileUploadComponent.getClientId(context), message);
			return;
		} catch (Exception exception) {
			logger.error("Error uploading file:", exception);
			
			return;
		}
	}
	
	public boolean getCouldUpdateImage(){
		if(StringUtils.isNotEmpty(this.getPhotoId())){
			return true;
		}
		return false;
	}
	
	public boolean getCouldAddImage(){
		if(StringUtils.isEmpty(this.getPhotoId()) && photoData != null){
			return true;
		}
		return false;
	}
	
	public List<?> getEntries(){
		if(getPhotos()!=null){
			return getPhotos();
		}
		else{
			return getAlbumsForArticles();
		}
	}

	public List<Album> getAlbumsForArticles() {
		List<Album> albums = new ArrayList<Album>();
		
		if(this.albums!=null){		
			for(Album album: this.albums){
				if(album.getDescription()!=null && album.getDescription().indexOf(Globals.GALLERY_ALBUM_MEDIA_KEYWORD)!=0){
					albums.add(album);
				}
			}
		}
		
		return albums;
	}
}