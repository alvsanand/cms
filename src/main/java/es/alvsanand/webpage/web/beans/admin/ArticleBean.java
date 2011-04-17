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

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
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
import org.primefaces.event.FileUploadEvent;
import org.primefaces.event.RateEvent;
import org.primefaces.model.DualListModel;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.UploadedFile;

import com.google.appengine.api.datastore.Text;

import es.alvsanand.webpage.AlvsanandException;
import es.alvsanand.webpage.common.FacesUtils;
import es.alvsanand.webpage.common.Globals;
import es.alvsanand.webpage.common.Logger;
import es.alvsanand.webpage.common.MessageResources;
import es.alvsanand.webpage.common.XMLUtils;
import es.alvsanand.webpage.db.QueryBean;
import es.alvsanand.webpage.db.QueryBean.OrderBean;
import es.alvsanand.webpage.model.Article;
import es.alvsanand.webpage.model.ArticleVersion;
import es.alvsanand.webpage.model.Rating;
import es.alvsanand.webpage.model.Tag;
import es.alvsanand.webpage.model.User;
import es.alvsanand.webpage.model.bulkUpload.ArticleBulkUpload;
import es.alvsanand.webpage.services.ServiceException;
import es.alvsanand.webpage.services.admin.CmsAdminService;
import es.alvsanand.webpage.services.admin.CmsAdminServiceImpl;

