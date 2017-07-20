package com.trumpia.util;

public class StringUtils {

	public static String nullTextIfNull(Object o) {
		return ((o == null)? "NULL" : o.toString());
	}

	public static String blankTextIfNull(Object o) {
		return ((o == null)? "" : o.toString());
	}
	
	public static Boolean textIsBlankOrNull(Object o) {
		return ((o == null) || (o.equals("")));
	}
}
