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
package es.alvsanand.webpage.db.dao.admin;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;

import es.alvsanand.webpage.db.QueryBean;
import es.alvsanand.webpage.db.dao.DAOException;
import es.alvsanand.webpage.db.dao.DAOHelper;
import es.alvsanand.webpage.model.ArticleVersion;
import es.alvsanand.webpage.model.Avatar;
import es.alvsanand.webpage.model.Role;
import es.alvsanand.webpage.model.User;

/**
 * Implementation of the <code>es.alvsanand.webpage.db.dao.security.UserDAO</code>
 *
 * @author alvaro.santos
 * @date 28/07/2010
 *
 */
public class UserDAOImpl extends DAOHelper implements UserDAO{
	/**
	 * Name of the query for delete an user
	 */
	private final static String DELETE_USER_NQ = "DELETE from User user WHERE user.idUser = :idUser";
	/**
	 * Name of the query for delete the roles of a user
	 */
	private final static String DELETE_ROLES_USER_NQ = "DELETE from Role role WHERE role.idUser = :idUser";
	/**
	 * Name of the query for delete the avatars of a user
	 */
	private final static String DELETE_AVATARS_USER_NQ = "DELETE from Avatar avatar WHERE avatar.idUser = :idUser";
	/**
	 * Name of the query for obtaining an user by idUser
	 */
	private final static String FIND_USER_NQ = "SELECT user FROM User user WHERE user.idUser = :idUser";
	/**
	 * Name of the query for obtaining an user by name and password
	 */
	private final static String FIND_USER_BYLOGINNAMEANDPASSWORD_NQ = "SELECT user FROM User user WHERE user.loginName = :name and user.password = :password";
	/**
	 * Name of the query for obtaining an user by name
	 */
	private final static String FIND_USER_BYLOGINNAME_NQ = "SELECT user FROM User user WHERE user.loginName = :loginName";
	/**
	 * Name of the query for obtaining an user by name
	 */
	private final static String FIND_USER_BYREGISTRATIONHASH_NQ = "SELECT user FROM User user WHERE user.registrationHash = :registrationHash";
	/**
	 * Name of the query for selecting all users
	 */
	private final static String FIND_ALL_USER_NQ = "SELECT user FROM User user";
	/**
	 * Name of the query for selecting all users except actual
	 */
	private final static String FIND_ALL_USER_EXCEPT_ACTUAL_NQ = "SELECT user FROM User user where user.loginName <> :loginName";
	/**
	 * Name of the query for selecting all users by the name
	 */
	private final static String FIND_ALL_BYLOGINNAME_USER_NQ = "SELECT user FROM User user WHERE user.loginName = :loginName";
	/**
	 * Name of the query for obtaining the count of users
	 */
	private final static String COUNT_USER_NQ = "SELECT count(user) FROM User user";
	/**
	 * Name of the query for obtaining the count of users by the name
	 */
	private final static String COUNT_BYLOGINNAME_USER_NQ = "SELECT count(user) FROM User user WHERE user.loginName = :loginName";
	/**
	 * Name of the query for obtaining the roles of a user
	 */
	private final static String FIND_ROLES_USER_NQ = "SELECT role FROM Role role WHERE role.idUser = :idUser";
	/**
	 * Name of the query for obtaining the articleVersions of a user
	 */
	private final static String FIND_ARTICLE_VERSIONS_USER_NQ = "SELECT articleVersion FROM ArticleVersion articleVersion WHERE articleVersion.idUser = :idUser";


	/* (non-Javadoc)
	 * @see es.alvsanand.webpage.db.dao.cms.UserDAO#getUsers(es.alvsanand.webpage.db.QueryBean)
	 */
	public List<User> getUsers(QueryBean queryBean) throws DAOException{		
		try{			
			EntityManager entityManager = getEntityManager();
			
			Query q = getQuery(entityManager, FIND_ALL_USER_NQ, queryBean);
			Object resultObj = q.getResultList();
			
			List<User> result = new java.util.ArrayList<User>((List<User>)resultObj);
			
			entityManager.close();
			
			return result;
		}
		catch(NoResultException noResultException){
			return null;
		}
		catch(Throwable e){
			throw new DAOException("Error getting User beans: " + e.getMessage(), e);
		}
	}
	
