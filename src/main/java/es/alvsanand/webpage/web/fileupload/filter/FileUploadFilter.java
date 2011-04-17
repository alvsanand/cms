/*
 * Copyright 2009 Prime Technology.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package es.alvsanand.webpage.web.fileupload.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.primefaces.webapp.MultipartRequest;

import es.alvsanand.webpage.common.Logger;
import es.alvsanand.webpage.web.fileupload.common.MemoryFileItemFactory;

public class FileUploadFilter implements Filter {
	
	private final static Logger logger = new Logger(FileUploadFilter.class);

	private final static String THRESHOLD_SIZE_PARAM = "thresholdSize";
	
	private String thresholdSize;
	
	public void init(FilterConfig filterConfig) throws ServletException {
		thresholdSize = filterConfig.getInitParameter(THRESHOLD_SIZE_PARAM);
		logger.info("FileUploadFilter initiated successfully");
	}
	
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException, ServletException {
		HttpServletRequest httpServletRequest = (HttpServletRequest) request;
		boolean isMultipart = ServletFileUpload.isMultipartContent(httpServletRequest);
		
		if(isMultipart) {
			logger.debug("Parsing file upload request");
			
			MemoryFileItemFactory memoryFileItemFactory = new MemoryFileItemFactory();
			if(thresholdSize != null) {
				memoryFileItemFactory.setSizeThreshold(Integer.valueOf(thresholdSize));
			}
			
			ServletFileUpload servletFileUpload = new ServletFileUpload(memoryFileItemFactory);
			MultipartRequest multipartRequest = new MultipartRequest(httpServletRequest, servletFileUpload);
			
			logger.debug("File upload request parsed succesfully, continuing with filter chain with a wrapped multipart request");
			
			filterChain.doFilter(multipartRequest, response);
		} else {
			filterChain.doFilter(request, response);
		}
	}

	public void destroy() {
		logger.info("Destroying FileUploadFilter");
	}

}
