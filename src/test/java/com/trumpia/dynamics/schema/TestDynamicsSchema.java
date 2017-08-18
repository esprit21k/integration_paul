package com.trumpia.dynamics.schema;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;

import com.trumpia.dynamics.APIcaller.DynamicsAPIcaller;
import com.trumpia.dynamics.APIcaller.RefreshAccessToken;

public class TestDynamicsSchema {
	DynamicsSchema test;
	@Before
	public void setUp() {
		try {
			DynamicsAPIcaller.ACCESS_TOKEN = RefreshAccessToken.updateAccessToken();
			test = new DynamicsSchema(DynamicsAPIcaller.ACCESS_TOKEN);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	@Test
	public void testIfNoXmlFileExist() {
		File file = new File(test.PATH);
		if(file.exists())
			file.delete();
		
		test.getDynamicsSchema();
		HashMap<String, String> properties = test.getProperties();
		File afterFile = new File(test.PATH);
		
		assertEquals(properties.size(),195);
		assertTrue(afterFile.exists());
		
	}

	@Test
	public void testPropertiesSize() throws Exception {
		test.getDynamicsSchema();
		HashMap<String, String> properties = test.getProperties();
		assertEquals(properties.size(),195); 
		assertTrue(properties.get("mobilephone").equals("Edm.String"));
		assertTrue(properties.get("spousesname").equals("Edm.String"));
		assertTrue(properties.get("merged").equals("Edm.Boolean"));
		System.out.println(test.propertiesToJSON().toString());
		test.storeDynamicsSchemaAsXML();
		
	}

}
