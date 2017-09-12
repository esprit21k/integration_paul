package com.trumpia.util;

import static org.junit.Assert.*;

import org.junit.Test;

public class TestXMLUtils {

	@Test
	public void test() throws Exception {
		String xml = "<?xml version=\"1.0\" encoding=\"UTF-8\" ?>" + 
				"<TRUMPIA>" + 
				"	<PUSH_ID>201493242</PUSH_ID>" + 
				"	<INBOUND_ID>61320445</INBOUND_ID>" + 
				"	<SUBSCRIPTION_UID>200609510</SUBSCRIPTION_UID>" + 
				"	<PHONENUMBER>7142348577</PHONENUMBER>" + 
				"	<KEYWORD>paulkim2</KEYWORD>" + 
				"	<DATA_CAPTURE />" + 
				"	<CONTENTS />" + 
				"	<ATTACHMENT />" + 
				"	<DATASET_ID>0</DATASET_ID>" + 
				"	<DATASET_NAME>Master Account</DATASET_NAME>" + 
				"</TRUMPIA>";
		String expression = "/TRUMPIA/SUBSCRIPTION_UID";
		assertEquals(XMLUtils.getValueFromExpression(xml, expression), "200609510");
	}

}
