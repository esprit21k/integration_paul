package com.trumpia.account.schema;

import static org.junit.Assert.*;

import java.io.IOException;

import org.json.JSONException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.trumpia.Main;
import com.trumpia.trumpia.data.TrumpiaAccountRepository;
import com.trumpia.trumpia.model.TrumpiaAccountEntity;
import com.trumpia.trumpia.schema.ContactSchema;

public class ContactSchemaTest {

	ContactSchema schema;
	@Before
	public void before() {
		schema = new ContactSchema("594c862a4bfe227e3a537be359d3381b", "paulkim", "http://api.trumpia.com");
	}
	
	@Test
	public void test() throws Exception {
		assertNotNull(schema.getContactSchema().get("list_name"));
		assertNotNull(schema.getContactSchema().get("customdata"));
		System.out.println(schema.getContactSchema().toString());
	}
}
