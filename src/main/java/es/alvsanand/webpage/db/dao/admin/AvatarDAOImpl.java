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
import es.alvsanand.webpage.model.Avatar;
import es.alvsanand.webpage.model.User;

/**
 * Implementation of the <code>es.alvsanand.webpage.db.dao.security.AvatarDAO</code>
 *
 * @author alvaro.santos
 * @date 28/07/2010
 *
 */
public class AvatarDAOImpl extends DAOHelper implements AvatarDAO{
	/**
	 * Name of the query for delete an avatar
	 */
	private final static String DELETE_AVATAR_NQ = "DELETE from Avatar avatar WHERE avatar.idAvatar = :idAvatar";
	/**
	 * Name of the query for obtaining an avatar by idAvatar
	 */
	private final static String FIND_AVATAR_NQ = "SELECT avatar FROM Avatar avatar WHERE avatar.idAvatar = :idAvatar";
	/**
	 * Name of the query for obtaining an avatar by User
	 */
	private final static String FIND_AVATAR_BY_USER_NQ = "SELECT avatar FROM Avatar avatar WHERE avatar.idUser = :idUser";
	/**
	 * Name of the query for selecting all avatars
	 */
	private final static String FIND_ALL_AVATAR_NQ = "SELECT avatar FROM Avatar avatar";
	/**
	 * Name of the query for obtaining the count of avatars
	 */
	private final static String COUNT_AVATAR_NQ = "SELECT count(avatar) FROM Avatar avatar";


	/* (non-Javadoc)
	 * @see es.alvsanand.webpage.db.dao.cms.AvatarDAO#getAvatars(es.alvsanand.webpage.db.QueryBean)
	 */
	public List<Avatar> getAvatars(QueryBean queryBean) throws DAOException{		
		try{			
			EntityManager entityManager = getEntityManager();
			
			Query q = getQuery(entityManager, FIND_ALL_AVATAR_NQ, queryBean);
			Object resultObj = q.getResultList();
			
			List<Avatar> result = new java.util.ArrayList<Avatar>((List<Avatar>)resultObj);
			
			entityManager.close();
			
			return result;
		}
		catch(NoResultException noResultException){
			return null;
		}
		catch(Throwable e){
			throw new DAOException("Error getting Avatar beans: " + e.getMessage(), e);
		}
	}

	/* (non-Javadoc)
	 * @see es.alvsanand.webpage.db.dao.cms.AvatarDAO#getAvatar(es.alvsanand.webpage.model.Avatar)
	 */
	public Avatar getAvatar(Avatar avatar) throws DAOException{
		try{
			if(avatar==null || avatar.getIdAvatar()==null){
				throw new IllegalArgumentException();
			}
			EntityManager entityManager = getEntityManager();
			
			Query q = getQuery(entityManager, FIND_AVATAR_NQ, null);
			q.setParameter("idAvatar", avatar.getIdAvatar());
			
			Object resultObj = q.getSingleResult();
			
			Avatar result = (Avatar)resultObj;
			
			entityManager.close();
			
			return result;
		}
		catch(NoResultException noResultException){
			return null;
		}
		catch(Throwable e){
			throw new DAOException("Error getting Avatar bean by Key: " + e.getMessage(), e);
		}
	}

	/* (non-Javadoc)
	 * @see es.alvsanand.webpage.db.dao.admin.AvatarDAO#getAvatarByUser(es.alvsanand.webpage.model.User)
	 */
	public Avatar getAvatarByUser(User user) throws DAOException{
		try{
			if(user==null || user.getIdUser()==null){
				throw new IllegalArgumentException();
			}
			EntityManager entityManager = getEntityManager();
			
			Query q = getQuery(entityManager, FIND_AVATAR_BY_USER_NQ, null);
			q.setParameter("idUser", user.getIdUser());
			
			Object resultObj = q.getSingleResult();
			
			Avatar result = (Avatar)resultObj;
			
			entityManager.close();
			
			return result;
		}
		catch(NoResultException noResultException){
			return null;
		}
		catch(Throwable e){
			throw new DAOException("Error getting Avatar bean by user: " + e.getMessage(), e);
		}
	}

	/* (non-Javadoc)
	 * @see es.alvsanand.webpage.db.dao.cms.AvatarDAO#getAvatarCount()
	 */
	public int getAvatarCount() throws DAOException{		
		try{
			EntityManager entityManager = getEntityManager();
			
			Query q = getQuery(entityManager, COUNT_AVATAR_NQ, null);

			Object resultObj = q.getSingleResult();
			
			entityManager.close();
			
			return ((Integer)resultObj).intValue();
		}
		catch(NoResultException noResultException){
			return 0;
		}
		catch(Throwable e){
			throw new DAOException("Error getting count of Avatar beans: " + e.getMessage(), e);
		}
	}

	/* (non-Javadoc)
	 * @see es.alvsanand.webpage.db.dao.cms.AvatarDAO#deleteAvatar(es.alvsanand.webpage.model.Avatar)
	 */
	public void deleteAvatar(Avatar avatar) throws DAOException {
		if(avatar!=null && avatar.getIdAvatar()!=null){

			EntityManager entityManager = getEntityManager();			
			
			try{				
				//Deleting Avatar
				{
					Query q = getQuery(entityManager, DELETE_AVATAR_NQ, null);
					q.setParameter("idAvatar", avatar.getIdAvatar());
					q.executeUpdate();
				}
				
			}
			catch(Throwable e){
				throw new DAOException("Error deleting Avatar bean: " + e.getMessage(), e);
			}
			finally{				
				entityManager.close();
			}
		}
		else{
			throw new DAOException("Error deleting Avatar bean: the avatar is null or empty");
		}
	}

	/* (non-Javadoc)
	 * @see es.alvsanand.webpage.db.dao.cms.AvatarDAO#saveOrUpdateAvatar(es.alvsanand.webpage.model.Avatar)
	 */
	public void saveOrUpdateAvatar(Avatar avatar) throws DAOException {
		if(avatar!=null){
			EntityManager entityManager = getEntityManager();			
			
			try{
				avatar = entityManager.merge(avatar);					
				entityManager.persist(avatar);
			}
			catch(Throwable e){				
				throw new DAOException("Error saving or updating Avatar bean: " + e.getMessage(), e);
			}
			finally{				
				entityManager.close();
			}
		}
		else{
			throw new DAOException("Error saving or updating Avatar bean: the avatar is null or empty");
		}
	}
}