	public List<User> getUsersExceptActual(String actualUser, QueryBean queryBean) throws DAOException{		
		try{
			if(actualUser==null){
				throw new IllegalArgumentException();
			}
			EntityManager entityManager = getEntityManager();
			
			Query q = getQuery(entityManager, FIND_ALL_USER_EXCEPT_ACTUAL_NQ, queryBean);
			q.setParameter("loginName", actualUser);
			Object resultObj = q.getResultList();
			
			List<User> result = new java.util.ArrayList<User>((List<User>)resultObj);
			
			entityManager.close();
			
			return result;
		}
		catch(NoResultException noResultException){
			return null;
		}
		catch(Throwable e){
			throw new DAOException("Error getting User beans: " + e.getMessage(), e);
		}
	}

	/* (non-Javadoc)
	 * @see es.alvsanand.webpage.db.dao.cms.UserDAO#getUser(es.alvsanand.webpage.model.User)
	 */
	public User getUser(User user) throws DAOException{
		try{
			if(user==null || user.getIdUser()==null){
				throw new IllegalArgumentException();
			}
			EntityManager entityManager = getEntityManager();
			
			Query q = getQuery(entityManager, FIND_USER_NQ, null);
			q.setParameter("idUser", user.getIdUser());
			
			Object resultObj = q.getSingleResult();
			
			User result = (User)resultObj;
			
			if(result!=null){
				result.setRole(getRole(result, entityManager));
			}
			
			entityManager.close();
			
			return result;
		}
		catch(NoResultException noResultException){
			return null;
		}
		catch(Throwable e){
			throw new DAOException("Error getting User bean by Key: " + e.getMessage(), e);
		}
	}

	/* (non-Javadoc)
	 * @see es.alvsanand.webpage.db.dao.cms.UserDAO#getUserCount()
	 */
	public int getUserCount() throws DAOException{		
		try{
			EntityManager entityManager = getEntityManager();
			
			Query q = getQuery(entityManager, COUNT_USER_NQ, null);

			Object resultObj = q.getSingleResult();
			
			entityManager.close();
			
			return ((Integer)resultObj).intValue();
		}
		catch(NoResultException noResultException){
			return 0;
		}
		catch(Throwable e){
			throw new DAOException("Error getting count of User beans: " + e.getMessage(), e);
		}
	}

	/* (non-Javadoc)
	 * @see es.alvsanand.webpage.db.dao.security.UserDAO#getUserCountByLoginName(java.lang.String)
	 */
	public int getUserCountByLoginName(String name) throws DAOException{		
		try{
			if(name==null){
				throw new IllegalArgumentException();
			}
			EntityManager entityManager = getEntityManager();
			
			Query q = getQuery(entityManager, COUNT_BYLOGINNAME_USER_NQ, null);
			q.setParameter("loginName", name);

			Object resultObj = q.getSingleResult();
			
			entityManager.close();
			
			return ((Integer)resultObj).intValue();
		}
		catch(NoResultException noResultException){
			return 0;
		}
		catch(Throwable e){
			throw new DAOException("Error getting count of User beans by loginName: " + e.getMessage(), e);
		}
	}

	/* (non-Javadoc)
	 * @see es.alvsanand.webpage.db.dao.security.UserDAO#getUsersByLoginName(java.lang.String)
	 */
	public List<User> getUsersByLoginName(String loginName) throws DAOException{
		try{
			if(loginName==null){
				throw new IllegalArgumentException();
			}
			EntityManager entityManager = getEntityManager();
			
			Query q = getQuery(entityManager, FIND_ALL_BYLOGINNAME_USER_NQ, null);
			q.setParameter("loginName", loginName);
			Object resultObj = q.getResultList();
			
			List<User> result = new java.util.ArrayList<User>((List<User>)resultObj);
			
			entityManager.close();
			
			return result;
		}
		catch(NoResultException noResultException){
			return null;
		}
		catch(Throwable e){
			throw new DAOException("Error getting User bean by loginName: " + e.getMessage(), e);
		}		
	}


