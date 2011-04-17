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
package test.es.alvsanand.webpage.services;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.tools.development.testing.LocalDatastoreServiceTestConfig;
import com.google.appengine.tools.development.testing.LocalServiceTestHelper;

import es.alvsanand.webpage.model.User;
import es.alvsanand.webpage.services.admin.UserAdminService;
import es.alvsanand.webpage.services.admin.UserAdminServiceImpl;

public class UserAdminServiceTest {
	private final LocalServiceTestHelper helper;

	public UserAdminServiceTest() {
		LocalDatastoreServiceTestConfig localServiceTestConfig = new LocalDatastoreServiceTestConfig();

		localServiceTestConfig.setNoStorage(true);

		helper = new LocalServiceTestHelper(localServiceTestConfig);
	}

	private UserAdminService userAdminService;

	@Before
	public void setUp() {
		helper.setUp();

		userAdminService = new UserAdminServiceImpl();
	}

	@After
	public void tearDown() {
		helper.tearDown();
	}

	@Test
	public void testSaveUser() throws Exception {
		User user = new User();
		user.setLoginName("loginName");
		user.setName("name");
		user.setSurname("surname");

		userAdminService.saveOrUpdateUser(user);

		Assert.assertNotNull(userAdminService.getUser(user));
	}

	@Test
	public void testDeleteUser() throws Exception {
		User user = new User();
		user.setLoginName("loginName");
		user.setName("name");
		user.setSurname("surname");

		userAdminService.saveOrUpdateUser(user);

		userAdminService.deleteUser(user);

		DatastoreService ds = DatastoreServiceFactory.getDatastoreService();
		Assert.assertEquals(0, ds.prepare(new Query("User")).countEntities(FetchOptions.Builder.withLimit(100)));
	}

	@Test
	public void testUpdateUserStates() throws Exception {
		User lastUser = new User();
		for (int i = 0; i < 5; i++) {
			User user = new User();
			user.setLoginName("loginName" + i);
			user.setName("name" + i);
			user.setSurname("surname" + i);
			user.setState(0);

			userAdminService.saveOrUpdateUser(user);

			if (i == 4) {
				lastUser = user;
			}
		}

		lastUser.setState(1);

		User updateUser = new User();
		updateUser.setIdUser(lastUser.getIdUser());
		updateUser.setLoginName(lastUser.getLoginName());
		updateUser.setName(lastUser.getName());
		updateUser.setSurname(lastUser.getSurname());
		updateUser.setState(1);
		
		List<User> users = new ArrayList<User>();
		users.add(updateUser);
		userAdminService.saveOrUpdateUsers(users);

		DatastoreService ds = DatastoreServiceFactory.getDatastoreService();
		Assert.assertEquals(4,
				ds.prepare((new Query("User")).addFilter("state", Query.FilterOperator.EQUAL, 0)).countEntities(FetchOptions.Builder.withLimit(100)));
		Assert.assertEquals(1,
				ds.prepare((new Query("User")).addFilter("state", Query.FilterOperator.EQUAL, 1)).countEntities(FetchOptions.Builder.withLimit(100)));
	}

    @Test
    public void testGetUserCount() throws Exception {
        User user = new User();
        user.setName("UserTest");
    	
        userAdminService.saveOrUpdateUser(user);	

        DatastoreService ds = DatastoreServiceFactory.getDatastoreService();
        Assert.assertEquals(ds.prepare(new Query("User")).countEntities(FetchOptions.Builder.withLimit(100)), userAdminService.getUserCount());
    }
}
