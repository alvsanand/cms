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
package es.alvsanand.webpage.web.validators;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

import es.alvsanand.webpage.common.StringUtils;

public class RegExpValidator implements Validator {
	private String regularExpression;
	private String messageError;
	
	public RegExpValidator() {
	}

	public String getRegularExpression() {
		return regularExpression;
	}

	public void setRegularExpression(String regularExpression) {
		this.regularExpression = regularExpression;
	}

	public String getMessageError() {
		return messageError;
	}

	public void setMessageError(String messageError) {
		this.messageError = messageError;
	}

	public void validate(FacesContext context, UIComponent component, Object value) {		
		initProps(component);

		String expression = (String) value;

		if (!StringUtils.validateRegExp(expression, expression)) {
			FacesMessage message = new FacesMessage();
			message.setDetail(messageError);
			message.setSummary(messageError);
			message.setSeverity(FacesMessage.SEVERITY_ERROR);
			throw new ValidatorException(message);
		}

	}
	
	private void initProps(UIComponent component) {
		regularExpression = (String) component.getAttributes().get("regularExpression");
		if(regularExpression==null || regularExpression.length()==0){
			new IllegalArgumentException("The regular expresion cannot be null or empty");
		}

		messageError = (String) component.getAttributes().get("messageError");		
		if(messageError==null || messageError.length()==0){
			new IllegalArgumentException("The message error cannot be null or empty");
		}
	}
}
