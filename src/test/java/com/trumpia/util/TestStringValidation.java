package com.trumpia.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.google.i18n.phonenumbers.NumberParseException;
import com.google.i18n.phonenumbers.Phonenumber.PhoneNumber;

public class TestStringValidation {

	
	@Test
	public void isPhoneNumberTest() {
		assertFalse(PhoneNumberValidationUtils.isPhoneNumber("asdfasd"));
		assertFalse(PhoneNumberValidationUtils.isPhoneNumber("+"));
		assertFalse(PhoneNumberValidationUtils.isPhoneNumber("555-0110"));
		assertTrue(PhoneNumberValidationUtils.isPhoneNumber("+7142545256"));
		assertTrue(PhoneNumberValidationUtils.isPhoneNumber("+123456789012"));
	}
	
	@Test
	public void parsingPhoneNumberTest() throws NumberParseException {

		PhoneNumber testNumber;

		testNumber = PhoneNumberValidationUtils.parsingPhoneNumber("+821040808517");
		assertEquals(testNumber.getCountryCode(), 82);
		assertEquals(testNumber.getNationalNumber(), 1040808517L);
		
		testNumber = PhoneNumberValidationUtils.parsingPhoneNumber("7142545256");
		assertEquals(testNumber.getCountryCode(), 1);
		assertEquals(testNumber.getNationalNumber(), 7142545256L);

		testNumber = PhoneNumberValidationUtils.parsingPhoneNumber("17142545256");
		assertEquals(testNumber.getCountryCode(), 1);
		assertEquals(testNumber.getNationalNumber(), 7142545256L);
		
		testNumber = PhoneNumberValidationUtils.parsingPhoneNumber("+17142545256");
		assertEquals(testNumber.getCountryCode(), 1);
		assertEquals(testNumber.getNationalNumber(), 7142545256L);

		testNumber = PhoneNumberValidationUtils.parsingPhoneNumber("+1-714-254-5256");
		assertEquals(testNumber.getCountryCode(), 1);
		assertEquals(testNumber.getNationalNumber(), 7142545256L);

		testNumber = PhoneNumberValidationUtils.parsingPhoneNumber("+1(714)-254-5256");
		assertEquals(testNumber.getCountryCode(), 1);
		assertEquals(testNumber.getNationalNumber(), 7142545256L);

		testNumber = PhoneNumberValidationUtils.parsingPhoneNumber("+45(714)-254-5256");
		assertEquals(testNumber.getCountryCode(), 45);
		assertEquals(testNumber.getNationalNumber(), 7142545256L);

		testNumber = PhoneNumberValidationUtils.parsingPhoneNumber("+45-714-254-5256");
		assertEquals(testNumber.getCountryCode(), 45);
		assertEquals(testNumber.getNationalNumber(), 7142545256L);

	}

	@Test(expected=NumberParseException.class)
	public void expectedNumberParseExceptionTest() throws NumberParseException{
		PhoneNumber testNumber = PhoneNumberValidationUtils.parsingPhoneNumber("+");
		testNumber = PhoneNumberValidationUtils.parsingPhoneNumber("aaa");
	}

}
