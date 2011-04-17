package es.alvsanand.webpage.common;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtils {
	private final static char[] INVALID_NAME_CHARS;

	static {
		INVALID_NAME_CHARS = "[][!\"#$%&'()*+,./:;<=>?@\\^_`{|}~-] ".toCharArray();
	}
	private final static char REPLACER_CHAR = '_';

	public static boolean validateRegExp(String expression, String regularExpression) {
		Pattern mask = Pattern.compile(regularExpression);

		Matcher matcher = mask.matcher(expression);

		return matcher.matches();
	}

	public static String getValidName(final String name) {
		String correctName = name;
		for (char badChar : INVALID_NAME_CHARS) {
			correctName = correctName.replace(badChar, REPLACER_CHAR);
		}

		return correctName;
	}

	public static String generateRandomString() {
		SecureRandom random = new SecureRandom();

		return new BigInteger(130, random).toString(32);
	}
}
