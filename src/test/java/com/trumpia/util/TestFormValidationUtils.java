package com.trumpia.util;

import static org.junit.Assert.*;

import org.junit.Test;

public class TestFormValidationUtils {

	@Test
	public void testPwvalidator() {
		assertFalse(FormValidationUtils.pwValidator("trumd@12"));
		assertFalse(FormValidationUtils.pwValidator("alafweiugfksaehfgaksjdfkahs"));
		assertTrue(FormValidationUtils.pwValidator("alskdjfG123$"));
		assertTrue(FormValidationUtils.pwValidator("asdfasdG12#sdfdf"));
		assertTrue(FormValidationUtils.pwValidator("A@#123s123123"));
		assertFalse(FormValidationUtils.pwValidator("123123@T123"));
		assertFalse(FormValidationUtils.pwValidator("123123@a123123"));
		assertFalse(FormValidationUtils.pwValidator("123123123123arT"));
		assertFalse(FormValidationUtils.pwValidator("123123123  123arT"));
	}
	
	@Test
	public void testIdValidator() {
		assertFalse(FormValidationUtils.idValidator("trum"));
		assertTrue(FormValidationUtils.idValidator("truee"));
		assertFalse(FormValidationUtils.idValidator("tr  um"));
		assertTrue(FormValidationUtils.idValidator("trum12"));
		assertTrue(FormValidationUtils.idValidator("trum123_"));
		assertFalse(FormValidationUtils.idValidator("_trum23"));
	}
	
	@Test
	public void testEmailValidator() {
		assertFalse(FormValidationUtils.emailValidator("asdfgmail.com"));
		assertFalse(FormValidationUtils.emailValidator("asdfasdf"));
		assertFalse(FormValidationUtils.emailValidator("sdfdf"));
		assertFalse(FormValidationUtils.emailValidator("asdfasdfasdfasdfasdf"));
		assertFalse(FormValidationUtils.emailValidator("asdfasdfasdf@gmail"));
		assertTrue(FormValidationUtils.emailValidator("asfasdfas_-.dfasdf@asdf.asdf"));
	}
}
