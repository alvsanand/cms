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

import es.alvsanand.webpage.db.QueryBean;
import es.alvsanand.webpage.model.User;
import es.alvsanand.webpage.services.ServiceException;

/**
 * This services realizes testing operations.
 *
 * @author alvaro.santos
 * @date 18/11/2009
 *
 */
public interface UserAdminService {	
	public void saveOrUpdateUser(User user) throws ServiceException;
	public void saveOrUpdateUsers(List<User> userList) throws ServiceException;
	public List<User> getUsers(String actualUser, QueryBean queryBean) throws ServiceException;
	public User getUser(User user) throws ServiceException;
	public void deleteUser(User user) throws ServiceException;
	public int getUserCount(String name) throws ServiceException;
	public int getUserCount() throws ServiceException;
}
