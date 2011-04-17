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

import java.util.Properties;

import javax.faces.context.FacesContext;

public class AlvsanandProperties {
	/**
	 * The Logger for this class.
	 */
	private transient static final Logger logger = new Logger(AlvsanandProperties.class);

	private static Properties properties = null;

	private final static String WEB_INF_DIR = "/WEB-INF";

	public final static String CONFIG_FILE_NAME = "config.xml";

	private final static String DEFAULT_CONFIG_FILE = WEB_INF_DIR + "/" + CONFIG_FILE_NAME;

	public static String getProperty(String key) {
		if(properties == null && FacesContext.getCurrentInstance()!=null && FacesContext.getCurrentInstance().getExternalContext()!=null){
			try {
				properties = new Properties();
	
				properties.loadFromXML(FacesContext.getCurrentInstance().getExternalContext().getResourceAsStream(DEFAULT_CONFIG_FILE));
			} catch (Exception e) {
				logger.error("Error loading config properties", e);
			}
		}
		
		if (properties == null && key != null)
			return null;
		return properties.getProperty(key);
	}
	
	public static void setProperties(Properties properties){
		AlvsanandProperties.properties = properties;
	}
}
