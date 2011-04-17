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
package es.alvsanand.webpage.security.exception;

public class UsernameDisabledException extends AuthenticationException {
	/**
	 * 
	 */
	private static final long serialVersionUID = -1940269904175565113L;

	public UsernameDisabledException(String msg, Object extraInformation) {
		super(msg, extraInformation);
	}

	public UsernameDisabledException(String msg, Throwable t) {
		super(msg, t);
	}

	public UsernameDisabledException(String msg) {
		super(msg);
	}

}
