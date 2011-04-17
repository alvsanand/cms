package es.alvsanand.webpage.common;

import java.util.HashMap;
import java.util.Map;

import com.google.appengine.api.datastore.Text;

public class XMLUtils {
	private final static Map<String, String> AUTO_CLOSED_HTML_TAGS;

	static {
		AUTO_CLOSED_HTML_TAGS = new HashMap<String, String>();

		AUTO_CLOSED_HTML_TAGS.put("<br([^>]*)>", "<br/>");
		AUTO_CLOSED_HTML_TAGS.put("<hr([^>]*)>", "<hr/>");
	}
	
	private final static Map<String, String> HTML_XML_ENTITIES;

	static {
		HTML_XML_ENTITIES = new HashMap<String, String>();

		HTML_XML_ENTITIES.put("& ", "&#38; ");
		HTML_XML_ENTITIES.put("&quot;", "&#34;");
		HTML_XML_ENTITIES.put("&amp;", "&#38;");
		HTML_XML_ENTITIES.put("&lt;", "&#60;");
		HTML_XML_ENTITIES.put("&gt;", "&#62;");
		HTML_XML_ENTITIES.put("&nbsp;", "&#160;");
		HTML_XML_ENTITIES.put("&iexcl;", "&#161;");
		HTML_XML_ENTITIES.put("&cent;", "&#162;");
		HTML_XML_ENTITIES.put("&pound;", "&#163;");
		HTML_XML_ENTITIES.put("&curren;", "&#164;");
		HTML_XML_ENTITIES.put("&yen;", "&#165;");
		HTML_XML_ENTITIES.put("&brvbar;", "&#166;");
		HTML_XML_ENTITIES.put("&sect;", "&#167;");
		HTML_XML_ENTITIES.put("&uml;", "&#168;");
		HTML_XML_ENTITIES.put("&copy;", "&#169;");
		HTML_XML_ENTITIES.put("&ordf;", "&#170;");
		HTML_XML_ENTITIES.put("&laquo;", "&#171;");
		HTML_XML_ENTITIES.put("&not;", "&#172;");
		HTML_XML_ENTITIES.put("&shy;", "&#173;");
		HTML_XML_ENTITIES.put("&reg;", "&#174;");
		HTML_XML_ENTITIES.put("&macr;", "&#175;");
		HTML_XML_ENTITIES.put("&deg;", "&#176;");
		HTML_XML_ENTITIES.put("&plusmn;", "&#177;");
		HTML_XML_ENTITIES.put("&sup2;", "&#178;");
		HTML_XML_ENTITIES.put("&sup3;", "&#179;");
		HTML_XML_ENTITIES.put("&acute;", "&#180;");
		HTML_XML_ENTITIES.put("&micro;", "&#181;");
		HTML_XML_ENTITIES.put("&para;", "&#182;");
		HTML_XML_ENTITIES.put("&middot;", "&#183;");
		HTML_XML_ENTITIES.put("&cedil;", "&#184;");
		HTML_XML_ENTITIES.put("&sup1;", "&#185;");
		HTML_XML_ENTITIES.put("&ordm;", "&#186;");
		HTML_XML_ENTITIES.put("&raquo;", "&#187;");
		HTML_XML_ENTITIES.put("&frac14;", "&#188;");
		HTML_XML_ENTITIES.put("&frac12;", "&#189;");
		HTML_XML_ENTITIES.put("&frac34;", "&#190;");
		HTML_XML_ENTITIES.put("&iquest;", "&#191;");
		HTML_XML_ENTITIES.put("&Agrave;", "&#192;");
		HTML_XML_ENTITIES.put("&Aacute;", "&#193;");
		HTML_XML_ENTITIES.put("&Acirc;", "&#194;");
		HTML_XML_ENTITIES.put("&Atilde;", "&#195;");
		HTML_XML_ENTITIES.put("&Auml;", "&#196;");
		HTML_XML_ENTITIES.put("&Aring;", "&#197;");
		HTML_XML_ENTITIES.put("&AElig;", "&#198;");
		HTML_XML_ENTITIES.put("&Ccedil;", "&#199;");
		HTML_XML_ENTITIES.put("&Egrave;", "&#200;");
		HTML_XML_ENTITIES.put("&Eacute;", "&#201;");
		HTML_XML_ENTITIES.put("&Ecirc;", "&#202;");
		HTML_XML_ENTITIES.put("&Euml;", "&#203;");
		HTML_XML_ENTITIES.put("&Igrave;", "&#204;");
		HTML_XML_ENTITIES.put("&Iacute;", "&#205;");
		HTML_XML_ENTITIES.put("&Icirc;", "&#206;");
		HTML_XML_ENTITIES.put("&Iuml;", "&#207;");
		HTML_XML_ENTITIES.put("&ETH;", "&#208;");
		HTML_XML_ENTITIES.put("&Ntilde;", "&#209;");
		HTML_XML_ENTITIES.put("&Ograve;", "&#210;");
		HTML_XML_ENTITIES.put("&Oacute;", "&#211;");
		HTML_XML_ENTITIES.put("&Ocirc;", "&#212;");
		HTML_XML_ENTITIES.put("&Otilde;", "&#213;");
		HTML_XML_ENTITIES.put("&Ouml;", "&#214;");
		HTML_XML_ENTITIES.put("&times;", "&#215;");
		HTML_XML_ENTITIES.put("&Oslash;", "&#216;");
		HTML_XML_ENTITIES.put("&Ugrave;", "&#217;");
		HTML_XML_ENTITIES.put("&Uacute;", "&#218;");
		HTML_XML_ENTITIES.put("&Ucirc;", "&#219;");
		HTML_XML_ENTITIES.put("&Uuml;", "&#220;");
		HTML_XML_ENTITIES.put("&Yacute;", "&#221;");
		HTML_XML_ENTITIES.put("&THORN;", "&#222;");
		HTML_XML_ENTITIES.put("&szlig;", "&#223;");
		HTML_XML_ENTITIES.put("&agrave;", "&#224;");
		HTML_XML_ENTITIES.put("&aacute;", "&#225;");
		HTML_XML_ENTITIES.put("&acirc;", "&#226;");
		HTML_XML_ENTITIES.put("&atilde;", "&#227;");
		HTML_XML_ENTITIES.put("&auml;", "&#228;");
		HTML_XML_ENTITIES.put("&aring;", "&#229;");
		HTML_XML_ENTITIES.put("&aelig;", "&#230;");
		HTML_XML_ENTITIES.put("&ccedil;", "&#231;");
		HTML_XML_ENTITIES.put("&egrave;", "&#232;");
		HTML_XML_ENTITIES.put("&eacute;", "&#233;");
		HTML_XML_ENTITIES.put("&ecirc;", "&#234;");
		HTML_XML_ENTITIES.put("&euml;", "&#235;");
		HTML_XML_ENTITIES.put("&igrave;", "&#236;");
		HTML_XML_ENTITIES.put("&iacute;", "&#237;");
		HTML_XML_ENTITIES.put("&icirc;", "&#238;");
		HTML_XML_ENTITIES.put("&iuml;", "&#239;");
		HTML_XML_ENTITIES.put("&eth;", "&#240;");
		HTML_XML_ENTITIES.put("&ntilde;", "&#241;");
		HTML_XML_ENTITIES.put("&ograve;", "&#242;");
		HTML_XML_ENTITIES.put("&oacute;", "&#243;");
		HTML_XML_ENTITIES.put("&ocirc;", "&#244;");
		HTML_XML_ENTITIES.put("&otilde;", "&#245;");
		HTML_XML_ENTITIES.put("&ouml;", "&#246;");
		HTML_XML_ENTITIES.put("&divide;", "&#247;");
		HTML_XML_ENTITIES.put("&oslash;", "&#248;");
		HTML_XML_ENTITIES.put("&ugrave;", "&#249;");
		HTML_XML_ENTITIES.put("&uacute;", "&#250;");
		HTML_XML_ENTITIES.put("&ucirc;", "&#251;");
		HTML_XML_ENTITIES.put("&uuml;", "&#252;");
		HTML_XML_ENTITIES.put("&yacute;", "&#253;");
		HTML_XML_ENTITIES.put("&thorn;", "&#254;");
		HTML_XML_ENTITIES.put("&yuml;", "&#255;");
		HTML_XML_ENTITIES.put("&euro;", "&#8364;");
	}
	
