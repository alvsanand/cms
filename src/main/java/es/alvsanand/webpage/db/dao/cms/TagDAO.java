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

import java.util.List;
import java.util.Map;

import es.alvsanand.webpage.db.QueryBean;
import es.alvsanand.webpage.db.dao.DAOException;
import es.alvsanand.webpage.model.Tag;

/**
 * Service for retrieving Tags from DB
 *
 * @author alvaro.santos
 * @date 20/11/2009
 *
 */
public interface TagDAO {        
    public List<Tag> getTags(QueryBean queryBean) throws DAOException;
    public List<Tag> getTagsWithSameName(QueryBean queryBean) throws DAOException;
    public List<Tag> getTagsByName(String name) throws DAOException;
    
    public Tag getTag(Tag tag) throws DAOException;
    public Tag getTagWithSameName(Tag tag) throws DAOException;
    
    public int getTagCount() throws DAOException;
    public int getTagCountWithSameName() throws DAOException;
    public int getTagCountByName(String name) throws DAOException;
    public Map<Tag, Integer> getTagCountSortedByName() throws DAOException;
    
    public void saveOrUpdateTag(Tag tag) throws DAOException;
    public void saveOrUpdateTagWithSameName(Tag tag) throws DAOException;
    
    public void deleteTag(Tag tag) throws DAOException;    
    public void deleteTagWithSameName(Tag tag) throws DAOException;
}
