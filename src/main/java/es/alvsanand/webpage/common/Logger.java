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
package es.alvsanand.webpage.common;

import java.util.logging.Level;

import javax.faces.context.FacesContext;

import es.alvsanand.webpage.model.User;

/**
 *
 *
 * @author alvaro.santos
 * @date 04/12/2009
 *
 */
public class Logger{
	private java.util.logging.Logger logger;
	
	public Logger(Class<?> clazz){
		logger = java.util.logging.Logger.getLogger(clazz.getName());
	}
	
	public void error(String message, java.lang.Throwable e){
		StackTraceElement stackTraceElement = getLastStackTraceElement();	
		
		StringBuffer sb = new StringBuffer(getMessageLogHead() + message);
		
		sb.append("\n");
		sb.append("Caused by: ");
		
		java.io.ByteArrayOutputStream out = new java.io.ByteArrayOutputStream();
		e.printStackTrace(new java.io.PrintStream(out));
		sb.append(out);
		
		logger.logp(Level.SEVERE, stackTraceElement.getClassName(), stackTraceElement.getMethodName(), sb.toString(), e);
	}
	
	public void error(String message){
		StackTraceElement stackTraceElement = getLastStackTraceElement();	
		
		logger.logp(Level.SEVERE, stackTraceElement.getClassName(), stackTraceElement.getMethodName(), getMessageLogHead() + message);
	}
	
	public void warn(String message, java.lang.Throwable e){
		StackTraceElement stackTraceElement = getLastStackTraceElement();	
		
		StringBuffer sb = new StringBuffer(getMessageLogHead() + message);
		
		sb.append("\n");
		sb.append("Caused by: ");
		
		java.io.ByteArrayOutputStream out = new java.io.ByteArrayOutputStream();
		e.printStackTrace(new java.io.PrintStream(out));
		sb.append(out);
		
		logger.logp(Level.WARNING, stackTraceElement.getClassName(), stackTraceElement.getMethodName(), sb.toString(), e);
	}
	
	public void warn(String message){
		StackTraceElement stackTraceElement = getLastStackTraceElement();	
		
		logger.logp(Level.WARNING, stackTraceElement.getClassName(), stackTraceElement.getMethodName(), getMessageLogHead() + message);
	}
	
	public void info(String message){
		StackTraceElement stackTraceElement = getLastStackTraceElement();	
		
		logger.logp(Level.INFO, stackTraceElement.getClassName(), stackTraceElement.getMethodName(), getMessageLogHead() + message);
	}
	
	public void debug(String message){
		StackTraceElement stackTraceElement = getLastStackTraceElement();	
		
		logger.logp(Level.FINE, stackTraceElement.getClassName(), stackTraceElement.getMethodName(), getMessageLogHead() + message);
	}
	
	private StackTraceElement getLastStackTraceElement(){
		StackTraceElement[] stackTraceElements = Thread.currentThread().getStackTrace();
		
		return stackTraceElements[3];
	}
	
	private User getUser(){
		if(FacesContext.getCurrentInstance()!=null && FacesContext.getCurrentInstance().getExternalContext()!=null
				&& FacesContext.getCurrentInstance().getExternalContext().getSessionMap()!=null){		
			return (User)FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get(Globals.SES_USER);
		}
		else{
			return null;
		}
	}
	
	private String getMessageLogHead(){
		User user = getUser();
		if(user!=null){
			return "[" + user.getLoginName() + "] ";
		}
		else{
			return "";
		}
	}
}
