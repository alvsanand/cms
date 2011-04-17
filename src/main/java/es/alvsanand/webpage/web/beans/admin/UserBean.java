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
package es.alvsanand.webpage.web.beans.admin;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;

import org.apache.commons.lang.StringUtils;
import org.primefaces.model.LazyDataModel;

import es.alvsanand.webpage.AlvsanandException;
import es.alvsanand.webpage.common.Globals;
import es.alvsanand.webpage.common.Logger;
import es.alvsanand.webpage.common.MessageResources;
import es.alvsanand.webpage.db.QueryBean;
import es.alvsanand.webpage.db.QueryBean.OrderBean;
import es.alvsanand.webpage.model.Role;
import es.alvsanand.webpage.model.User;
import es.alvsanand.webpage.services.ServiceException;
import es.alvsanand.webpage.services.admin.UserAdminService;
import es.alvsanand.webpage.services.admin.UserAdminServiceImpl;

@SessionScoped
@ManagedBean(name="userBean")
public class UserBean implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -8906971058757132704L;

	private transient final static Logger logger = new Logger(UserBean.class);
	
	private transient final static String DEFAULT_SORT_FIELD = "creationdate";
	private transient final static String OBLIGATORY_SORT_FIELD = "loginName";
	
	private transient static final String LIST_USER_VIEW_ID = "pretty:listUser";
	private transient static final String EDIT_USER_VIEW_ID = "/xhtml/secured/admin/users/edit.jsf";

	private transient UserAdminService userAdminService;

	private String idUser;

	private String loginName;

	private String name;

	private String surname;

	private String email;

	private String state;

	private String roleName;

	private Map<String, Boolean> selectedUsers = new HashMap<String, Boolean>();

	private LazyDataModel<User> lazyModel;

	public UserBean() {
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public String getIdUser() {
		return idUser;
	}

	public void setIdUser(String idUser) {
		this.idUser = idUser;
	}

	public LazyDataModel<User> getLazyModel() throws ServiceException {
		int rowCount = getUserAdminService().getUserCount();
		
		if(lazyModel==null){			
			final String actualName = ((es.alvsanand.webpage.model.User)FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get(Globals.SES_USER)).getLoginName();
			
			lazyModel = new LazyDataModel<User>() {			
				/**
				 * 
				 */
				private static final long serialVersionUID = -3849916319838761619L;

				@Override
				public List<User> load(int first, int pageSize, String sortField, boolean sortOrder, Map<String,String> filters) {
					logger.info("Loading the user list between " + first + " and " + (first + pageSize));

					List<User> lazyUsers = null;
					try {
						QueryBean queryBean = new QueryBean();
						queryBean.setLimit(pageSize);
						queryBean.setOffset(first);

//						//Sorting disabled until Primefaces implements it
//						OrderBean orderBean = new OrderBean(sortOrder, sortField);
//						List<OrderBean> orderBeans = new java.util.ArrayList<OrderBean>();
//						orderBeans.add(orderBean);					
//						queryBean.setOrderBeans(orderBeans);
						
						if(sortField==null){
							List<OrderBean> orderBeans = new java.util.ArrayList<OrderBean>();
							
							OrderBean orderBean = new OrderBean(true, OBLIGATORY_SORT_FIELD);					
							orderBeans.add(orderBean);
							
							orderBean = new OrderBean(true, DEFAULT_SORT_FIELD);					
							orderBeans.add(orderBean);
							
							queryBean.setOrderBeans(orderBeans);
						}
						
						lazyUsers = getUserAdminService().getUsers(actualName, queryBean);
					} catch (ServiceException e) {
						logger.error("Error loading the user list between " + first + " and " + (first+pageSize) + ": " + e.getMessage());
					}

					return lazyUsers;
				}
			};
		}
		lazyModel.setRowCount(rowCount);
		
		return lazyModel;
	}

	public Map<String, Boolean> getSelectedUsers() {
		return selectedUsers;
	}

	public void setSelectedUsers(Map<String, Boolean> selectedUsers) {
		this.selectedUsers = selectedUsers;
	}

	public UserAdminService getUserAdminService() {
		if(userAdminService==null){
			userAdminService = new UserAdminServiceImpl();
		}
		return userAdminService;
	}

	// JSF methods

	public void getUsers() throws AlvsanandException {
		logger.info("Launched UserBean.getUsers");		
	}

	public String editUser() throws AlvsanandException {
		logger.info("Launched UserBean.editUser[" + idUser + "]");
		
		User userTmp = new User();
		userTmp.setIdUser(idUser);

		User user = getUserAdminService().getUser(userTmp);
		loginName = user.getLoginName();
		name = user.getName();
		surname = user.getSurname();
		email = user.getEmail();
		state = Integer.toString(user.getState());
		idUser = user.getIdUser();
		roleName = user.getRole().getName();

		return EDIT_USER_VIEW_ID;
	}

	public String deleteUser() throws AlvsanandException {
		logger.info("Launched UserBean.deleteUser[" + idUser + "]");
		
		User user = new User();
		user.setIdUser(idUser);

		getUserAdminService().deleteUser(user);

		return LIST_USER_VIEW_ID;
	}

	public String saveUser() throws AlvsanandException {
		logger.info("Launched UserBean.saveUser[" + idUser + "]");
		
		User userTmp = new User();
		userTmp.setIdUser(idUser);

		User user = getUserAdminService().getUser(userTmp);

		user.setLoginName(loginName);
		user.setName(name);
		user.setSurname(surname);
		user.setEmail(email);
		
		Role role = new Role();
		role.setName(roleName);
		user.setRole(role);

		getUserAdminService().saveOrUpdateUser(user);

		return LIST_USER_VIEW_ID;
	}

	public String updateStateUser() throws AlvsanandException {
		logger.info("Launched UserBean.updateStateUser[" + idUser + "]");
		
		User userTmp = new User();
		userTmp.setIdUser(idUser);

		User user = getUserAdminService().getUser(userTmp);

		user.setState(Integer.parseInt(state));

		getUserAdminService().saveOrUpdateUser(user);

		return LIST_USER_VIEW_ID;
	}

	public String updateStateUsers() throws AlvsanandException {
		logger.info("Launched UserBean.updateStateUsers[" + selectedUsers.keySet() + "]");
		
		List<User> userList = new java.util.ArrayList<User>();
		
		if(selectedUsers!=null){
			for(String idUser: selectedUsers.keySet()){				
				if(selectedUsers.get(idUser)!=null && selectedUsers.get(idUser).booleanValue()){
					User userTmp = new User();
					userTmp.setIdUser(idUser);
		
					User user = getUserAdminService().getUser(userTmp);
		
					user.setState(Integer.parseInt(state));
					
					userList.add(user);
				}
			}
		}

		getUserAdminService().saveOrUpdateUsers(userList);

		return LIST_USER_VIEW_ID;
	}
}
