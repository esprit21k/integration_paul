package com.trumpia.util;

import com.google.i18n.phonenumbers.NumberParseException;
import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.google.i18n.phonenumbers.Phonenumber.PhoneNumber;

public class PhoneNumberValidationUtils {

public static boolean isPhoneNumber(String phoneNo) {
		phoneNo = removeBracketAndHyphen(phoneNo);
		String pattern = "^\\+?\\d{10,14}$";
		return phoneNo.matches(pattern);
	}

	public static PhoneNumber parsingPhoneNumber(String phoneNo) throws NumberParseException {

		if(!isPhoneNumber(phoneNo))
			throw new NumberParseException(NumberParseException.ErrorType.NOT_A_NUMBER, "Wrong Format : " + phoneNo);
		
		phoneNo = removeBracketAndHyphen(phoneNo);
		PhoneNumber parsedForm = getParsedForm(phoneNo);

		return parsedForm;
	}

	private static PhoneNumber getParsedForm(String phoneNo) throws NumberParseException {
		PhoneNumber parsed;
		PhoneNumberUtil phoneUtil = PhoneNumberUtil.getInstance();
		
		if(phoneNo.length() == 10)
			parsed = phoneUtil.parse(phoneNo,"US");		
		else if(phoneNo.length() == 11 && phoneNo.startsWith("1") )
			parsed = phoneUtil.parse(phoneNo,"US");
		else if(phoneNo.length() == 12 && phoneNo.startsWith("+1") )
			parsed = phoneUtil.parse(phoneNo,"US");
		else
			parsed = phoneUtil.parse(phoneNo, "ZZ");

		return parsed;
	}

	private static String removeBracketAndHyphen(String phoneNo) {
		phoneNo = phoneNo.replaceAll("[(|)|-]", "");
		return phoneNo;
	}
}