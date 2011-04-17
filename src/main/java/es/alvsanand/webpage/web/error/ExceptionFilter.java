package es.alvsanand.webpage.web.error;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import es.alvsanand.webpage.AlvsanandException;
import es.alvsanand.webpage.common.Logger;

public class ExceptionFilter implements Filter {
	private final static Logger logger = new Logger(ExceptionFilter.class);

    /**
     * Default constructor. 
     */
    public ExceptionFilter() {
    }

	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {
		logger.info("Destroying ExceptionFilter");
	}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		try{
			chain.doFilter(request, response);
		}
		catch(ServletException servletException){
			Throwable facesException = searchLastFacesExceptionOrELException(servletException);
			
			if(facesException!=null){
				if(facesException.getCause() instanceof ServletException){
					throw (ServletException)facesException.getCause();
				}
				else{
					throw new AlvsanandException("Unknown exception type", facesException);
				}
			}
			else{
				throw servletException;
			}			
		}
	}
	
	private Throwable searchLastFacesExceptionOrELException(Throwable throwable){
		if(throwable==null){
			return null;
		}
		
		if(throwable instanceof javax.faces.FacesException || throwable instanceof javax.el.ELException){
			if(throwable.getCause()!=null){
				Throwable nextFacesException = searchLastFacesExceptionOrELException(throwable.getCause());
				
				if(nextFacesException!=null){
					return nextFacesException;
				}
				else{
					return throwable;
				}
			}
			else{
				return throwable;
			}
		}
		else{
			return searchLastFacesExceptionOrELException(throwable.getCause());
		}
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		logger.info("ExceptionFilter initiated successfully");
	}

}
