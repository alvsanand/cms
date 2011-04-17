package es.alvsanand.webpage.common.taglibs.facelets;

import es.alvsanand.webpage.common.MessageResources;


public class StringELLibrary {
	public StringELLibrary() {
	}

	public static String concat(final String stringA, final String stringB) {
		return stringA + stringB;
	}

	public static String replace(final String source, final String pattern, String replaceText) {
		if(source!=null){
			return source.replaceAll(pattern, replaceText);
		}
		else{
			return null;
		}
	}

	public static String getMessage(final String reourceBundleName, final String key) {
		return MessageResources.getMessage(reourceBundleName, key, null);
	}
}