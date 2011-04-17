package es.alvsanand.webpage.web.filters;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import es.alvsanand.webpage.common.Logger;

public class CharacterEncodingFilter implements Filter {
	private final static Logger logger = new Logger(CharacterEncodingFilter.class);
	
	private final static String CHARACTER_ENCODING = "UTF-8";

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		logger.info("CharacterEncodingFilter initiated successfully");
	}

	@Override
	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
		servletRequest.setCharacterEncoding(CHARACTER_ENCODING);
		servletResponse.setCharacterEncoding(CHARACTER_ENCODING);
		
		filterChain.doFilter(servletRequest, servletResponse);
	}

	@Override
	public void destroy() {
		logger.info("Destroying CharacterEncodingFilter");
	}
}
