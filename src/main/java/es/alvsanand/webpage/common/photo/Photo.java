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
package es.alvsanand.webpage.common.photo;

import java.io.Serializable;
import java.util.Date;

import com.google.gdata.data.PlainTextConstruct;
import com.google.gdata.data.photos.PhotoEntry;

public class Photo implements Serializable, Comparable<Photo> {
	/**
	 * 
	 */
	private static final long serialVersionUID = -7935029466396008564L;

	private String title;
	private String description;
	private String id;
	private String albumId;
	private String thumbnailUrl;
	private String url;
	private Date date;

	public Photo(){
	}

	public Photo(PhotoEntry photoEntry){
		if (photoEntry == null) {
			throw new IllegalArgumentException();
		}
		
		setId(photoEntry.getGphotoId());
		setAlbumId(photoEntry.getAlbumId());
		if(photoEntry.getTitle()!=null){
			setTitle(photoEntry.getTitle().getPlainText());
		}
		if(photoEntry.getDescription()!=null){
			setDescription(photoEntry.getDescription().getPlainText());
		}
		if(photoEntry.getFeaturedDate()!=null){
			setDate(photoEntry.getFeaturedDate());
		}
		if(photoEntry.getMediaThumbnails()!=null && photoEntry.getMediaThumbnails().size()>0){
			setThumbnailUrl(photoEntry.getMediaThumbnails().get(0).getUrl());
		}
		if(photoEntry.getMediaContents()!=null && photoEntry.getMediaContents().size()>0){
			setUrl(photoEntry.getMediaContents().get(0).getUrl());
		}
	}
	
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getThumbnailUrl() {
		return thumbnailUrl;
	}

	public void setThumbnailUrl(String thumbnailUrl) {
		this.thumbnailUrl = thumbnailUrl;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getAlbumId() {
		return albumId;
	}

	public void setAlbumId(String albumId) {
		this.albumId = albumId;
	}

	@Override
	public int compareTo(Photo galleryAlbum) {
		if(galleryAlbum.getDate()==null && date!=null){
			return 1;
		}
		if(galleryAlbum.getDate()!=null && date==null){
			return -1;
		}
		if(galleryAlbum.getDate()!=null && date!=null && date.compareTo(galleryAlbum.getDate()) != 0){
			return date.compareTo(galleryAlbum.getDate());
		}
		
		if(galleryAlbum.getTitle()==null && title!=null){
			return 1;
		}
		if(galleryAlbum.getTitle()!=null && title==null){
			return -1;
		}
		if(galleryAlbum.getTitle()!=null && title!=null && title.compareTo(galleryAlbum.getTitle()) != 0){
			return title.compareTo(galleryAlbum.getTitle());
		}
		
		return 0;
	}
	
	public PhotoEntry toPhotoEntry(){
		PhotoEntry photoEntry = new PhotoEntry();		

		photoEntry.setGphotoId(id);		
		photoEntry.setAlbumId(albumId);		
		photoEntry.setTitle(new PlainTextConstruct(title));
		photoEntry.setDescription(new PlainTextConstruct(description));
		
		return photoEntry;
	}
	
	public void updatePhotoEntry(PhotoEntry photoEntry){
		photoEntry.setGphotoId(id);	
		photoEntry.setAlbumId(albumId);	
		photoEntry.setTitle(new PlainTextConstruct(title));
		photoEntry.setDescription(new PlainTextConstruct(description));
	}

	@Override
	public String toString() {
		return "Photo [title=" + title + ", description=" + description + ", id=" + id + ", albumId=" + albumId + ", thumbnailUrl=" + thumbnailUrl
				+ ", url=" + url + ", date=" + date + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((albumId == null) ? 0 : albumId.hashCode());
		result = prime * result + ((date == null) ? 0 : date.hashCode());
		result = prime * result + ((description == null) ? 0 : description.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((thumbnailUrl == null) ? 0 : thumbnailUrl.hashCode());
		result = prime * result + ((title == null) ? 0 : title.hashCode());
		result = prime * result + ((url == null) ? 0 : url.hashCode());
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
		Photo other = (Photo) obj;
		if (albumId == null) {
			if (other.albumId != null) {
				return false;
			}
		} else if (!albumId.equals(other.albumId)) {
			return false;
		}
		if (date == null) {
			if (other.date != null) {
				return false;
			}
		} else if (!date.equals(other.date)) {
			return false;
		}
		if (description == null) {
			if (other.description != null) {
				return false;
			}
		} else if (!description.equals(other.description)) {
			return false;
		}
		if (id == null) {
			if (other.id != null) {
				return false;
			}
		} else if (!id.equals(other.id)) {
			return false;
		}
		if (thumbnailUrl == null) {
			if (other.thumbnailUrl != null) {
				return false;
			}
		} else if (!thumbnailUrl.equals(other.thumbnailUrl)) {
			return false;
		}
		if (title == null) {
			if (other.title != null) {
				return false;
			}
		} else if (!title.equals(other.title)) {
			return false;
		}
		if (url == null) {
			if (other.url != null) {
				return false;
			}
		} else if (!url.equals(other.url)) {
			return false;
		}
		return true;
	}
}
