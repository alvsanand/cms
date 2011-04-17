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
package es.alvsanand.webpage.common.resources;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.Collections;
import java.util.Enumeration;
import java.util.Locale;
import java.util.Properties;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import es.alvsanand.webpage.common.Logger;

public class XMLResourceBundle extends ResourceBundle {
	private final static Logger logger = new Logger(XMLResourceBundle.class);
	
	private static final ConcurrentMap<CacheKey, ResourceBundle> cacheList = new ConcurrentHashMap<CacheKey, ResourceBundle>();

	private static String XML = "xml";

	private Properties props;

	XMLResourceBundle(InputStream stream) throws IOException {
		props = new Properties();
		props.loadFromXML(stream);
	}

	protected Object handleGetObject(String key) {
		return props.getProperty(key);
	}

	public Enumeration<String> getKeys() {
		Set<String> handleKeys = props.stringPropertyNames();
		return Collections.enumeration(handleKeys);
	}

	public static ResourceBundle getBundle(String baseName, Locale locale, ClassLoader loader) {
		if (loader == null) {
			throw new NullPointerException();
		}

		CacheKey cacheKey = new CacheKey(baseName, locale);

		if (cacheList.containsKey(cacheKey)) {
			return cacheList.get(cacheKey);
		} else {
			try {
				ResourceBundle resourceBundle = newBundle(baseName, locale, XML, loader, true);
				
				if(resourceBundle!=null){				
					cacheList.put(cacheKey, resourceBundle);
					return cacheList.get(cacheKey);
				}
				else{
					resourceBundle = newBundle(baseName, new Locale(locale.getLanguage(), locale.getCountry()), XML, loader, true);
					
					if(resourceBundle!=null){				
						cacheList.put(cacheKey, resourceBundle);
						return cacheList.get(cacheKey);
					}
					else{
						resourceBundle = newBundle(baseName, new Locale(locale.getLanguage()), XML, loader, true);
						
						if(resourceBundle!=null){				
							cacheList.put(cacheKey, resourceBundle);
							return cacheList.get(cacheKey);
						}
					}
				}
			} catch (IllegalAccessException e) {
				logger.error("Error getting ResourceBundle[baseName=" + baseName + ", locale=" + locale + "]", e);
			} catch (InstantiationException e) {
				logger.error("Error getting ResourceBundle[baseName=" + baseName + ", locale=" + locale + "]", e);
			} catch (IOException e) {
				logger.error("Error getting ResourceBundle[baseName=" + baseName + ", locale=" + locale + "]", e);
			}
			return null;
		}
	}

	static ResourceBundle newBundle(String baseName, Locale locale, String format, ClassLoader loader, boolean reload) throws IllegalAccessException,
			InstantiationException, IOException {

		if ((baseName == null) || (locale == null) || (format == null) || (loader == null)) {
			throw new NullPointerException();
		}
		ResourceBundle bundle = null;
		if (!format.equals(XML)) {
			return null;
		}

		String bundleName = toBundleName(baseName, locale);
		String resourceName = toResourceName(bundleName, format);
		
		URL url = loader.getResource(resourceName);
		if (url == null) {
			return null;
		}
		URLConnection connection = url.openConnection();
		if (connection == null) {
			return null;
		}
		if (reload) {
			connection.setUseCaches(false);
		}
		InputStream stream = connection.getInputStream();
		if (stream == null) {
			return null;
		}
		BufferedInputStream bis = new BufferedInputStream(stream);
		bundle = new XMLResourceBundle(bis);
		bis.close();

		return bundle;
	}

	static String toBundleName(String baseName, Locale locale) {
		if (locale == Locale.ROOT) {
			return baseName;
		}

		String language = locale.getLanguage();
		String country = locale.getCountry();
		String variant = locale.getVariant();

		if (language == "" && country == "" && variant == "") {
			return baseName;
		}

		StringBuilder sb = new StringBuilder(baseName);
		sb.append('_');
		if (variant != "") {
			sb.append(language).append('_').append(country).append('_').append(variant);
		} else if (country != "") {
			sb.append(language).append('_').append(country);
		} else {
			sb.append(language);
		}
		return sb.toString();

	}

	static final String toResourceName(String bundleName, String suffix) {
		StringBuilder sb = new StringBuilder(bundleName.length() + 1 + suffix.length());
		sb.append(bundleName.replace('.', '/')).append('.').append(suffix);
		return sb.toString();
	}
}