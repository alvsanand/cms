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

import es.alvsanand.webpage.common.Logger;
import es.alvsanand.webpage.db.QueryBean;
import es.alvsanand.webpage.db.dao.DAOException;
import es.alvsanand.webpage.db.dao.admin.UserDAO;
import es.alvsanand.webpage.db.dao.admin.UserDAOImpl;
import es.alvsanand.webpage.model.User;
import es.alvsanand.webpage.services.ServiceException;

/**
 * This class implements the service Tag
 *
 * @author alvaro.santos
 * @date 18/11/2009
 *
 */
public class UserAdminServiceImpl implements UserAdminService{
	private final static Logger logger = new Logger(UserAdminServiceImpl.class);
	
	private UserDAO userDAO = new UserDAOImpl();

	public void saveOrUpdateUser(User user) throws ServiceException{
		try {		
			logger.debug("Saving user[" + ((user!=null)?user:"") + "]");
			
			userDAO.saveOrUpdateUser(user);
		} catch (DAOException e) {
			logger.error("Error saving user",e);
			
			throw new ServiceException(e);
		}
	}

	public void saveOrUpdateUsers(List<User> userList) throws ServiceException{
		try {		
			logger.debug("Saving users[" + ((userList!=null)?userList:"") + "]");
			
			userDAO.saveOrUpdateUsers(userList);
		} catch (DAOException e) {
			logger.error("Error saving users",e);
			
			throw new ServiceException(e);
		}
	}

	public User getUser(User user) throws ServiceException{
		try {
			logger.debug("Getting user[" + ((user!=null)?user:"") + "]");
			
			return userDAO.getUser(user);
		} catch (DAOException e) {
			logger.error("Error getting user",e);
			
			throw new ServiceException(e);
		}
	}

	public int getUserCount(String loginName) throws ServiceException{
		try {
			logger.debug("Getting count of user[" + ((loginName!=null)?loginName:"") + "]");
			
			return userDAO.getUserCountByLoginName(loginName);
		} catch (DAOException e) {
			logger.error("Error count of user",e);
			
			throw new ServiceException(e);
		}
	}

	public int getUserCount() throws ServiceException{
		try {
			logger.debug("Getting count of user");
			
			return userDAO.getUserCount();
		} catch (DAOException e) {
			logger.error("Error count of user",e);
			
			throw new ServiceException(e);
		}
	}

	public void deleteUser(User user) throws ServiceException{
		try {
			logger.debug("Deleting user[" + ((user!=null)?user:"") + "]");
			
			userDAO.deleteUser(user);
		} catch (DAOException e) {
			logger.error("Error deleting user",e);
			
			throw new ServiceException(e);
		}
	}

	public List<User> getUsers(String actualUser, QueryBean queryBean) throws ServiceException{
		try {
			logger.debug("Getting users[" + ((queryBean!=null)?queryBean:"") + "]");
			
			return userDAO.getUsersExceptActual(actualUser, queryBean);
		} catch (DAOException e) {
			logger.error("Error getting users",e);
			
			throw new ServiceException(e);
		}
	}
}
