package es.alvsanand.webpage.common;

import java.text.MessageFormat;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

import javax.faces.context.FacesContext;

import es.alvsanand.webpage.common.resources.XMLResourceBundle;

public class MessageResources {
	private transient static final Logger logger = new Logger(MessageResources.class);
	
	public final static String FACES_BUNDLE_NAME_FORMAT = "resources.{0}";
	public final static String FACES_BUNDLE_NAME_REGEXP = "bundle_.*";
	
	public final static String ERROR_RESOURCE_BUNDLE_NAME = "resources.error";
	public final static String ADMIN_RESOURCE_BUNDLE_NAME = "resources.admin";
	public final static String GENERAL_RESOURCE_BUNDLE_NAME = "resources.general";
	public final static String HOME_RESOURCE_BUNDLE_NAME = "resources.home";
	public final static String REGISTRATION_RESOURCE_BUNDLE_NAME = "resources.registration";
	public final static String MENU_RESOURCE_BUNDLE_NAME = "resources.menu";
	public final static String MOBILE_RESOURCE_BUNDLE_NAME = "resources.mobile";

	public static ResourceBundle getBundle(String _resourceBundleName) {
		FacesContext context = FacesContext.getCurrentInstance();

		String resourceBundleName = (_resourceBundleName!=null && _resourceBundleName.matches(FACES_BUNDLE_NAME_REGEXP))?(MessageFormat.format(FACES_BUNDLE_NAME_FORMAT, new String[]{_resourceBundleName.split("_")[1]})):_resourceBundleName;
		
		ResourceBundle bundle = null;
		Locale locale = null;
		if (context != null) {
			bundle = context.getApplication().getResourceBundle(context, resourceBundleName);
			locale =  (Locale)context.getExternalContext().getSessionMap().get(Globals.SES_LOCALE);
		}
		
		if(locale == null){
			locale = Locale.ROOT;
		}
		
		if(bundle==null) {
			ClassLoader loader = Thread.currentThread().getContextClassLoader();
			try {
				bundle = ResourceBundle.getBundle(resourceBundleName, locale, loader);
			} catch (java.util.MissingResourceException missingResourceException) {
				bundle = XMLResourceBundle.getBundle(resourceBundleName, locale, loader);
			}
		}

		return bundle;
	}

	public static String getMessage(String resourceBundleName, String key, Object params[]) {
		FacesContext context = FacesContext.getCurrentInstance();

		Locale locale = (context != null) ? context.getViewRoot().getLocale() : Locale.ROOT;

		String text = null;

		try {
			text = getBundle(resourceBundleName).getString(key);
		} catch (MissingResourceException missingResourceException) {
			text = "?? key " + key + " not found ??";
		} catch (Exception exception) {
			logger.error("Error getting message[" + key + "] of bundle[" + resourceBundleName + "]: " + exception);
			text = "?? key " + key + " not found ??";
		}

		if (params != null) {
			MessageFormat mf = new MessageFormat(text, locale);
			text = mf.format(params, new StringBuffer(), null).toString();
		}

		return text;
	}
}
