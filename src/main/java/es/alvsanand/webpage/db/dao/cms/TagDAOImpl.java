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
package es.alvsanand.webpage.db.dao.cms;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;

import org.datanucleus.store.query.AbstractQueryResult;

import es.alvsanand.webpage.db.QueryBean;
import es.alvsanand.webpage.db.dao.DAOException;
import es.alvsanand.webpage.db.dao.DAOHelper;
import es.alvsanand.webpage.model.Tag;

/**
 * Implementation of the <code>es.alvsanand.webpage.db.dao.cms.TagDAO</code>
 *
 * @author alvaro.santos
 * @date 28/07/2010
 *
 */
public class TagDAOImpl extends DAOHelper implements TagDAO{
	/**
	 * Name of the query for delete an tag
	 */
	private final static String DELETE_TAG_NQ = "DELETE from Tag tag WHERE tag.idTag = :idTag";
	/**
	 * Name of the query for delete an tag with the same name
	 */
	private final static String DELETE_TAG_WITH_SAME_NAME_NQ = "DELETE from Tag tag WHERE tag.name = :name";
	/**
	 * Name of the query for obtaining an tag by idTag
	 */
	private final static String FIND_TAG_NQ = "SELECT tag FROM Tag tag WHERE tag.idTag = :idTag";
	/**
	 * Name of the query for obtaining an tag with same name
	 */
	private final static String FIND_TAG_WITH_SAME_NAME_NQ = "SELECT tag.name, tag.description FROM Tag tag WHERE tag.name = :name and tag.asociated = false";
	/**
	 * Name of the query for selecting all tags
	 */
	private final static String FIND_ALL_TAG_NQ = "SELECT tag FROM Tag tag";
	/**
	 * Name of the query for selecting all tags with the same name
	 */
	private final static String FIND_ALL_TAG_WITH_SAME_NAME_NQ = "SELECT tag.name, tag.description FROM Tag tag WHERE tag.asociated = false";
	/**
	 * Name of the query for selecting all tags by the name
	 */
	private final static String FIND_ALL_BYNAME_TAG_NQ = "SELECT tag FROM Tag tag WHERE tag.name = :name";
	/**
	 * Name of the query for obtaining the count of tags
	 */
	private final static String COUNT_TAG_NQ = "SELECT count(tag) FROM Tag tag";
	/**
	 * Name of the query for obtaining the count of tags with the same name
	 */
	private final static String COUNT_TAG_WITH_SAME_NAME_NQ = "SELECT  count(tag) FROM Tag tag WHERE tag.asociated = false";
	/**
	 * Name of the query for obtaining the count of tags by the name
	 */
	private final static String COUNT_TAG_BYNAME_NQ = "SELECT count(tag) FROM Tag tag WHERE tag.name = :name";
	/**
	 * Name of the query for obtaining the count of tags sorted by name
	 */
	private final static String COUNT_TAG_SORTED_BYNAME_NQ = "SELECT tag.name, tag.description FROM Tag tag WHERE tag.asociated = true";

	/* (non-Javadoc)
	 * @see es.alvsanand.webpage.db.dao.cms.TagDAO#getTags(es.alvsanand.webpage.db.QueryBean)
	 */
	public List<Tag> getTags(QueryBean queryBean) throws DAOException{		
		try{			
			EntityManager entityManager = getEntityManager();
			
			Query q = getQuery(entityManager, FIND_ALL_TAG_NQ, queryBean);
			Object resultObj = q.getResultList();
			
			List<Tag> result = new java.util.ArrayList<Tag>((List<Tag>)resultObj);
			
			entityManager.close();
			
			return result;
		}
		catch(NoResultException noResultException){
			return null;
		}
		catch(Throwable e){
			throw new DAOException("Error getting Tag beans: " + e.getMessage(), e);
		}
	}

	/* (non-Javadoc)
	 * @see es.alvsanand.webpage.db.dao.cms.TagDAO#getTagsWithSameName(es.alvsanand.webpage.db.QueryBean)
	 */
	public List<Tag> getTagsWithSameName(QueryBean queryBean) throws DAOException{		
		try{			
			EntityManager entityManager = getEntityManager();
			
			Query q = getQuery(entityManager, FIND_ALL_TAG_WITH_SAME_NAME_NQ, queryBean);
			
			List resultObj = q.getResultList();
			
			List<Tag> result = new java.util.ArrayList<Tag>();
			
			for (int i=0; resultObj!=null && i<resultObj.size(); i++) {
				Object[] objectTag = (Object[])resultObj.get(i);
				
				if(objectTag!=null && objectTag.length==2)
					result.add(new Tag((String)objectTag[0], (String)objectTag[1]));
			}
			
			entityManager.close();
			
			return result;
		}
		catch(NoResultException noResultException){
			return null;
		}
		catch(Throwable e){
			throw new DAOException("Error getting Tag beans with same name: " + e.getMessage(), e);
		}
	}

