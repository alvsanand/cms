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
import es.alvsanand.webpage.common.Logger;
import es.alvsanand.webpage.common.MessageResources;
import es.alvsanand.webpage.db.QueryBean;
import es.alvsanand.webpage.db.QueryBean.OrderBean;
import es.alvsanand.webpage.model.Tag;
import es.alvsanand.webpage.services.ServiceException;
import es.alvsanand.webpage.services.admin.CmsAdminService;
import es.alvsanand.webpage.services.admin.CmsAdminServiceImpl;

@SessionScoped
@ManagedBean(name="tagBean")
public class TagBean implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -6063189373574611850L;

	private transient static final Logger logger = new Logger(TagBean.class);
	
	private transient static final String DEFAULT_SORT_FIELD = "name";
	
	private transient static final String LIST_TAG_VIEW_ID = "pretty:listTag";
	private transient static final String EDIT_TAG_VIEW_ID = "/xhtml/secured/admin/tags/edit.jsf";
	private transient static final String NEW_TAG_VIEW_ID = "/xhtml/secured/admin/tags/edit.jsf";

	private transient CmsAdminService cmsAdminService;

	private String name;

	private String description;

	private LazyDataModel<Tag> lazyModel; 

	public TagBean(){
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public LazyDataModel<Tag> getLazyModel() throws ServiceException {

		int rowCount = getCmsAdminService().getTagCount();
		
		if(lazyModel==null){			
			lazyModel = new LazyDataModel<Tag>() {			
				/**
				 * 
				 */
				private static final long serialVersionUID = -6616310526645229962L;

				@Override
				public List<Tag> load(int first, int pageSize, String sortField, boolean sortOrder, Map<String,String> filters) {
					logger.info("Loading the tag list between " + first + " and " + (first + pageSize));

					List<Tag> lazyTags = null;
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
							OrderBean orderBean = new OrderBean(true,DEFAULT_SORT_FIELD);
							List<OrderBean> orderBeans = new java.util.ArrayList<OrderBean>();
							orderBeans.add(orderBean);					
							queryBean.setOrderBeans(orderBeans);
						}
						
						lazyTags = getCmsAdminService().getTags(queryBean);
					} catch (ServiceException e) {
						logger.error("Error loading the tag list between " + first + " and " + (first+pageSize) + ": " + e.getMessage());
					}

					return lazyTags;
				}
			};
		}
		lazyModel.setRowCount(rowCount);
		
		return lazyModel;
	}

	public CmsAdminService getCmsAdminService() {
		if(cmsAdminService==null){
			cmsAdminService = new CmsAdminServiceImpl();
		}
		return cmsAdminService;
	}

	// JSF methods

	public void getTags() throws AlvsanandException {
		logger.info("Launched TagBean.getTags");		
	}

	public String newTag() throws AlvsanandException {
		logger.info("Launched TagBean.newTag");
		
		name = null;
		description = null;

		return NEW_TAG_VIEW_ID;
	}

	public String editTag() throws AlvsanandException {
		logger.info("Launched TagBean.editTag[" + name + "]");
		
		Tag tagTmp = new Tag();
		tagTmp.setName(name);

		Tag tag = getCmsAdminService().getTag(tagTmp);
		name = tag.getName();
		description = tag.getDescription();

		return EDIT_TAG_VIEW_ID;
	}

	public String deleteTag() throws AlvsanandException {
		logger.info("Launched TagBean.deleteTag[" + name + "]");
		
		Tag tag = new Tag();
		tag.setName(name);

		getCmsAdminService().deleteTag(tag);

		return LIST_TAG_VIEW_ID;
	}

	public String saveTag() throws AlvsanandException {
		logger.info("Launched TagBean.saveTag[" + name + "]");
		
		Tag tag = new Tag();
		tag.setName(name);
		tag.setDescription(description);

		getCmsAdminService().saveOrUpdateTag(tag);

		return LIST_TAG_VIEW_ID;
	}

	public void validateName(FacesContext context, UIComponent validate, Object value) throws AlvsanandException {
		logger.info("Launched TagBean.validateName");
		
		String name = (String) value;

		if (!StringUtils.isEmpty(name)) {
			int count = 0;
			try {
				count = getCmsAdminService().getTagCount(name);
			} catch (ServiceException ex) {
				throw ex;
			}

			if (count > 0) {
				((UIInput) validate).setValid(false);

				FacesMessage message = new FacesMessage();
				message.setDetail(MessageResources.getMessage(MessageResources.ERROR_RESOURCE_BUNDLE_NAME, "error.form.validation.validateTagName.detail", null));
				message.setSummary(MessageResources.getMessage(MessageResources.ERROR_RESOURCE_BUNDLE_NAME, "error.form.validation.validateTagName.summary", null));
				message.setSeverity(FacesMessage.SEVERITY_ERROR);

				throw new ValidatorException(message);
			}
		}
	}
}
