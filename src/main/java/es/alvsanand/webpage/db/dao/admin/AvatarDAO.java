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

import es.alvsanand.webpage.db.QueryBean;
import es.alvsanand.webpage.db.dao.DAOException;
import es.alvsanand.webpage.model.Avatar;
import es.alvsanand.webpage.model.User;

/**
 * Service for retrieving Avatars from DB
 *
 * @author alvaro.santos
 * @date 20/11/2009
 *
 */
public interface AvatarDAO { 
    public List<Avatar> getAvatars(QueryBean queryBean) throws DAOException;       
    public Avatar getAvatar(Avatar avatar) throws DAOException;    
    public Avatar getAvatarByUser(User user) throws DAOException;
    public int getAvatarCount() throws DAOException;
    public void saveOrUpdateAvatar(Avatar avatar) throws DAOException;
    public void deleteAvatar(Avatar avatar) throws DAOException;
}
