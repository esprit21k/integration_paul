package com.trumpia.account.schema;

import static org.junit.Assert.*;

import java.io.IOException;

import org.json.JSONException;
import org.junit.Before;
import org.junit.Test;

import com.trumpia.trumpia.schema.ContactSchema;

public class ContactSchemaTest {
	ContactSchema schema;
	@Before
	public void before() {
		schema = new ContactSchema("594c862a4bfe227e3a537be359d3381b", "paulkim");
	}
	
	@Test
	public void test() throws JSONException, IOException {
		System.out.println(schema.getContactSchema().toString());
		
	}

}
