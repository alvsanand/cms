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
package es.alvsanand.webpage.services.admin;

import java.util.List;

import es.alvsanand.webpage.services.ServiceException;


public interface ImageAdminService {
	public es.alvsanand.webpage.common.photo.Album getAlbum(es.alvsanand.webpage.common.photo.Album album) throws ServiceException;
	
	public es.alvsanand.webpage.common.photo.Album getAlbum(String albumId) throws ServiceException;
	
	public List<es.alvsanand.webpage.common.photo.Album> getAlbums() throws ServiceException;
	
	public es.alvsanand.webpage.common.photo.Album addAlbum(es.alvsanand.webpage.common.photo.Album album) throws ServiceException;
	
	public void updateAlbum(es.alvsanand.webpage.common.photo.Album album) throws ServiceException;
	
	public void deleteAlbum(es.alvsanand.webpage.common.photo.Album album) throws ServiceException;
	
	public es.alvsanand.webpage.common.photo.Photo getPhoto(es.alvsanand.webpage.common.photo.Photo photo) throws ServiceException;
	
	public es.alvsanand.webpage.common.photo.Photo getPhoto(String albumId, String photoId) throws ServiceException;
	
	public List<es.alvsanand.webpage.common.photo.Photo> getPhotos(es.alvsanand.webpage.common.photo.Album album) throws ServiceException;
	
	public es.alvsanand.webpage.common.photo.Photo addPhoto(es.alvsanand.webpage.common.photo.Photo photo, byte[] data, String mediaType) throws ServiceException;
	
	public void updatePhoto(es.alvsanand.webpage.common.photo.Photo photo) throws ServiceException;
	
	public void deletePhoto(es.alvsanand.webpage.common.photo.Photo photo) throws ServiceException;
	
	public byte[] createAvatarImage(byte[] photoData) throws ServiceException;
}