	/* (non-Javadoc)
	 * @see es.alvsanand.webpage.db.dao.cms.TagDAO#getTag(es.alvsanand.webpage.model.Tag)
	 */
	public Tag getTag(Tag tag) throws DAOException{
		try{
			if(tag==null || tag.getIdTag()==null){
				throw new IllegalArgumentException();
			}
			EntityManager entityManager = getEntityManager();
			
			Query q = getQuery(entityManager, FIND_TAG_NQ, null);
			q.setParameter("idTag", tag.getIdTag());
			
			Object resultObj = q.getSingleResult();
			
			entityManager.close();
			
			return (resultObj!=null)?(Tag)resultObj:null;
		}
		catch(NoResultException noResultException){
			return null;
		}
		catch(Throwable e){
			throw new DAOException("Error getting Tag bean by Key: " + e.getMessage(), e);
		}
	}

	/* (non-Javadoc)
	 * @see es.alvsanand.webpage.db.dao.cms.TagDAO#getTagWithSameName(es.alvsanand.webpage.model.Tag)
	 */
	public Tag getTagWithSameName(Tag tag) throws DAOException{
		try{
			if(tag==null || tag.getName()==null){
				throw new IllegalArgumentException();
			}
			EntityManager entityManager = getEntityManager();
			
			Query q = getQuery(entityManager, FIND_TAG_WITH_SAME_NAME_NQ, null);
			q.setParameter("name", tag.getName());
			
			Object[] resultObj = (Object[])q.getSingleResult();
			
			entityManager.close();			
			
			return (resultObj!=null && resultObj.length==2)?(new Tag((String)resultObj[0], (String)resultObj[1])):null;
		}
		catch(NoResultException noResultException){
			return null;
		}
		catch(Throwable e){
			throw new DAOException("Error getting Tag bean with same name: " + e.getMessage(), e);
		}
	}

	/* (non-Javadoc)
	 * @see es.alvsanand.webpage.db.dao.cms.TagDAO#getTagCount()
	 */
	public int getTagCount() throws DAOException{		
		try{
			EntityManager entityManager = getEntityManager();
			
			Query q = getQuery(entityManager, COUNT_TAG_NQ, null);

			Object resultObj = q.getSingleResult();
			
			entityManager.close();
			
			return ((Integer)resultObj).intValue();
		}
		catch(NoResultException noResultException){
			return 0;
		}
		catch(Throwable e){
			throw new DAOException("Error getting count of Tag beans: " + e.getMessage(), e);
		}
	}

	/* (non-Javadoc)
	 * @see es.alvsanand.webpage.db.dao.cms.TagDAO#getTagCountByName(java.lang.String)
	 */
	public int getTagCountByName(String name) throws DAOException{		
		try{
			if(name==null){
				throw new IllegalArgumentException();
			}
			EntityManager entityManager = getEntityManager();
			
			Query q = getQuery(entityManager, COUNT_TAG_BYNAME_NQ, null);
			q.setParameter("name", name);

			Object resultObj = q.getSingleResult();
			
			entityManager.close();
			
			return ((Integer)resultObj).intValue();
		}
		catch(NoResultException noResultException){
			return 0;
		}
		catch(Throwable e){
			throw new DAOException("Error getting count of Tag beans by the name: " + e.getMessage(), e);
		}
	}

	/* (non-Javadoc)
	 * @see es.alvsanand.webpage.db.dao.cms.TagDAO#getTagCountWithSameName()
	 */
	public int getTagCountWithSameName() throws DAOException{		
		try{
			EntityManager entityManager = getEntityManager();
			
			Query q = getQuery(entityManager, COUNT_TAG_WITH_SAME_NAME_NQ, null);

			Object resultObj = q.getSingleResult();
			
			entityManager.close();
			
			return ((Integer)resultObj).intValue();
		}
		catch(NoResultException noResultException){
			return 0;
		}
		catch(Throwable e){
			throw new DAOException("Error getting count of Tag beans by the name: " + e.getMessage(), e);
		}
	}

	/* (non-Javadoc)
	 * @see es.alvsanand.webpage.db.dao.cms.TagDAO#getTagCountSortedByName()
	 */
	public Map<Tag, Integer> getTagCountSortedByName() throws DAOException{		
		try{
			EntityManager entityManager = getEntityManager();
			
			Query q = getQuery(entityManager, COUNT_TAG_SORTED_BYNAME_NQ, null);

			Object resultObj = q.getResultList();
			
			Map<Tag, Integer> map = new HashMap<Tag, Integer>();
			
			Iterator<Object[]> iterator = ((AbstractQueryResult)resultObj).iterator();

            while(iterator.hasNext())
            {
            	Object[] entries = iterator.next();
            	
            	Tag tag = new Tag();
            	tag.setName((String)entries[0]);
            	tag.setDescription((String)entries[1]);
            	
            	int counter = (map.get(tag)!=null)?map.get(tag).intValue():0;
            	
            	map.put(tag, new Integer(counter+1));
            }
			entityManager.close();
			
			return map;
		}
		catch(NoResultException noResultException){
			return null;
		}
		catch(Throwable e){
			throw new DAOException("Error getting count of Tag beans: " + e.getMessage(), e);
		}
	}

