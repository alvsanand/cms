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

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;

import es.alvsanand.webpage.AlvsanandException;
import es.alvsanand.webpage.common.Logger;
import es.alvsanand.webpage.common.MessageResources;
import es.alvsanand.webpage.services.admin.WebAdminService;
import es.alvsanand.webpage.services.admin.WebAdminServiceImpl;

@RequestScoped
@ManagedBean(name="webAdminBean")
public class WebAdminBean implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 7349051155634091347L;

	private transient static final Logger logger = new Logger(WebAdminBean.class);

	private transient WebAdminService webAdminService;

	public WebAdminBean(){
	}

	public WebAdminService getWebAdminService() {
		if(webAdminService==null){
			webAdminService = new WebAdminServiceImpl();
		}
		return webAdminService;
	}

	// JSF methods

	public void eraseCache() throws AlvsanandException {
		logger.info("Launched WebAdminBean.eraseCache");
		
		getWebAdminService().eraseCache();
		
		FacesMessage message = new FacesMessage();
		message.setDetail(MessageResources.getMessage(MessageResources.ADMIN_RESOURCE_BUNDLE_NAME, "admin.eraseCache.message.detail", null));
		message.setSummary(MessageResources.getMessage(MessageResources.ADMIN_RESOURCE_BUNDLE_NAME, "admin.eraseCache.message.summary", null));
		message.setSeverity(FacesMessage.SEVERITY_INFO);
		
		FacesContext.getCurrentInstance().addMessage(null, message);
	}
}
