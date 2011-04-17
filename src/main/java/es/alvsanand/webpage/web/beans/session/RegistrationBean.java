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
package es.alvsanand.webpage.web.beans.session;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.net.FileNameMap;
import java.net.URLConnection;
import java.security.NoSuchAlgorithmException;
import java.util.Date;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang.StringUtils;
import org.primefaces.event.FileUploadEvent;

import com.google.appengine.api.datastore.Blob;

import es.alvsanand.webpage.AlvsanandException;
import es.alvsanand.webpage.common.FacesUtils;
import es.alvsanand.webpage.common.Globals;
import es.alvsanand.webpage.common.Logger;
import es.alvsanand.webpage.common.MessageResources;
import es.alvsanand.webpage.model.Avatar;
import es.alvsanand.webpage.model.Role;
import es.alvsanand.webpage.model.User;
import es.alvsanand.webpage.model.UserState;
import es.alvsanand.webpage.model.security.GrantedAuthorityImpl;
import es.alvsanand.webpage.services.ServiceException;
import es.alvsanand.webpage.services.security.CryptographyService;
import es.alvsanand.webpage.services.security.CryptographyServiceImpl;
import es.alvsanand.webpage.services.session.UserService;
import es.alvsanand.webpage.services.session.UserServiceImpl;

/**
 * This bean process the flow for the user operations
 * 
 * @author alvaro.santos
 * @date 18/11/2009
 * 
 */