	/* (non-Javadoc)
	 * @see es.alvsanand.webpage.db.dao.cms.TagDAO#getTagsByName(java.lang.String)
	 */
	public List<Tag> getTagsByName(String name) throws DAOException{
		try{
			if(name==null){
				throw new IllegalArgumentException();
			}
			EntityManager entityManager = getEntityManager();
			
			Query q = getQuery(entityManager, FIND_ALL_BYNAME_TAG_NQ, null);
			q.setParameter("name", name);
			Object resultObj = q.getResultList();
			
			List<Tag> result = new java.util.ArrayList<Tag>((List<Tag>)resultObj);
			
			entityManager.close();
			
			return result;
		}
		catch(NoResultException noResultException){
			return null;
		}
		catch(Throwable e){
			throw new DAOException("Error getting Tag bean by name: " + e.getMessage(), e);
		}		
	}

	/* (non-Javadoc)
	 * @see es.alvsanand.webpage.db.dao.cms.TagDAO#deleteTag(es.alvsanand.webpage.model.Tag)
	 */
	public void deleteTag(Tag tag) throws DAOException {
		if(tag!=null && tag.getIdTag()!=null){
			EntityManager entityManager = getEntityManager();
			
			try{				
				//Deleting Tag
				Query q = getQuery(entityManager, DELETE_TAG_NQ, null);
				q.setParameter("idTag", tag.getIdTag());
				q.executeUpdate();
			}
			catch(Throwable e){
				throw new DAOException("Error deleting Tag bean: " + e.getMessage(), e);
			}
			finally{				
				entityManager.close();
			}
		}
		else{
			throw new DAOException("Error deleting Tag bean: the tag is null or empty");
		}
	}

	/* (non-Javadoc)
	 * @see es.alvsanand.webpage.db.dao.cms.TagDAO#deleteTagWithSameName(es.alvsanand.webpage.model.Tag)
	 */
	public void deleteTagWithSameName(Tag tag) throws DAOException {
		if(tag!=null && tag.getName()!=null){
			EntityManager entityManager = getEntityManager();
			
			try{
				//Deleting Tag
				Query q = getQuery(entityManager, DELETE_TAG_WITH_SAME_NAME_NQ, null);
				q.setParameter("name", tag.getName());
				q.executeUpdate();
			}
			catch(Throwable e){
				throw new DAOException("Error deleting Tag bean with same name: " + e.getMessage(), e);
			}
			finally{
				entityManager.close();
			}
		}
		else{
			throw new DAOException("Error deleting Tag bean with same name: the tag is null or empty");
		}
	}

	/* (non-Javadoc)
	 * @see es.alvsanand.webpage.db.dao.cms.TagDAO#saveOrUpdateTag(es.alvsanand.webpage.model.Tag)
	 */
	public void saveOrUpdateTag(Tag tag) throws DAOException {
		if(tag!=null){
			EntityManager entityManager = getEntityManager();			
			
			try{
				tag = entityManager.merge(tag);					
				entityManager.persist(tag);
			}
			catch(Throwable e){
				throw new DAOException("Error saving or updating Tag bean: " + e.getMessage(), e);
			}
			finally{
				entityManager.close();
			}
		}
		else{
			throw new DAOException("Error saving or updating Tag bean: the tag is null or empty");
		}
	}

	/* (non-Javadoc)
	 * @see es.alvsanand.webpage.db.dao.cms.TagDAO#saveOrUpdateTagWithSameName(es.alvsanand.webpage.model.Tag)
	 */
	public void saveOrUpdateTagWithSameName(Tag tag) throws DAOException {
		if(tag!=null && tag.getName()!=null){
			EntityManager entityManager = getEntityManager();
			
			try{
				List<Tag> tagList = getTagsByName(tag.getName());
				
				if(tagList==null || tagList.size()==0){
					tag = entityManager.merge(tag);					
					entityManager.persist(tag);
				}
				else{
					for (Tag tag2 : tagList) {
						tag2 = entityManager.merge(tag2);
						
						tag2.setDescription(tag.getDescription());
						
						tag2 = entityManager.merge(tag2);					
						entityManager.persist(tag2);
					}
				}
			}
			catch(Throwable e){
				throw new DAOException("Error saving or updating Tag bean with same name: " + e.getMessage(), e);
			}
			finally{
				entityManager.close();
			}
		}
		else{
			throw new DAOException("Error saving or updating Tag bean with same name: the tag is null or empty");
		}
	}
}
