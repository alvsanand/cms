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
package es.alvsanand.webpage.services.session;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.cache.Cache;
import javax.cache.CacheException;
import javax.cache.CacheFactory;
import javax.cache.CacheManager;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.datanucleus.util.StringUtils;

import com.google.appengine.api.datastore.Blob;

import es.alvsanand.webpage.common.AlvsanandProperties;
import es.alvsanand.webpage.common.Globals;
import es.alvsanand.webpage.common.Logger;
import es.alvsanand.webpage.common.MessageResources;
import es.alvsanand.webpage.db.QueryBean;
import es.alvsanand.webpage.db.dao.DAOException;
import es.alvsanand.webpage.db.dao.admin.UserDAO;
import es.alvsanand.webpage.db.dao.admin.UserDAOImpl;
import es.alvsanand.webpage.model.Avatar;
import es.alvsanand.webpage.model.User;
import es.alvsanand.webpage.services.ServiceException;
import es.alvsanand.webpage.services.admin.ImageAdminService;
import es.alvsanand.webpage.services.admin.ImageAdminServiceImpl;
import es.alvsanand.webpage.services.admin.WebAdminService;
import es.alvsanand.webpage.services.admin.WebAdminServiceImpl;

/**
 * This class implements the service User
 * 
 * @author alvaro.santos
 * @date 18/11/2009
 * 
 */
public class UserServiceImpl implements UserService {
	private final static Logger logger = new Logger(UserServiceImpl.class);

	private UserDAO userDAO = new UserDAOImpl();

	private transient WebAdminService webAdminService = new WebAdminServiceImpl();
	