@SessionScoped
@ManagedBean(name="articleBean")
public class ArticleBean implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 8834258652731878852L;

	private transient static final Logger logger = new Logger(ArticleBean.class);
	
	private transient static final String DEFAULT_SORT_FIELD = "date";
	
	private transient static final String LIST_ARTICLE_VIEW_ID = "pretty:listArticle";
	private transient static final String SHOW_ARTICLE_VIEW_ID = "pretty:article";
	private transient static final String EDIT_ARTICLE_VIEW_ID = "/xhtml/secured/admin/articles/edit.jsf";
	private transient static final String NEW_ARTICLE_VIEW_ID = "/xhtml/secured/admin/articles/edit.jsf";

	private transient CmsAdminService cmsAdminService;

	private String idArticle;

	private String idArticleVersion;

	private String name;

	private String title;

	private String data;

	private Date date;

	private Date modifiedDate;

	private String state;
	
	private double ratingNumber;

	private ArticleBulkUpload articleBulkUpload;

	private boolean bulkUpload;
	
	private LazyDataModel<Article> lazyModel;

	private Map<String, Boolean> selectedArticles = new HashMap<String, Boolean>();
	
	private DualListModel<String> tags;
	
	private Map<String,String> articleVersions = new HashMap<String, String>();

	public ArticleBean(){
	}

	public String getIdArticle() {
		return idArticle;
	}

	public void setIdArticle(String idArticle) {
		this.idArticle = idArticle;
	}

	public String getIdArticleVersion() {
		return idArticleVersion;
	}

	public void setIdArticleVersion(String idArticleVersion) {
		this.idArticleVersion = idArticleVersion;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getTitle() {
		return title;
	}

	public void setData(String data) {
		this.data = data;
	}

	public String getData() {
		return data;
	}

	public Date getModifiedDate() {
		return modifiedDate;
	}

	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public LazyDataModel<Article> getLazyModel() throws ServiceException {	
		int rowCount = getCmsAdminService().getArticleCount();
		if(lazyModel==null){	
			lazyModel = new LazyDataModel<Article>() {			
				/**
				 * 
				 */
				private static final long serialVersionUID = 444907527835413595L;

				@Override
				public List<Article> load(int first, int pageSize, String sortField, boolean sortOrder, Map<String,String> filters) {
					logger.info("Loading the article list between " + first + " and " + (first + pageSize));
	
					List<Article> lazyArticles = null;
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
							OrderBean orderBean = new OrderBean(false,DEFAULT_SORT_FIELD);
							List<OrderBean> orderBeans = new java.util.ArrayList<OrderBean>();
							orderBeans.add(orderBean);					
							queryBean.setOrderBeans(orderBeans);
						}
						
						lazyArticles = getCmsAdminService().getArticles(queryBean);
					} catch (ServiceException e) {
						logger.error("Error loading the article list between " + first + " and " + (first+pageSize) + ": " + e.getMessage());
					}
	
					return lazyArticles;
				}
			};
		}
		lazyModel.setRowCount(rowCount);		
		
		return lazyModel;
	}

	public Map<String, Boolean> getSelectedArticles() {
		return selectedArticles;
	}

	public void setSelectedArticles(Map<String, Boolean> selectedArticles) {
		this.selectedArticles = selectedArticles;
	}

	public DualListModel<String> getTags() {
		return tags;
	}

	public void setTags(DualListModel<String> tags) {
		this.tags = tags;
	}

	public Map<String, String> getArticleVersions() {
		return articleVersions;
	}

	public void setArticleVersions(Map<String, String> articleVersions) {
		this.articleVersions = articleVersions;
	}

	public CmsAdminService getCmsAdminService() {
		if(cmsAdminService==null){
			cmsAdminService = new CmsAdminServiceImpl();
		}
		return cmsAdminService;
	}

	public boolean isBulkUpload() {
		return bulkUpload;
	}

	public void setBulkUpload(boolean bulkUpload) {
		this.bulkUpload = bulkUpload;
	}

	public double getRatingNumber() {
		return ratingNumber;
	}

	public void setRatingNumber(double ratingNumber) {
		this.ratingNumber = ratingNumber;
	}

	// JSF methods

	public void getArticles() throws AlvsanandException {
		logger.info("Launched ArticleBean.getArticles");
		bulkUpload = false;
		articleBulkUpload = null;
	}

	public String newArticle() throws AlvsanandException {
		logger.info("Launched ArticleBean.newArticle");
		
		idArticle = null;
		data = null;
		date = null;
		modifiedDate = null;
		name = null;
		title = null;
		
		tags = new DualListModel<String>();
		
		List<Tag> allTags = getCmsAdminService().getTags(null);
		
		List<String> source = new ArrayList<String>();
		List<String> target = new ArrayList<String>();
		
		if(allTags!=null){			
			for(Tag tag: allTags){
				source.add(XMLUtils.repareText(tag.getName()));
			}
		}
		tags.setSource(source);
		tags.setTarget(target);

		return NEW_ARTICLE_VIEW_ID;
	}

	public String editArticle() throws AlvsanandException {
		logger.info("Launched ArticleBean.editArticle[" + idArticle + "]");
		
		Article articleTmp = new Article();
		articleTmp.setIdArticle(idArticle);

		Article article = getCmsAdminService().getArticle(articleTmp);
		name = article.getName();
		date = article.getDate();
		title = article.getTitle();
		data = article.getDataValue();
		
		tags = new DualListModel<String>();
		
		List<Tag> articleTags = article.getTags();
		List<Tag> allTags = getCmsAdminService().getTags(null);
		
		List<String> source = new ArrayList<String>();
		List<String> target = new ArrayList<String>();
		if(articleTags!=null && tags!=null){			
			for(Tag tag: allTags){
				boolean foundTag = false;
				for(Tag tag2: articleTags){
					if(tag2.getName()!=null && tag2.getName().equals(tag.getName())){
						foundTag = true;
					}
				}
				if(foundTag){
					target.add(XMLUtils.repareText(tag.getName()));
				}
				else{
					source.add(XMLUtils.repareText(tag.getName()));
				}
			}
		}
		tags.setSource(source);
		tags.setTarget(target);
		
		articleVersions = new LinkedHashMap<String, String>();
		
		int maxNumberversion = 0;
		if(article!=null && article.getArticleVersions()!=null){
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat(Globals.DEFAULT_DATE_FORMAT);
			for(ArticleVersion articleVersion: article.getArticleVersions()){
				articleVersions.put(Integer.toString(articleVersion.getNumberversion()) + "(" + simpleDateFormat.format(articleVersion.getDate()) + ")", articleVersion.getIdArticleVersion());
			
				if(articleVersion.getNumberversion() > maxNumberversion){
					idArticleVersion = articleVersion.getIdArticleVersion();
					maxNumberversion = articleVersion.getNumberversion();
				}
			}			
		}
		
		return EDIT_ARTICLE_VIEW_ID;
	}

	public String deleteArticle() throws AlvsanandException {
		logger.info("Launched ArticleBean.deleteArticle[" + idArticle + "]");
		
		Article article = new Article();
		article.setIdArticle(idArticle);

		getCmsAdminService().deleteArticle(article);

		return LIST_ARTICLE_VIEW_ID;
	}

	public String saveArticle() throws AlvsanandException {
		logger.info("Launched ArticleBean.saveArticle[" + idArticle + "]");
		
		Article article = null;
		
		User actualUser = (User)FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get(Globals.SES_USER);
		
		Date actualDate = new Date();
		if(StringUtils.isEmpty(idArticle)){
			article = new Article();
			article.setName(es.alvsanand.webpage.common.StringUtils.getValidName(name));
			article.setDate(actualDate);
			article.setModifiedDate(actualDate);
			article.setData(new Text(data));
			article.setTitle(title);			
			article.setUser(actualUser);
			
			ArticleVersion articleVersion = new ArticleVersion();
			articleVersion.setData(new Text(data));
			articleVersion.setDate(actualDate);
			articleVersion.setTitle(title);
			articleVersion.setUser(actualUser);
			
			List<ArticleVersion> articleVersions = new ArrayList<ArticleVersion>();
			articleVersions.add(articleVersion);			
			
			article.setArticleVersions(articleVersions);
			
			if(tags.getTarget()!=null && tags.getTarget().size()>0){
				List<Tag> articleTags = new ArrayList<Tag>();
				for(String tagName: tags.getTarget()){
					Tag tag = new Tag(tagName, null);
					tag.setAsociated(true);
					
					articleTags.add(tag);
				}
				article.setTags(articleTags);
			}
		}
		else{
			Article articleTmp = new Article();
			articleTmp.setIdArticle(idArticle);
	
			article = getCmsAdminService().getArticle(articleTmp);

			article.setData(new Text(data));
			article.setModifiedDate(actualDate);
			article.setTitle(title);
			article.setUser(actualUser);
			
			ArticleVersion articleVersion = new ArticleVersion();
			articleVersion.setData(new Text(data));
			articleVersion.setDate(actualDate);
			articleVersion.setTitle(title);
			articleVersion.setUser(actualUser);
			
			
			List<ArticleVersion> articleVersions = new ArrayList<ArticleVersion>();
			articleVersions.add(articleVersion);
			
			article.setArticleVersions(articleVersions);
			
			if(tags.getTarget()!=null && tags.getTarget().size()>0){
				List<Tag> articleTags = new ArrayList<Tag>();
				for(String tagName: tags.getTarget()){
					Tag tag = new Tag(tagName, null);
					tag.setAsociated(true);
					
					articleTags.add(tag);
				}
				article.setTags(articleTags);
			}
			
		}

		getCmsAdminService().saveOrUpdateArticle(article);

		return LIST_ARTICLE_VIEW_ID;
	}

	public String updateStateArticles() throws AlvsanandException {
		logger.info("Launched ArticleBean.updateStateArticles[" + selectedArticles.keySet() + "]");
		
		List<Article> articleList = new java.util.ArrayList<Article>();
		
		if(selectedArticles!=null){
			for(String idArticle: selectedArticles.keySet()){				
				if(selectedArticles.get(idArticle)!=null && selectedArticles.get(idArticle).booleanValue()){
					Article articleTmp = new Article();
					articleTmp.setIdArticle(idArticle);
		
					Article article = getCmsAdminService().getArticle(articleTmp);
		
					article.setState(Integer.parseInt(state));
					
					articleList.add(article);
				}
			}
		}

		getCmsAdminService().saveOrUpdateArticles(articleList);

		return LIST_ARTICLE_VIEW_ID;
	}

	public void articleBulkUpload() throws AlvsanandException {
		logger.info("Launched ArticleBean.articleBulkUpload");
			
		FacesContext context = FacesContext.getCurrentInstance();
		UIComponent fileUploadComponent = FacesUtils.findComponent(context.getViewRoot(), "fileUpload");
		
		try{			
			cmsAdminService.saveOrUpdateArticles(convertToArticleList(articleBulkUpload));
			
			FacesMessage message = new FacesMessage();
			message.setDetail(MessageResources.getMessage(MessageResources.ADMIN_RESOURCE_BUNDLE_NAME, "admin.article.articleBulkUpload.completed.detail", null));
			message.setSummary(MessageResources.getMessage(MessageResources.ADMIN_RESOURCE_BUNDLE_NAME, "admin.article.articleBulkUpload.completed.summary", null));
			message.setSeverity(FacesMessage.SEVERITY_INFO);
			
			FacesContext.getCurrentInstance().addMessage(fileUploadComponent.getClientId(context), message);
		}
		catch(ServiceException serviceException){			
			FacesMessage message = new FacesMessage();
			message.setDetail(MessageResources.getMessage(MessageResources.ERROR_RESOURCE_BUNDLE_NAME, "error.form.validation.loadArticleBulkUpload.unknown.detail", null));
			message.setSummary(MessageResources.getMessage(MessageResources.ERROR_RESOURCE_BUNDLE_NAME, "error.form.validation.loadArticleBulkUpload.unknown.summary", null));
			message.setSeverity(FacesMessage.SEVERITY_ERROR);
			
			FacesContext.getCurrentInstance().addMessage(fileUploadComponent.getClientId(context), message);
		}
		setBulkUpload(false);
	}
	
	public String changeArticleVersion() throws AlvsanandException{
		logger.info("Launched ArticleBean.changeArticleVersion[articleVersion=" + idArticleVersion + "]");
		
		ArticleVersion articleVersionTmp = new ArticleVersion();
		articleVersionTmp.setIdArticleVersion(idArticleVersion);

		ArticleVersion articleVersion = getCmsAdminService().getArticleVersion(articleVersionTmp);
		title = articleVersion.getTitle();
		data = articleVersion.getData().getValue();
		
		return EDIT_ARTICLE_VIEW_ID;
	}

	public void validateName(FacesContext context, UIComponent validate, Object value) throws AlvsanandException {
		logger.info("Launched ArticleBean.validateName");
		
		String name = (String) value;

		if (!StringUtils.isEmpty(name)) {
			int count = 0;
			try {
				count = getCmsAdminService().getArticleCount(es.alvsanand.webpage.common.StringUtils.getValidName(name));
			} catch (ServiceException ex) {
				throw ex;
			}

			if (count > 0) {
				((UIInput) validate).setValid(false);

				FacesMessage message = new FacesMessage();
				message.setDetail(MessageResources.getMessage(MessageResources.ERROR_RESOURCE_BUNDLE_NAME, "error.form.validation.validateArticleName.detail", null));
				message.setSummary(MessageResources.getMessage(MessageResources.ERROR_RESOURCE_BUNDLE_NAME, "error.form.validation.validateArticleName.summary", null));
				message.setSeverity(FacesMessage.SEVERITY_ERROR);

				throw new ValidatorException(message);
			}
		}
	}

	public void handleFileUpload(FileUploadEvent fileUploadEvent) {
		logger.info("Uploaded: {" + fileUploadEvent.getFile().getFileName()+"}");

		loadArticleBulkUpload(fileUploadEvent.getFile());
	}
	
	private void loadArticleBulkUpload(UploadedFile uploadedFile){		
		
		FacesContext context = FacesContext.getCurrentInstance();
        UIComponent fileUploadComponent = FacesUtils.findComponent(context.getViewRoot(), "fileUpload");
        
		InputStream inputStream = null;
		try {
			inputStream = uploadedFile.getInputstream();
		} catch (IOException e1) {
			setBulkUpload(false);
			
            FacesMessage message = new FacesMessage();
			message.setDetail(MessageResources.getMessage(MessageResources.ERROR_RESOURCE_BUNDLE_NAME, "error.form.validation.loadArticleBulkUpload.badFile.detail", null));
			message.setSummary(MessageResources.getMessage(MessageResources.ERROR_RESOURCE_BUNDLE_NAME, "error.form.validation.loadArticleBulkUpload.badFile.summary", null));
			message.setSeverity(FacesMessage.SEVERITY_ERROR);
			
			FacesContext.getCurrentInstance().addMessage(fileUploadComponent.getClientId(context), message);
			return;
		}
		
		try {
			articleBulkUpload = ArticleBulkUpload.unmarshal(new InputStreamReader(inputStream));
			
		} catch (Exception e) {
			setBulkUpload(false);
			
            FacesMessage message = new FacesMessage();
			message.setDetail(MessageResources.getMessage(MessageResources.ERROR_RESOURCE_BUNDLE_NAME, "error.form.validation.loadArticleBulkUpload.badFile.detail", null));
			message.setSummary(MessageResources.getMessage(MessageResources.ERROR_RESOURCE_BUNDLE_NAME, "error.form.validation.loadArticleBulkUpload.badFile.summary", null));
			message.setSeverity(FacesMessage.SEVERITY_ERROR);
			
			FacesContext.getCurrentInstance().addMessage(fileUploadComponent.getClientId(context), message);
			return;
		}
		
		try{
			for(es.alvsanand.webpage.model.bulkUpload.Article article: articleBulkUpload.getArticle()){
				validateArticle(article);
			}
			setBulkUpload(true);
		}
		catch(ValidatorException validatorException){
			setBulkUpload(false);         
			FacesContext.getCurrentInstance().addMessage(fileUploadComponent.getClientId(context), validatorException.getFacesMessage());
		}
	}

	public void validateArticle(es.alvsanand.webpage.model.bulkUpload.Article article) throws ValidatorException {
		if (article!=null && StringUtils.isNotEmpty(article.getName()) && StringUtils.isNotEmpty(article.getTitle())
				&& article.getDate()!=null && article.getData()!=null && StringUtils.isNotEmpty(article.getData().toString())) {			
			int count = 0;
			try {
				count = getCmsAdminService().getArticleCount(es.alvsanand.webpage.common.StringUtils.getValidName(article.getName()));
			} catch (ServiceException ex) {
				FacesMessage message = new FacesMessage();
				message.setDetail(MessageResources.getMessage(MessageResources.ERROR_RESOURCE_BUNDLE_NAME, "error.form.validation.loadArticleBulkUpload.unknown.detail", null));
				message.setSummary(MessageResources.getMessage(MessageResources.ERROR_RESOURCE_BUNDLE_NAME, "error.form.validation.loadArticleBulkUpload.unknown.summary", null));
				message.setSeverity(FacesMessage.SEVERITY_ERROR);
	
				throw new ValidatorException(message);
			}

			if (count > 0) {
				FacesMessage message = new FacesMessage();
				message.setDetail(MessageResources.getMessage(MessageResources.ERROR_RESOURCE_BUNDLE_NAME, "error.form.validation.loadArticleBulkUpload.badName.detail", new String[]{es.alvsanand.webpage.common.StringUtils.getValidName(article.getName())}));
				message.setSummary(MessageResources.getMessage(MessageResources.ERROR_RESOURCE_BUNDLE_NAME, "error.form.validation.loadArticleBulkUpload.badName.summary", new String[]{es.alvsanand.webpage.common.StringUtils.getValidName(article.getName())}));
				message.setSeverity(FacesMessage.SEVERITY_ERROR);

				throw new ValidatorException(message);
			}
		}
		else{
			FacesMessage message = new FacesMessage();
			message.setDetail(MessageResources.getMessage(MessageResources.ERROR_RESOURCE_BUNDLE_NAME, "error.form.validation.loadArticleBulkUpload.badContent.detail", null));
			message.setSummary(MessageResources.getMessage(MessageResources.ERROR_RESOURCE_BUNDLE_NAME, "error.form.validation.loadArticleBulkUpload.badContent.summary", null));
			message.setSeverity(FacesMessage.SEVERITY_ERROR);

			throw new ValidatorException(message);
		}
	}
	
	private List<Article> convertToArticleList(ArticleBulkUpload articleBulkUpload){
		User user = (User)FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get(Globals.SES_USER);
		
		List<Article> articles = new ArrayList<Article>();
		for(es.alvsanand.webpage.model.bulkUpload.Article bulkUploadArticle: articleBulkUpload.getArticle()){
			Article article = new Article();
			article.setName(es.alvsanand.webpage.common.StringUtils.getValidName(bulkUploadArticle.getName()));
			article.setTitle(bulkUploadArticle.getTitle());
			article.setDate(bulkUploadArticle.getDate());
			article.setData(new Text(bulkUploadArticle.getData()));
			article.setUser(user);
			
			ArticleVersion articleVersion = new ArticleVersion();
			articleVersion.setData(new Text(bulkUploadArticle.getData()));
			articleVersion.setDate(bulkUploadArticle.getDate());
			articleVersion.setTitle(bulkUploadArticle.getTitle());
			articleVersion.setUser(user);
			
			List<ArticleVersion> articleVersions = new ArrayList<ArticleVersion>();
			articleVersions.add(articleVersion);			
			
			article.setArticleVersions(articleVersions);
			
			articles.add(article);
		}
		
		return articles;
	}

	public void handleRateListener(RateEvent rateEvent) throws AlvsanandException {
		logger.info("Launched ArticleBean.saveRating[" + idArticle + "]");
		
		ratingNumber = rateEvent.getRating();
	}

	public String saveRating() throws AlvsanandException {
		logger.info("Launched ArticleBean.saveRating[" + idArticle + "]");
		
		User actualUser = (User)FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get(Globals.SES_USER);
		
		Rating rating = new Rating();
		
		rating.setIdArticle(idArticle);
		rating.setRatingNumber(ratingNumber);
		rating.setUser(actualUser);
		rating.setDate(new Date());	

		getCmsAdminService().saveOrUpdateRating(rating);
		
		return SHOW_ARTICLE_VIEW_ID;
	}
}
