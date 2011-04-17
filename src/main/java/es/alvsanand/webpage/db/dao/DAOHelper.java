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
package es.alvsanand.webpage.db.dao;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

import org.apache.commons.lang.StringUtils;
import org.compass.core.Compass;
import org.compass.core.config.CompassConfiguration;
import org.compass.core.config.CompassEnvironment;
import org.compass.gps.CompassGps;
import org.compass.gps.CompassGpsDevice;
import org.compass.gps.device.jpa.JpaGpsDevice;
import org.compass.gps.device.support.parallel.SameThreadParallelIndexExecutor;
import org.compass.gps.impl.SingleCompassGps;

import es.alvsanand.webpage.common.Logger;
import es.alvsanand.webpage.db.QueryBean;
import es.alvsanand.webpage.db.QueryBean.OrderBean;
import es.alvsanand.webpage.db.search.TextConverter;

/**
 * 
 * 
 * @author alvaro.santos
 * @date 23/11/2009
 * 
 */
public abstract class DAOHelper {
	private transient static final Logger logger = new Logger(DAOHelper.class);
	
	protected final static String ORDERBY_SPQL_TEXT = " ORDER BY ";
	protected final static String ASC_SPQL_TEXT = " ASCENDING";
	protected final static String DES_SPQL_TEXT = " DESCENDING";

	private static final EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("alvsanand");

	private static Compass compass;
	private static CompassGps compassGps;

	protected EntityManager getEntityManager() {
		return entityManagerFactory.createEntityManager();
	}

	public static Compass getCompass() {
		if (compass == null) {
			logger.debug("Getting new instance of Compass");
			
			compass = new CompassConfiguration().setConnection("gae://index")
					.setSetting(CompassEnvironment.ExecutorManager.EXECUTOR_MANAGER_TYPE, "disabled").addScan("es.alvsanand.webpage.model")
					.registerConverter("textConverter", com.google.appengine.api.datastore.Text.class, new TextConverter()).buildCompass();

			compassGps = new SingleCompassGps(compass);

			CompassGpsDevice device = new JpaGpsDevice("appengine", entityManagerFactory);

			((JpaGpsDevice) device).setParallelIndexExecutor(new SameThreadParallelIndexExecutor());

			compassGps.addGpsDevice(device);

			 if(compass.getSearchEngineIndexManager().isLocked()){ 
				 compass.getSearchEngineIndexManager().releaseLocks(); 
			 }
			 
			 compassGps.start();
	
			 compassGps.index();
		}

		return compass;
	}

	protected Query getQuery(EntityManager entityManager, String queryName, QueryBean queryBean) {
		StringBuffer queryString = new StringBuffer(queryName);

		Query q = null;

		if (queryBean != null) {
			if (queryBean.getOrderBeans() != null) {
				for (int i = 0; i < queryBean.getOrderBeans().size(); i++) {
					OrderBean orderBean = queryBean.getOrderBeans().get(i);

					if (StringUtils.isEmpty(orderBean.getName())) {
						continue;
					}

					if (i == 0) {
						queryString.append(ORDERBY_SPQL_TEXT);
					}

					queryString.append(orderBean.getName());
					queryString.append((orderBean.isAscendingOrder()) ? ASC_SPQL_TEXT : DES_SPQL_TEXT);
					if (i + 1 < queryBean.getOrderBeans().size()) {
						queryString.append(", ");
					}
				}
			}

			q = entityManager.createQuery(queryString.toString());

			if (queryBean.getLimit() > 0) {
				q.setMaxResults(queryBean.getLimit());
			}
			if (queryBean.getOffset() > 0) {
				q.setFirstResult(queryBean.getOffset());
			}
		} else {
			q = entityManager.createQuery(queryName);
		}

		return q;
	}

	protected Query getNamedQuery(EntityManager entityManager, String queryName, QueryBean queryBean) {
		Query q = entityManager.createNamedQuery(queryName);

		if (queryBean != null) {
			if (queryBean.getLimit() > 0) {
				q.setMaxResults(queryBean.getLimit());
			}
			if (queryBean.getOffset() > 0) {
				q.setFirstResult(queryBean.getOffset());
			}
		}

		return q;
	}
}
