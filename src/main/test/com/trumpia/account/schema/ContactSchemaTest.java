package com.trumpia.account.schema;

import java.io.IOException;

import org.json.JSONException;
import org.junit.Test;

public class ContactSchemaTest {
	
	@Test
	public void test() throws JSONException, IOException {
		String aPIKey = "594c862a4bfe227e3a537be359d3381b";
		String userName = "paulkim";
		ContactSchema schema = new ContactSchema(aPIKey, userName);
		System.out.println(schema.getContactSchema());
		
	}

}
