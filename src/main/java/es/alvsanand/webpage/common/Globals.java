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
package es.alvsanand.webpage.common;

public class Globals {
	public static final String SES_LOCALE = "locale";
	public static final String SES_USER = "user";
	public static final String SES_AUTHENTICATION = "authentication";
	
	public static final String REQ_PARAMETER_SEPARATOR = ";";
	
	public static final String DATE_SEPARATOR = "-";
	
	public static final int DATA_TABLE_PAGE_SIZE = 10;
	
	public static final String ACTIVATED_ARTICLES_CACHE_NAME = "ActivatedArticles";
	
	public static final String ACTIVATED_ARTICLES_BY_TAG_CACHE_NAME = "ActivatedArticlesByTag";
	
	public static final String ACTIVATED_ARTICLES_BY_DATES_CACHE_NAME = "ActivatedArticlesByDates";
	
	public static final String ACTIVATED_ARTICLES_BY_SEARCH_CACHE_NAME = "ActivatedArticlesBySearch";
	
	public static final String ACTIVATED_ARTICLES_COUNT_CACHE_NAME = "ActivatedArticlesCount";
	
	public static final String ACTIVATED_ARTICLES_TREE_CACHE_NAME = "ActivatedArticlesTree";
	
	public static final String ACTIVATED_TAG_MAP_CACHE_NAME = "ActivatedTagMap";
	
	public static final String ACTIVATED_ARTICLES_BY_ID_CACHE_NAME = "ActivatedArticlesById";
	
	public static final String GALLERY_ALBUMS_CACHE_NAME = "GalleryAlbums";
	
	public static final String AVATAR_BY_ID_USER_CACHE_NAME = "AvatarByIdUser";
	
	public static final String[] ARTICLE_CAHE_NAMES = {
		ACTIVATED_ARTICLES_CACHE_NAME, ACTIVATED_ARTICLES_BY_TAG_CACHE_NAME, ACTIVATED_ARTICLES_BY_DATES_CACHE_NAME,
		ACTIVATED_ARTICLES_BY_SEARCH_CACHE_NAME, ACTIVATED_ARTICLES_COUNT_CACHE_NAME, ACTIVATED_ARTICLES_TREE_CACHE_NAME,
		ACTIVATED_TAG_MAP_CACHE_NAME, ACTIVATED_ARTICLES_BY_ID_CACHE_NAME
	};
	
	public static final String[] CAHE_NAMES = {
		es.alvsanand.webpage.security.config.SecurityConfig.class.getName(),
		ACTIVATED_ARTICLES_CACHE_NAME, ACTIVATED_ARTICLES_BY_TAG_CACHE_NAME, ACTIVATED_ARTICLES_BY_DATES_CACHE_NAME,
		ACTIVATED_ARTICLES_BY_SEARCH_CACHE_NAME, ACTIVATED_ARTICLES_COUNT_CACHE_NAME, ACTIVATED_ARTICLES_TREE_CACHE_NAME,
		ACTIVATED_TAG_MAP_CACHE_NAME, ACTIVATED_ARTICLES_BY_ID_CACHE_NAME, GALLERY_ALBUMS_CACHE_NAME, AVATAR_BY_ID_USER_CACHE_NAME
	};
	
	public static final String GALLERY_ALBUM_MEDIA_KEYWORD = "#Gallery_Album";
	
	public static final String JSESSIONID_ATTRIBUTE_NAME = "JSESSIONID";
	
	public static final String ARTICLE_DATA_DELIMITER = "<hr id=\"separator\"";
	
	public static final String ARTICLE_DATA_DELIMITER_REGEXP = "<hr id=\"separator\"([^>]*)>";
	
	public static final String CHARACTER_ENCODING  = "UTF-8";
	
	public static final String DEFAULT_DATE_FORMAT  = "HH:mm:ss yyyy/MM/dd";
	
	public static final String DEFAULT_LOGGING_FORMAT  = "%L: %m [%c.%M %t]";
	
	public static final String ARTICLE_URL_FORMAT  = "http://{0}{1}/article/{2}";
	
	public static final String ARTICLE_URL_WHITOUT_PROTOCOL_FORMAT  = "{0}{1}/article/{2}";
	
	public static final String ACTIVATION_URL_FORMAT  = "http://{0}{1}/activation/{2}";		
	
	public static final String EMAIL_CONFIG_KEY  = "alvsanand.website.email";
	
	public static final String DOMAIN_CONFIG_KEY  = "alvsanand.website.domain";
	
	public static final String CONTEXT_CONFIG_KEY  = "alvsanand.website.context";
	
	public static final String PICASA_APPLICATION_CONFIG_KEY  = "alvsanand.picasa.application";
	
	public static final String PICASA_USER_CONFIG_KEY  = "alvsanand.picasa.user";
	
	public static final String PICASA_PASS_CONFIG_KEY  = "alvsanand.picasa.pass";
	
	public static final String AVATAR_IMAGE_HEIGHT_CONFIG_KEY  = "alvsanand.avatar.height";
	
	public static final String AVATAR_IMAGE_WIDTH_CONFIG_KEY  = "alvsanand.avatar.width";
}