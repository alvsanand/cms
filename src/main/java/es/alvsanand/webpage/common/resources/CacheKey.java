package es.alvsanand.webpage.common.resources;

import java.io.Serializable;
import java.util.Locale;

public class CacheKey implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 6214198744235488820L;
	
	private String baseBaseName;
	
	private Locale locale;
	
	public CacheKey(String baseBaseName, Locale locale) {
		super();
		this.baseBaseName = baseBaseName;
		this.locale = locale;
	}
	public String getBaseName() {
		return baseBaseName;
	}
	public void setBaseName(String baseBaseName) {
		this.baseBaseName = baseBaseName;
	}
	public Locale getLocale() {
		return locale;
	}
	public void setLocale(Locale locale) {
		this.locale = locale;
	}
	@Override
	public String toString() {
		return "CacheKey [baseBaseName=" + baseBaseName + ", locale=" + locale + "]";
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((locale == null) ? 0 : locale.hashCode());
		result = prime * result + ((baseBaseName == null) ? 0 : baseBaseName.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		CacheKey other = (CacheKey) obj;
		if (locale == null) {
			if (other.locale != null) {
				return false;
			}
		} else if (!locale.equals(other.locale)) {
			return false;
		}
		if (baseBaseName == null) {
			if (other.baseBaseName != null) {
				return false;
			}
		} else if (!baseBaseName.equals(other.baseBaseName)) {
			return false;
		}
		return true;
	}
}
