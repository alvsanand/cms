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

import java.util.Collections;

import javax.cache.Cache;
import javax.cache.CacheException;
import javax.cache.CacheFactory;
import javax.cache.CacheManager;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Transport;

import es.alvsanand.webpage.common.Globals;
import es.alvsanand.webpage.common.Logger;
import es.alvsanand.webpage.services.ServiceException;

/**
 * This class implements the service Tag
 * 
 * @author alvaro.santos
 * @date 18/11/2009
 * 
 */
public class WebAdminServiceImpl implements WebAdminService {
	private final static Logger logger = new Logger(WebAdminServiceImpl.class);

	/*
	 * (non-Javadoc)
	 * 
	 * @see es.alvsanand.webpage.services.admin.WebAdminService#eraseCache()
	 */
	public void eraseCache() {
		Cache cache;

		try {
			logger.debug("Erasing cache data");

			CacheFactory cacheFactory = CacheManager.getInstance().getCacheFactory();
			cache = cacheFactory.createCache(Collections.emptyMap());

			for (String cacheName : Globals.CAHE_NAMES) {
				cache.remove(cacheName);
			}
		} catch (CacheException cacheException) {
			logger.error("Error removing erasing cache data.", cacheException);
		}
	}

	/* (non-Javadoc)
	 * @see es.alvsanand.webpage.services.admin.WebAdminService#sentEmail(javax.mail.Message)
	 */
	public void sentEmail(Message message) throws ServiceException {
		try {
			logger.debug("Sending email");

			Transport.send(message);
		} catch (MessagingException messagingException) {
			logger.error("Error sending email.", messagingException);

			throw new ServiceException(messagingException);
		}
	}
}