@SessionScoped
@ManagedBean(name="registrationBean")
public class RegistrationBean implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1602676211690700905L;

	private transient static final Logger logger = new Logger(RegistrationBean.class);
	
	private transient static final String END_REGISTRATION_VIEW_ID = "/xhtml/session/endRegistration.jsf";
	
	private transient static final String END_RESET_PASSWORD_VIEW_ID = "/xhtml/session/endResetPassword.jsf";
	
	private transient static final String MODIFY_PERSONAL_DATA_VIEW_ID = "/xhtml/secured/modifyPersonalData.jsf";

	private transient CryptographyService cryptographyService;

	private transient UserService userService;

	private String loginName;

	private String name;

	private String password;

	private String rePassword;

	private String surname;

	private String email;

	private String recaptcha;

	private boolean privacy;

	private transient UIInput passwordComponent;

	private String registrationHash;

	private boolean savedPersonalData;

	private byte[] photoData;
	
	private String photoMediaType;
	
	private boolean showFileUpload;

	public RegistrationBean() {
	}

	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getRePassword() {
		return rePassword;
	}

	public void setRePassword(String rePassword) {
		this.rePassword = rePassword;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getRecaptcha() {
		return recaptcha;
	}

	public void setRecaptcha(String recaptcha) {
		this.recaptcha = recaptcha;
	}

	public UIInput getPasswordComponent() {
		return passwordComponent;
	}

	public void setPasswordComponent(UIInput passwordComponent) {
		this.passwordComponent = passwordComponent;
	}

	public boolean isPrivacy() {
		return privacy;
	}

	public void setPrivacy(boolean privacy) {
		this.privacy = privacy;
	}

	public String getRegistrationHash() {
		return registrationHash;
	}

	public void setRegistrationHash(String registrationHash) {
		this.registrationHash = registrationHash;
	}

	public boolean isSavedPersonalData() {
		return savedPersonalData;
	}

	public void setSavedPersonalData(boolean savedPersonalData) {
		this.savedPersonalData = savedPersonalData;
	}

	public byte[] getPhotoData() {
		return photoData;
	}

	public void setPhotoData(byte[] photoData) {
		this.photoData = photoData;
	}

	public String getPhotoMediaType() {
		return photoMediaType;
	}

	public void setPhotoMediaType(String photoMediaType) {
		this.photoMediaType = photoMediaType;
	}

	public UserService getUserService() {
		if(userService==null){
			userService = new UserServiceImpl();
		}
		
		return userService;
	}

	public CryptographyService getCryptographyService() {
		if(cryptographyService==null){
			cryptographyService = new CryptographyServiceImpl();
		}
		return cryptographyService;
	}

	public boolean isShowFileUpload() {
		return showFileUpload;
	}

	public void setShowFileUpload(boolean showFileUpload) {
		this.showFileUpload = showFileUpload;
	}

	// JSF methods

	public void create() {
		logger.info("Launched RegistrationBean.create");

		loginName = null;
		name = null;
		password = null;
		rePassword = null;
		surname = null;
		email = null;
		recaptcha = null;
		privacy = false;
		registrationHash = null;
		photoData = null;
		photoMediaType = null;
		showFileUpload = false;
	}

	public String save() throws AlvsanandException {
		logger.info("Launched RegistrationBean.save[" + loginName + "]");
		
		User user = new User();

		user.setLoginName(loginName);
		user.setPassword(password);
		user.setName(loginName);
		user.setSurname(surname);
		user.setEmail(email);

		Role role = new Role();
		role.setName(GrantedAuthorityImpl.ROLE_GENERIC_USER.getAuthority());
		user.setRole(role);
		user.setState(UserState.REGISTERED.getValue());
		
		if (user != null && user.getPassword() != null) {
			try {
				String passwordDigested = new String(Base64.encodeBase64(getCryptographyService().digest(user.getPassword().getBytes())));

				user.setPassword(passwordDigested);
				
				String registrationHash = es.alvsanand.webpage.common.StringUtils.generateRandomString();
				
				user.setRegistrationHash(registrationHash);
			} catch (NoSuchAlgorithmException noSuchAlgorithmException) {
			}
		}

		getUserService().saveUser(user);

		getUserService().sendActivationEmail(user);
		
		
		FacesMessage message = new FacesMessage();
		message.setDetail(MessageResources.getMessage(MessageResources.REGISTRATION_RESOURCE_BUNDLE_NAME, "registration.end.completed.detail", null));
		message.setSummary(MessageResources.getMessage(MessageResources.REGISTRATION_RESOURCE_BUNDLE_NAME, "registration.end.completed.summary", null));
		message.setSeverity(FacesMessage.SEVERITY_INFO);
		
		FacesContext.getCurrentInstance().addMessage(null, message);

		return END_REGISTRATION_VIEW_ID;
	}

	public void validateRePassword(FacesContext context, UIComponent validate, Object value) {
		logger.info("Launched RegistrationBean.validateRePassword");
		
		String rePassword = (String) value;

		String password = (String) getPasswordComponent().getLocalValue();

		if (StringUtils.isEmpty(rePassword) || !rePassword.equals(password)) {
			((UIInput) validate).setValid(false);
			FacesMessage message = new FacesMessage();
			message.setDetail(MessageResources.getMessage(MessageResources.ERROR_RESOURCE_BUNDLE_NAME, "error.form.validation.validateRePassword.detail", null));
			message.setSummary(MessageResources.getMessage(MessageResources.ERROR_RESOURCE_BUNDLE_NAME, "error.form.validation.validateRePassword.summary", null));
			message.setSeverity(FacesMessage.SEVERITY_ERROR);

			throw new ValidatorException(message);
		}
	}

	public void validateName(FacesContext context, UIComponent validate, Object value) throws AlvsanandException {
		logger.info("Launched RegistrationBean.validateName");
		
		String name = (String) value;

		if (!StringUtils.isEmpty(name)) {
			int count = 0;
			try {
				count = getUserService().getUserCount(name);
			} catch (ServiceException ex) {
				throw ex;
			}

			if (count > 0) {
				((UIInput) validate).setValid(false);

				FacesMessage message = new FacesMessage();
				message.setDetail(MessageResources.getMessage(MessageResources.ERROR_RESOURCE_BUNDLE_NAME, "error.form.validation.validateUserName.detail", null));
				message.setSummary(MessageResources.getMessage(MessageResources.ERROR_RESOURCE_BUNDLE_NAME, "error.form.validation.validateUserName.summary", null));
				message.setSeverity(FacesMessage.SEVERITY_ERROR);

				throw new ValidatorException(message);
			}
		}
	}

	public void activate() throws AlvsanandException {
		logger.info("Launched RegistrationBean.activate[" + registrationHash + "]");
		
		User user = getUserService().getUserByRegistrationHash(registrationHash);
		
		if(user==null){			
			FacesMessage message = new FacesMessage();
			message.setDetail(MessageResources.getMessage(MessageResources.ERROR_RESOURCE_BUNDLE_NAME, "error.activate.registrationHash.detail", null));
			message.setSummary(MessageResources.getMessage(MessageResources.ERROR_RESOURCE_BUNDLE_NAME, "error.activate.registrationHash.summary", null));
			message.setSeverity(FacesMessage.SEVERITY_ERROR);
			
			FacesContext.getCurrentInstance().addMessage(null, message);
			return;
		}
		if(user.getState()!=UserState.REGISTERED.getValue()){		
			FacesMessage message = new FacesMessage();
			message.setDetail(MessageResources.getMessage(MessageResources.ERROR_RESOURCE_BUNDLE_NAME, "error.activate.activated.detail", null));
			message.setSummary(MessageResources.getMessage(MessageResources.ERROR_RESOURCE_BUNDLE_NAME, "error.activate.activated.summary", null));
			message.setSeverity(FacesMessage.SEVERITY_ERROR);
			FacesContext.getCurrentInstance().addMessage(null, message);
			return;
		}
		
		user.setState(UserState.ACCEPTED.getValue());
		
		getUserService().saveUser(user);
		
		FacesMessage message = new FacesMessage();
		message.setDetail(MessageResources.getMessage(MessageResources.REGISTRATION_RESOURCE_BUNDLE_NAME, "registration.activation.completed.detail", null));
		message.setSummary(MessageResources.getMessage(MessageResources.REGISTRATION_RESOURCE_BUNDLE_NAME, "registration.activation.completed.summary", null));
		message.setSeverity(FacesMessage.SEVERITY_INFO);
		
		FacesContext.getCurrentInstance().addMessage(null, message);
	}

	public String resetPassword() throws AlvsanandException {
		logger.info("Launched RegistrationBean.resetPassword[" + loginName + "]");
		
		User user = getUserService().getUserByLoginName(loginName);
		
		if(user==null){			
			FacesMessage message = new FacesMessage();
			message.setDetail(MessageResources.getMessage(MessageResources.ERROR_RESOURCE_BUNDLE_NAME, "error.resetPassword.loginName.detail", null));
			message.setSummary(MessageResources.getMessage(MessageResources.ERROR_RESOURCE_BUNDLE_NAME, "error.resetPassword.loginName.summary", null));
			message.setSeverity(FacesMessage.SEVERITY_ERROR);
			
			FacesContext.getCurrentInstance().addMessage(null, message);
			return END_RESET_PASSWORD_VIEW_ID;
		}
		
		String plainPassword = es.alvsanand.webpage.common.StringUtils.generateRandomString();
		
		try {
			String passwordDigested = new String(Base64.encodeBase64(getCryptographyService().digest(plainPassword.getBytes())));

			user.setPassword(passwordDigested);
		} catch (NoSuchAlgorithmException noSuchAlgorithmException) {
		}
		
		getUserService().saveUser(user);

		getUserService().sendResetPasswordEmail(user, plainPassword);
		
		FacesMessage message = new FacesMessage();
		message.setDetail(MessageResources.getMessage(MessageResources.REGISTRATION_RESOURCE_BUNDLE_NAME, "registration.resetPassword.activation.completed.detail", null));
		message.setSummary(MessageResources.getMessage(MessageResources.REGISTRATION_RESOURCE_BUNDLE_NAME, "registration.resetPassword.activation.completed.summary", null));
		message.setSeverity(FacesMessage.SEVERITY_INFO);
		
		FacesContext.getCurrentInstance().addMessage(null, message);
		
		return END_RESET_PASSWORD_VIEW_ID;
	}

	public void loadPersonalData() throws AlvsanandException {
		User actualUser = (User)FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get(Globals.SES_USER);
		
		logger.info("Launched RegistrationBean.loadPersonalData[" + actualUser + "]");

		User user = getUserService().getUser(actualUser);
		
		name = user.getName();
		surname = user.getSurname();
		email = user.getEmail();
		password = null;
		rePassword = null;
		savedPersonalData = false;
	}

	public String savePersonalData() throws AlvsanandException {
		User actualUser = (User)FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get(Globals.SES_USER);
		
		logger.info("Launched RegistrationBean.savePersonalData[" + actualUser + "]");
		
		savedPersonalData = true;
		
		User user = getUserService().getUser(actualUser);
		
		user.setPassword(password);
		user.setName(name);
		user.setSurname(surname);
		user.setEmail(email);
		
		if(photoData!=null && StringUtils.isNotEmpty(photoMediaType)){
			Avatar avatar = new Avatar();
			avatar.setDate(new Date());
			avatar.setUser(actualUser);
			avatar.setMediaType(photoMediaType);
			avatar.setData(new Blob(photoData));
			
			user.setAvatar(avatar);
			
			photoData = null;
			photoMediaType = null;
		}

		if (user != null && user.getPassword() != null) {
			try {
				String passwordDigested = new String(Base64.encodeBase64(getCryptographyService().digest(user.getPassword().getBytes())));

				user.setPassword(passwordDigested);
			} catch (NoSuchAlgorithmException noSuchAlgorithmException) {
			}
		}

		getUserService().saveUser(user);
		
		FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put(Globals.SES_USER, user);
		
		FacesMessage message = new FacesMessage();
		message.setDetail(MessageResources.getMessage(MessageResources.REGISTRATION_RESOURCE_BUNDLE_NAME, "registration.modifyPersonalData.completed.detail", null));
		message.setSummary(MessageResources.getMessage(MessageResources.REGISTRATION_RESOURCE_BUNDLE_NAME, "registration.modifyPersonalData.completed.summary", null));
		message.setSeverity(FacesMessage.SEVERITY_INFO);
		
		FacesContext.getCurrentInstance().addMessage(null, message);
		
		password = null;
		rePassword = null;
		showFileUpload = false;
		
		return MODIFY_PERSONAL_DATA_VIEW_ID;
	}

	public void toggleFileUpload() throws AlvsanandException {
		logger.info("Launched RegistrationBean.toggleFileUpload");
		
		showFileUpload = !showFileUpload;
	}

	public void handleFileUpload(FileUploadEvent fileUploadEvent) {
		logger.info("Uploaded: {" + fileUploadEvent.getFile().getFileName() + "}");

		FacesContext context = FacesContext.getCurrentInstance();
		UIComponent fileUploadComponent = FacesUtils.findComponent(context.getViewRoot(), "fileUpload");
		
		InputStream inputStream = null;
		try {
			inputStream = fileUploadEvent.getFile().getInputstream();

			ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

			byte[] buf = new byte[1024];

			int length;

			while ((length = inputStream.read(buf)) > 0) {
				outputStream.write(buf, 0, length);
			}

			inputStream.close();

			photoData = outputStream.toByteArray();
			
			outputStream.close();
			
			FileNameMap fileNameMap = URLConnection.getFileNameMap();
			
			photoMediaType = fileNameMap.getContentTypeFor(fileUploadEvent.getFile().getFileName());
			
			showFileUpload = false;
		} catch (IOException e1) {
			FacesMessage message = new FacesMessage();
			message.setDetail(MessageResources.getMessage(MessageResources.ERROR_RESOURCE_BUNDLE_NAME, "error.modifyPersonalData.fileUpload.detail", null));
			message.setSummary(MessageResources.getMessage(MessageResources.ERROR_RESOURCE_BUNDLE_NAME, "error.modifyPersonalData.fileUpload.summary", null));
			message.setSeverity(FacesMessage.SEVERITY_ERROR);

			FacesContext.getCurrentInstance().addMessage(fileUploadComponent.getClientId(context), message);
			
			showFileUpload = false;
			return;
		}
	}
}