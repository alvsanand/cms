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
import java.util.List;

import com.google.gdata.data.PlainTextConstruct;
import com.google.gdata.data.photos.AlbumEntry;

public final class Album implements Serializable, Comparable<Album> {
	/**
	 * 
	 */
	private static final long serialVersionUID = -322397257436917883L;
	private String title;
	private String description;
	private String id;
	private Date date;
	
	public Album(){
	}
	
	public Album(AlbumEntry albumEntry){
		if (albumEntry == null) {
			throw new IllegalArgumentException();
		}
		
		setId(albumEntry.getGphotoId());
		if(albumEntry.getTitle()!=null){
			setTitle(albumEntry.getTitle().getPlainText());
		}
		if(albumEntry.getDescription()!=null){
			setDescription(albumEntry.getDescription().getPlainText());
		}
		if(albumEntry.getDate()!=null){
			setDate(albumEntry.getDate());
		}
	}

	private List<Photo> photos;

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

	public List<Photo> getPhotos() {
		return photos;
	}

	public void setPhotos(List<Photo> photos) {
		this.photos = photos;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	@Override
	public int compareTo(Album galleryAlbum) {
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
	
	public AlbumEntry toAlbumEntry(){
		AlbumEntry albumEntry = new AlbumEntry();		

		albumEntry.setGphotoId(id);
		albumEntry.setTitle(new PlainTextConstruct(title));	
		albumEntry.setDescription(new PlainTextConstruct(description));
		
		return albumEntry;
	}
	
	public void updateAlbumEntry(AlbumEntry albumEntry){
		albumEntry.setGphotoId(id);
		albumEntry.setTitle(new PlainTextConstruct(title));
		albumEntry.setDescription(new PlainTextConstruct(description));
	}

	@Override
	public String toString() {
		return "GalleryAlbum [title=" + title + ", description=" + description + ", id=" + id + ", date=" + date + ", photos=" + photos
				+ "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((date == null) ? 0 : date.hashCode());
		result = prime * result + ((description == null) ? 0 : description.hashCode());
		result = prime * result + ((photos == null) ? 0 : photos.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((title == null) ? 0 : title.hashCode());
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
		Album other = (Album) obj;
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
		if (photos == null) {
			if (other.photos != null) {
				return false;
			}
		} else if (!photos.equals(other.photos)) {
			return false;
		}
		if (id == null) {
			if (other.id != null) {
				return false;
			}
		} else if (!id.equals(other.id)) {
			return false;
		}
		if (title == null) {
			if (other.title != null) {
				return false;
			}
		} else if (!title.equals(other.title)) {
			return false;
		}
		return true;
	}
}