	/* (non-Javadoc)
	 * @see es.alvsanand.webpage.db.dao.security.UserDAO#getUserByLoginName(java.lang.String)
	 */
	public User getUserByLoginName(String loginName) throws DAOException{
		try{
			if(loginName==null){
				throw new IllegalArgumentException();
			}
			EntityManager entityManager = getEntityManager();
			
			Query q = getQuery(entityManager, FIND_USER_BYLOGINNAME_NQ, null);
			q.setParameter("loginName", loginName);
			
			Object resultObj = q.getSingleResult();
			
			User result = (User)resultObj;
			
			if(result!=null){
				result.setRole(getRole(result, entityManager));
			}
			
			entityManager.close();
			
			return result;
		}
		catch(NoResultException noResultException){
			return null;
		}
		catch(Throwable e){
			throw new DAOException("Error getting User bean by loginName: " + e.getMessage(), e);
		}
	}


	 /* (non-Javadoc)
	 * @see es.alvsanand.webpage.db.dao.security.UserDAO#getUserByLoginNameAndPassword(java.lang.String, java.lang.String)
	 */
	public User getUserByLoginNameAndPassword(String loginName, String password) throws DAOException{
		try{
			if(loginName==null || password==null){
				throw new IllegalArgumentException();
			}
			EntityManager entityManager = getEntityManager();
			
			Query q = getQuery(entityManager, FIND_USER_BYLOGINNAMEANDPASSWORD_NQ, null);
			q.setParameter("loginName", loginName);
			q.setParameter("password", password);
			
			Object resultObj = q.getSingleResult();
			
			User result = (User)resultObj;
			
			if(result!=null){
				result.setRole(getRole(result, entityManager));
			}
			
			entityManager.close();
			
			return result;
		}
		catch(NoResultException noResultException){
			return null;
		}
		catch(Throwable e){
			throw new DAOException("Error getting User bean by loginName and password: " + e.getMessage(), e);
		}
	}
	 
	/* (non-Javadoc)
	 * @see es.alvsanand.webpage.db.dao.security.UserDAO#getUserByRegistrationHash(java.lang.String)
	 */
	public User getUserByRegistrationHash(String registrationHash) throws DAOException {
		try {
			if (registrationHash == null) {
				throw new IllegalArgumentException();
			}
			EntityManager entityManager = getEntityManager();

			Query q = getQuery(entityManager, FIND_USER_BYREGISTRATIONHASH_NQ, null);
			q.setParameter("registrationHash", registrationHash);

			Object resultObj = q.getSingleResult();

			User result = (User) resultObj;

			if (result != null) {
				result.setRole(getRole(result, entityManager));
			}

			entityManager.close();

			return result;
		} catch (NoResultException noResultException) {
			return null;
		} catch (Throwable e) {
			throw new DAOException("Error getting User bean by registrationHash: " + e.getMessage(), e);
		}
	}

	private Role getRole(User user, EntityManager entityManager) throws DAOException{
		try{
			if(user==null || user.getIdUser()==null){
				throw new IllegalArgumentException();
			}
			Query q = getQuery(entityManager, FIND_ROLES_USER_NQ, null);
			q.setParameter("idUser", user.getIdUser());
			
			Object resultObj = q.getSingleResult();
						
			return (Role)resultObj;
		}
		catch(NoResultException noResultException){
			return null;
		}
		catch(Throwable e){
			throw new DAOException("Error getting User bean by Key: " + e.getMessage(), e);
		}
	}

	private List<ArticleVersion> getArticleVersions(User user, EntityManager entityManager) throws DAOException{
		try{
			if(user==null || user.getIdUser()==null){
				throw new IllegalArgumentException();
			}
			Query q = getQuery(entityManager, FIND_ARTICLE_VERSIONS_USER_NQ, null);
			q.setParameter("idUser", user.getIdUser());
			
			Object resultObj = q.getResultList();
			
			List<ArticleVersion> result = new java.util.ArrayList<ArticleVersion>((List<ArticleVersion>)resultObj);
			
			return result;
		}
		catch(NoResultException noResultException){
			return null;
		}
		catch(Throwable e){
			throw new DAOException("Error getting User bean by Key: " + e.getMessage(), e);
		}
	}

