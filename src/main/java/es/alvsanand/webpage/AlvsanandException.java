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
package es.alvsanand.webpage;

import javax.servlet.ServletException;

/**
 * Generic Alvsanand Escption
 *
 * @author alvaro.santos
 * @date 23/11/2009
 *
 */
public class AlvsanandException extends ServletException {
	/**
	 * 
	 */
	private static final long serialVersionUID = -5894296081636386480L;

	/**
	 * Default constructor
	 */
	public AlvsanandException() {
	}

	/**
	 * 
	 * @param message The message of the exception
	 */
	public AlvsanandException(String message) {
		super(message);
	}

	/**
	 * @param cause The cause of the exception
	 */
	public AlvsanandException(Throwable cause) {
		super(cause);
	}

	/**
	 * @param message The message of the exception
	 * @param cause The cause of the exception
	 */
	public AlvsanandException(String message, Throwable cause) {
		super(message, cause);
	}

}