	public static String getFullArticleData(Text articleData){		
		if(articleData==null){
			return null;
		}
		
		String dataValue = articleData.getValue();
		
		if(dataValue==null){
			return null;
		}
		
		return repareText(dataValue.replaceFirst(Globals.ARTICLE_DATA_DELIMITER_REGEXP, ""));
	}
	
	public static String getResumeArticleData(Text articleData){
		if(articleData==null){
			return null;
		}
		
		String dataValue = articleData.getValue();
		
		if(dataValue==null){
			return null;
		}
		
		int lasResumeLastCharacterPosition = dataValue.indexOf(Globals.ARTICLE_DATA_DELIMITER);
		
		if(lasResumeLastCharacterPosition>-1){
			return repareText(dataValue.substring(0, lasResumeLastCharacterPosition));
		}
		else{
			return repareText(dataValue);
		}
	}
	

	public static String repareText(String text){
		return convertHTMLToXMLEntities(repareAutoClosedHTMLTags(text));
	}

	private static String convertHTMLToXMLEntities(String text){
		StringBuilder stringBuilderText = new StringBuilder(text);
		
		for(String htmlEntity: HTML_XML_ENTITIES.keySet()){
			replaceString(stringBuilderText, htmlEntity.toLowerCase(), HTML_XML_ENTITIES.get(htmlEntity));
			replaceString(stringBuilderText, htmlEntity.toUpperCase(), HTML_XML_ENTITIES.get(htmlEntity));
		}		
		
		return stringBuilderText.toString();
	}

	private static String repareAutoClosedHTMLTags(final String text){
		String textAux = new String(text);
		for(String tag: AUTO_CLOSED_HTML_TAGS.keySet()){
			textAux = textAux.replaceAll(tag, AUTO_CLOSED_HTML_TAGS.get(tag));
		}
		
		return textAux;
	}
	
	private static StringBuilder replaceString(StringBuilder text, String search, String replace) {
		int fromIndex = 0;

		int start = text.indexOf(search, fromIndex);
		if (start == -1) {
			return text;
		}

		if (replace.length() > 0) {
			int end = 0;

			int endAdjust = (search.length() - replace.length());
			do {
				end = (start + replace.length()) + endAdjust;
				text.replace(start, end, replace);
				fromIndex = end;
			} while ((start = text.indexOf(search, fromIndex)) != -1);
		} else {
			do {
				text.delete(start, search.length());
				fromIndex = start + replace.length();
			} while ((start = text.indexOf(search, fromIndex)) != -1);
		}

		return text;
	}
}