	/* (non-Javadoc)
	 * @see es.alvsanand.webpage.db.dao.cms.UserDAO#deleteUser(es.alvsanand.webpage.model.User)
	 */
	public void deleteUser(User user) throws DAOException {
		if(user!=null && user.getIdUser()!=null){

			EntityManager entityManager = getEntityManager();			
			
			try{
				//Modifying dependencies
				{
					List<ArticleVersion> articleVersionsList = getArticleVersions(user, entityManager);
					
					if(articleVersionsList!=null){
						for (ArticleVersion articleVersion : articleVersionsList) {
							articleVersion.setUser(null);
							
							articleVersion = entityManager.merge(articleVersion);					
							entityManager.persist(articleVersion);
						}
					}
				}
				//Deleting dependencies				
				{
					Query q = getQuery(entityManager, DELETE_ROLES_USER_NQ, null);
					q.setParameter("idUser", user.getIdUser());
					q.executeUpdate();
				}			
				{
					Query q = getQuery(entityManager, DELETE_AVATARS_USER_NQ, null);
					q.setParameter("idUser", user.getIdUser());
					q.executeUpdate();
				}
				
				//Deleting User
				{
					Query q = getQuery(entityManager, DELETE_USER_NQ, null);
					q.setParameter("idUser", user.getIdUser());
					q.executeUpdate();
				}
				
			}
			catch(Throwable e){
				throw new DAOException("Error deleting User bean: " + e.getMessage(), e);
			}
			finally{				
				entityManager.close();
			}
		}
		else{
			throw new DAOException("Error deleting User bean: the user is null or empty");
		}
	}

	/* (non-Javadoc)
	 * @see es.alvsanand.webpage.db.dao.cms.UserDAO#saveOrUpdateUser(es.alvsanand.webpage.model.User)
	 */
	public void saveOrUpdateUser(User user) throws DAOException {
		if(user!=null){
			EntityManager entityManager = getEntityManager();			
			
			try{
				user = entityManager.merge(user);					
				entityManager.persist(user);
				
				//Saving or updating dependencies
				if(user.getArticleVersions()!=null){
					for(ArticleVersion articleVersion: user.getArticleVersions()){
						articleVersion.setUser(user);
						
						articleVersion = entityManager.merge(articleVersion);					
						entityManager.persist(articleVersion);
					}
				}
				
				if(user.getRole()!=null){
					//Deleting old roles				
					{
						Query q = getQuery(entityManager, DELETE_ROLES_USER_NQ, null);
						q.setParameter("idUser", user.getIdUser());
						q.executeUpdate();
					}
					
					Role role = user.getRole();
					role.setUser(user);
						
					role = entityManager.merge(role);					
					entityManager.persist(role);
				}
				
				if(user.getAvatar()!=null){
					//Deleting old avatars				
					{
						Query q = getQuery(entityManager, DELETE_AVATARS_USER_NQ, null);
						q.setParameter("idUser", user.getIdUser());
						q.executeUpdate();
					}
					
					Avatar avatar = user.getAvatar();
					avatar.setUser(user);
						
					avatar = entityManager.merge(avatar);					
					entityManager.persist(avatar);
				}
			}
			catch(Throwable e){				
				throw new DAOException("Error saving or updating User bean: " + e.getMessage(), e);
			}
			finally{				
				entityManager.close();
			}
		}
		else{
			throw new DAOException("Error saving or updating User bean: the user is null or empty");
		}
	}

	/* (non-Javadoc)
	 * @see es.alvsanand.webpage.db.dao.security.UserDAO#saveOrUpdateUsers(java.util.List)
	 */
	public void saveOrUpdateUsers(List<User> userList) throws DAOException {
		//Transactions are not permitted for multiple saves
		if(userList!=null){
			EntityManager entityManager = getEntityManager();
			
			
			try{
				
				for(User user: userList){					
					user = entityManager.merge(user);					
					entityManager.persist(user);
					
					//Saving or updating dependencies
					if(user.getArticleVersions()!=null){
						for(ArticleVersion articleVersion: user.getArticleVersions()){
							articleVersion.setUser(user);
							
							articleVersion = entityManager.merge(articleVersion);					
							entityManager.persist(articleVersion);
						}
					}				
					
					if(user.getRole()!=null){
						Role role = user.getRole();
						role.setUser(user);
						
						role = entityManager.merge(role);					
						entityManager.persist(role);
					}
				}				
			}
			catch(Throwable e){				
				throw new DAOException("Error saving or updating User bean: " + e.getMessage(), e);
			}
			finally{				
				entityManager.close();
			}
		}
		else{
			throw new DAOException("Error saving or updating User bean: the user is null or empty");
		}
	}
}
