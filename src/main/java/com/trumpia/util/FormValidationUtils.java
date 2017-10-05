package com.trumpia.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FormValidationUtils {
	
	public static boolean pwValidator(String password) {
		String pattern = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[`~!@#$%^&*()-+=])(?=\\S+$).{8,64}$";
		Pattern matchPattern = Pattern.compile(pattern);
		Matcher matcher = matchPattern.matcher(password);
		if(!matcher.matches())
			return false;
		return true;
	}
	
	public static boolean idValidator(String id) {
		String pattern = "(?i)(?=\\S+$)^[a-z0-9]+[a-z0-9-_.]{4,29}$";
		Pattern matchPattern = Pattern.compile(pattern);
		Matcher matcher = matchPattern.matcher(id);
		if(!matcher.matches())
			return false;
		return true;
	}
	
	public static boolean emailValidator(String email) {
		String pattern = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
		Pattern matchPattern = Pattern.compile(pattern);
		Matcher matcher = matchPattern.matcher(email);
		if(!matcher.matches())
			return false;
		return true;
	}
}