	private transient ImageAdminService imageAdminService = new ImageAdminServiceImpl();

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * es.alvsanand.webpage.services.session.UserService#saveUser(es.alvsanand
	 * .webpage.model.User)
	 */
	public void saveUser(User user) throws ServiceException {
		try {
			logger.debug("Saving user[" + ((user != null) ? user : "") + "]");

			if(user.getCreationdate()==null){
				user.setCreationdate(new java.util.Date());
			}
			if(user.getAvatar()!=null && user.getAvatar().getData()!=null){
				byte[] photoData = imageAdminService.createAvatarImage(user.getAvatar().getData().getBytes());
				
				user.getAvatar().setData(new Blob(photoData));
				
				removeAvatFromCache(user);
			}			
			
			userDAO.saveOrUpdateUser(user);
		} catch (DAOException e) {
			logger.error("Error saving user", e);

			throw new ServiceException(e);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * es.alvsanand.webpage.services.session.UserService#getUser(es.alvsanand
	 * .webpage.model.User)
	 */
	public User getUser(User user) throws ServiceException {
		try {
			logger.debug("Getting user[" + ((user != null) ? user : "") + "]");

			return userDAO.getUser(user);
		} catch (DAOException e) {
			logger.error("Error getting user", e);

			throw new ServiceException(e);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * es.alvsanand.webpage.services.session.UserService#getUserByRegistrationHash(java.lang.String
	 * )
	 */
	public User getUserByRegistrationHash(String registrationHash) throws ServiceException {
		try {
			logger.debug("Getting user by registrationHash[" + ((registrationHash != null) ? registrationHash : "") + "]");

			return userDAO.getUserByRegistrationHash(registrationHash);
		} catch (DAOException e) {
			logger.error("Error getting user by registrationHash", e);

			throw new ServiceException(e);
		}
	}

	/* (non-Javadoc)
	 * @see es.alvsanand.webpage.services.session.UserService#getUserByLoginName(java.lang.String)
	 */
	public User getUserByLoginName(String loginName) throws ServiceException {
		try {
			logger.debug("Getting user by loginName[" + ((loginName != null) ? loginName : "") + "]");

			return userDAO.getUserByLoginName(loginName);
		} catch (DAOException e) {
			logger.error("Error getting user by loginName", e);

			throw new ServiceException(e);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * es.alvsanand.webpage.services.session.UserService#delete(es.alvsanand
	 * .webpage.model.User)
	 */
	public void delete(User user) throws ServiceException {
		try {
			logger.debug("Deleting user[" + ((user != null) ? user : "") + "]");

			userDAO.deleteUser(user);
		} catch (DAOException e) {
			logger.error("Error deleting user", e);

			throw new ServiceException(e);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * es.alvsanand.webpage.services.session.UserService#getUsers(es.alvsanand
	 * .webpage.db.QueryBean)
	 */
	public List<User> getUsers(QueryBean queryBean) throws ServiceException {
		try {
			logger.debug("Getting users[" + ((queryBean != null) ? queryBean : "") + "]");

			return userDAO.getUsers(queryBean);
		} catch (DAOException e) {
			logger.error("Error getting users", e);

			throw new ServiceException(e);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * es.alvsanand.webpage.services.session.UserService#getUserCount(java.lang
	 * .String)
	 */
	public int getUserCount(String loginName) throws ServiceException {
		try {
			logger.debug("Getting count of user[" + ((loginName != null) ? loginName : "") + "]");

			return userDAO.getUserCountByLoginName(loginName);
		} catch (DAOException e) {
			logger.error("Error count of user", e);

			throw new ServiceException(e);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * es.alvsanand.webpage.services.session.UserService#sendActivationEmail
	 * (es.alvsanand.webpage.model.User)
	 */
	public void sendActivationEmail(User user) throws ServiceException {
		try {
			logger.debug("Sending activation email[" + ((user != null) ? user : "") + "]");

			if (user == null) {
				throw new IllegalArgumentException();
			}

			Properties props = new Properties();
			Session session = Session.getDefaultInstance(props, null);

			String domain = AlvsanandProperties.getProperty(Globals.DOMAIN_CONFIG_KEY);
			String contextPath = AlvsanandProperties.getProperty(Globals.CONTEXT_CONFIG_KEY);

			String activationUrl = MessageFormat.format(Globals.ACTIVATION_URL_FORMAT, new String[] { domain, contextPath, user.getRegistrationHash() });

			String subject = MessageResources.getMessage(MessageResources.REGISTRATION_RESOURCE_BUNDLE_NAME, "registration.activation.email.subject",
					null);
			String bodyText = MessageResources.getMessage(MessageResources.REGISTRATION_RESOURCE_BUNDLE_NAME, "registration.activation.email.body",
					new String[] { activationUrl });
			String sender = AlvsanandProperties.getProperty(Globals.EMAIL_CONFIG_KEY);

			Message message = new MimeMessage(session);

			message.setFrom(new InternetAddress(sender));
			message.addRecipient(Message.RecipientType.TO, new InternetAddress(user.getEmail()));

			message.setSubject(subject);
			message.setText(bodyText);

			webAdminService.sentEmail(message);
		} catch (AddressException e) {
			logger.error("Error sending activation email", e);

			throw new ServiceException(e);
		} catch (MessagingException e) {
			logger.error("Error sending activation email", e);

			throw new ServiceException(e);
		}
	}

	/* (non-Javadoc)
	 * @see es.alvsanand.webpage.services.session.UserService#sendResetPasswordEmail(es.alvsanand.webpage.model.User, java.lang.String)
	 */
	public void sendResetPasswordEmail(User user, String plainPassword) throws ServiceException {
		try {
			logger.debug("Sending reset password email[" + ((user != null) ? user : "") + "]");

			if (user == null || StringUtils.isEmpty(plainPassword)) {
				throw new IllegalArgumentException();
			}

			Properties props = new Properties();
			Session session = Session.getDefaultInstance(props, null);

			String subject = MessageResources.getMessage(MessageResources.REGISTRATION_RESOURCE_BUNDLE_NAME, "registration.resotrePassword.email.subject",
					null);
			String bodyText = MessageResources.getMessage(MessageResources.REGISTRATION_RESOURCE_BUNDLE_NAME, "registration.resotrePassword.email.body",
					new String[] { user.getLoginName(), plainPassword });
			String sender = AlvsanandProperties.getProperty(Globals.EMAIL_CONFIG_KEY);

			Message message = new MimeMessage(session);

			message.setFrom(new InternetAddress(sender));
			message.addRecipient(Message.RecipientType.TO, new InternetAddress(user.getEmail()));

			message.setSubject(subject);
			message.setText(bodyText);

			webAdminService.sentEmail(message);
		} catch (AddressException e) {
			logger.error("Error sending reset password email", e);

			throw new ServiceException(e);
		} catch (MessagingException e) {
			logger.error("Error sending reset password email", e);

			throw new ServiceException(e);
		}
	}
	
	private void removeAvatFromCache(User user){
		Cache cache;

		try {
			logger.debug("Removing avatar of user[" + user +"] from cache.");
			
			CacheFactory cacheFactory = CacheManager.getInstance().getCacheFactory();
			cache = cacheFactory.createCache(Collections.emptyMap());
			
			{
				Map<String, Avatar> articlesMap = (Map<String, Avatar>)cache.get(Globals.AVATAR_BY_ID_USER_CACHE_NAME);
				
				if(articlesMap!=null){
					List<String> avatarToDelete = new ArrayList<String>();
					for(String idUser: articlesMap.keySet()){
						Avatar avatar = articlesMap.get(idUser);
						
						if(avatar!=null){
							avatarToDelete.add(idUser);
							break;
						}
					}
					
					for(String idUser: avatarToDelete){
						articlesMap.remove(idUser);
					}
					
					cache.put(Globals.AVATAR_BY_ID_USER_CACHE_NAME, articlesMap);
				}
			}
		} catch (CacheException cacheException) {
			logger.error("Error removing avatar of user[" + user +"] from cache",cacheException);
		}
	}
}