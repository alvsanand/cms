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
package es.alvsanand.webpage.web.beans.cms;

import java.io.Serializable;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;

import es.alvsanand.webpage.AlvsanandException;
import es.alvsanand.webpage.common.AlvsanandProperties;
import es.alvsanand.webpage.common.Globals;
import es.alvsanand.webpage.common.Logger;
import es.alvsanand.webpage.common.tree.ArticleDataTree;
import es.alvsanand.webpage.model.Article;
import es.alvsanand.webpage.model.Avatar;
import es.alvsanand.webpage.model.Rating;
import es.alvsanand.webpage.model.Tag;
import es.alvsanand.webpage.model.TagCount;
import es.alvsanand.webpage.model.User;
import es.alvsanand.webpage.services.ServiceException;
import es.alvsanand.webpage.services.cms.CmsService;
import es.alvsanand.webpage.services.cms.CmsServiceImpl;
import es.alvsanand.webpage.web.common.ElParameterMap;
import es.alvsanand.webpage.web.dynamicContent.DynamicContentHandler;

@SessionScoped
@ManagedBean(name = "homeBean")
public class HomeBean implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 5568572677374816374L;

	private transient static final Logger logger = new Logger(HomeBean.class);

	public transient static final int ARTICLE_PER_PAGE = 10;

	public transient static final String[] DEFAULT_SORT_FIELDS = { "date" };

	private transient CmsService cmsService;

	private int offset;

	private String name;

	private List<Article> articles;

	private Article article;

	private transient TreeNode treeNode;

	boolean focusComments;

	public HomeBean() {
	}

	public int getOffset() {
		return offset;
	}

	public void setOffset(int offset) {
		this.offset = offset;
	}

	public List<Article> getArticles() {
		return articles;
	}

	public void setArticles(List<Article> articles) {
		this.articles = articles;
	}

	public Article getArticle() {
		return article;
	}

	public void setArticle(Article article) {
		this.article = article;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean isFocusComments() {
		return focusComments;
	}

	public void setFocusComments(boolean focusComments) {
		this.focusComments = focusComments;
	}

	public CmsService getCmsService() {
		if (cmsService == null) {
			cmsService = new CmsServiceImpl();
		}
		return cmsService;
	}

	public TreeNode getTreeNode() throws ServiceException {
		if (treeNode == null || !getCmsService().existsArticleTree()) {
			Map<Date, Map<Date, Map<Date, List<Article>>>> articlesTree = getCmsService().getArticleTree();

			treeNode = new DefaultTreeNode("Root", null);

			for (Date yearDate : articlesTree.keySet()) {
				Calendar yearCalendar = new GregorianCalendar();
				yearCalendar.setTime(yearDate);

				ArticleDataTree yearArticleDataTree = new ArticleDataTree();
				yearArticleDataTree.setText(Integer.toString(yearCalendar.get(Calendar.YEAR)));

				yearArticleDataTree.setDate(Integer.toString(yearCalendar.get(Calendar.YEAR)));

				DefaultTreeNode yearTree = new DefaultTreeNode(yearArticleDataTree, treeNode);

				int yearArticleCounter = 0;

				for (Date monthDate : articlesTree.get(yearDate).keySet()) {
					Calendar monthCalendar = new GregorianCalendar();
					monthCalendar.setTime(monthDate);

					ArticleDataTree monthArticleDataTree = new ArticleDataTree();
					monthArticleDataTree.setText(Integer.toString(monthCalendar.get(Calendar.MONTH) + 1));

					monthArticleDataTree.setDate(Integer.toString(monthCalendar.get(Calendar.YEAR)) + Globals.DATE_SEPARATOR
							+ Integer.toString(monthCalendar.get(Calendar.MONTH) + 1));

					DefaultTreeNode monthTree = new DefaultTreeNode(monthArticleDataTree, yearTree);

					int monthArticleCounter = 0;

					for (Date dayDate : articlesTree.get(yearDate).get(monthDate).keySet()) {
						Calendar dayCalendar = new GregorianCalendar();
						dayCalendar.setTime(dayDate);

						ArticleDataTree dayArticleDataTree = new ArticleDataTree();
						dayArticleDataTree.setLength(articlesTree.get(yearDate).get(monthDate).get(dayDate).size());
						dayArticleDataTree.setText(Integer.toString(dayCalendar.get(Calendar.DAY_OF_MONTH)));

						dayArticleDataTree.setDate(Integer.toString(dayCalendar.get(Calendar.YEAR)) + Globals.DATE_SEPARATOR
								+ Integer.toString(dayCalendar.get(Calendar.MONTH) + 1) + Globals.DATE_SEPARATOR
								+ Integer.toString(dayCalendar.get(Calendar.DAY_OF_MONTH)));

						new DefaultTreeNode(dayArticleDataTree, monthTree);

						monthArticleCounter += articlesTree.get(yearDate).get(monthDate).get(dayDate).size();
					}

					monthArticleDataTree.setLength(monthArticleCounter);

					yearArticleCounter += monthArticleCounter;
				}

				yearArticleDataTree.setLength(yearArticleCounter);
			}
		}
		return treeNode;
	}

	public List<TagCount> getTagCountList(int maxTags, Comparator<TagCount> comparator) throws ServiceException {
		Map<Tag, Integer> map = getCmsService().getTagMap();

		List<TagCount> tagCountList = new ArrayList<TagCount>();

		if (map != null) {
			for (Tag tag : map.keySet()) {
				TagCount tagCount = new TagCount();
				tagCount.setName(tag.getName());
				tagCount.setDescription(tag.getDescription());
				tagCount.setCount(map.get(tag).intValue());

				tagCountList.add(tagCount);
			}
		}	

		Collections.sort(tagCountList, TagCount.COUNT_ORDER_DESC);
		
		return tagCountList.subList(0, ((maxTags==-1 || maxTags>tagCountList.size())?tagCountList.size():maxTags));
	}

	public List<TagCount> getTagCountListSortedByName() throws ServiceException {
		return getTagCountList(-1, TagCount.NAME_ORDER);		
	}

	public List<TagCount> getTagCountListSortedByCountForMenu() throws ServiceException {
		return getTagCountList(4, TagCount.COUNT_ORDER_DESC);
	}

	public List<TagCount> getTagCountListSortedByCount() throws ServiceException {
		return getTagCountList(-1, TagCount.COUNT_ORDER_DESC);
	}

	public List<es.alvsanand.webpage.common.photo.Album> getAlbums() throws ServiceException {
		return cmsService.getAlbums();
	}
	
	public ElParameterMap<String, String> getAvatarImage() throws AlvsanandException {
		return new ElParameterMap<String, String>(){
			@Override
			public String get(Object idUser) {			
				User user = new User();
				user.setIdUser((String)idUser);		
				
				Avatar avatar = null;
				try {
					avatar = cmsService.getAvatar(user);
					
					if(avatar==null){
						return null;
					}
					
					return DynamicContentHandler.addContent(avatar.getData().getBytes(), avatar.getMediaType());
				} catch (ServiceException e) {
					return null;
				}
			}
		};
	}
	
	public ElParameterMap<String, Boolean> getHasAvatarImage() throws AlvsanandException {
		return new ElParameterMap<String, Boolean>(){
			@Override
			public Boolean get(Object idUser) {			
				User user = new User();
				user.setIdUser((String)idUser);		
				
				Avatar avatar = null;
				try {
					avatar = cmsService.getAvatar(user);
					
					if(avatar==null){
						return Boolean.FALSE;
					}
					else{
						return Boolean.TRUE;
					}
				} catch (ServiceException e) {
					return null;
				}
			}
		};
	}

	// JSF methods

	public void home() throws AlvsanandException {
		this.offset = 0;
		showArticles();
	}

	public void showArticles() throws AlvsanandException {
		logger.info("Launched HomeBean.showArticles[" + offset + "]");

		articles = getCmsService().getArticles(offset);
	}

	public void showArticle() throws AlvsanandException {
		logger.info("Launched HomeBean.showArticle[" + name + "]");

		Article _article = new Article();
		_article.setName(name);

		article = getCmsService().getArticle(_article);
	}

	public boolean getCanForward() {
		try {
			int count = getCmsService().getArticlesCount();
			if (offset + ARTICLE_PER_PAGE < count) {
				return true;
			}
			return false;
		} catch (ServiceException serviceException) {
			return false;
		}
	}

	public boolean getCanBackward() throws AlvsanandException {
		if (offset - ARTICLE_PER_PAGE >= 0) {
			return true;
		}
		return false;
	}

	public void showNextArticles() throws AlvsanandException {
		offset = offset + ARTICLE_PER_PAGE;

		showArticles();
	}

	public void showPreviousArticles() throws AlvsanandException {
		offset = offset - ARTICLE_PER_PAGE;

		showArticles();
	}

	public String getArticleUrl() {
		String domain = AlvsanandProperties.getProperty(Globals.DOMAIN_CONFIG_KEY);
		String contextPath = FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath();
		String name = this.getName();

		return FacesContext.getCurrentInstance().getExternalContext()
				.encodeActionURL(MessageFormat.format(Globals.ARTICLE_URL_FORMAT, new String[] { domain, contextPath, name }));
	}

	public String getArticleUrlWithoutProtocol() {
		String domain = AlvsanandProperties.getProperty(Globals.DOMAIN_CONFIG_KEY);
		String contextPath = FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath();
		String name = this.getName();

		return FacesContext.getCurrentInstance().getExternalContext()
				.encodeActionURL(MessageFormat.format(Globals.ARTICLE_URL_FORMAT, new String[] { domain, contextPath, name }));
	}

	public String getEncodedArticleTitle() {
		if (article != null && article.getTitle() != null) {
			return FacesContext.getCurrentInstance().getExternalContext().encodeActionURL(article.getTitle());
		} else {
			return "";
		}
	}

	public boolean getCanRate() {
		User actualUser = (User) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get(Globals.SES_USER);

		if (actualUser == null) {
			return false;
		}

		if (article != null && article.getRatings() != null && article.getRatings().size() > 0) {
			for (Rating rating : article.getRatings()) {
				if (rating.getIdUser().equals(actualUser.getIdUser())) {
					return false;
				}
			}
			return true;
		} else {
			return true;
		}
	}
}