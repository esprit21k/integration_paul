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
			RefreshAccessToken refreshAccessToken = new RefreshAccessToken("AQABAAAAAABnfiG-mA6NTae7CdWW7Qfd2cr918F76yI_lb2yH4UrcAZs61Uw9kFJrmEeBuqeCXnvrMC7tvF4BMMq8rMaT3sPeMkMUIdoJo8YRrOs8Hzk2fxn9OZfnfthUTX86hcspEkO4l2qwUTzUNh7nsNeyq-KqTrpnCysO1jl0MWx63ZUb5ji3U9CIIMsJxZwRTdocyRPUyte0A-K3hoNsCd7xv0mDoxOvdjMatn9ZszQYdRxzI4l86NEaroIKHqjrlha6OEs_z5c-sCpzsxBrxL_4foKkuciu2UHY4EN_IHTywzEidkpzdYOIXqJd4naRDBaEclpp9orhnX2aZPstzbbPK7PNZW5YA-WI6GPOO1wg8kf9JyUk19jecTBZNZSwIVClqOacDEnioZfooYVUwHAZRQFDh-OWNZAWuTC6UCaMoGddRMdm3HWnP3BivT_rDerz1rwu22HuMX0ihxjDs34mhLIm4fCwi8gTDSLm1RbcJh09eZ_hJ9v8S3rYGhpS5aq3Y6aE_DaGQS958X73srP0TKqku4XJutXXgyd-O7VemPFUQ0K0X_avmnHtFL4MOv9lOcmmHG5mKT3zfEWdlO4RMgEmwBvMB4niPU0W56BEHF9AG9dHWoBUNuBbRZaNL4fxShPolT38BG_giO9nhmiBLQl7d7Suheo9HcD7rTxXCu3e43qePTlIADD86Llw-WqCNkELaGkBIwe5Cbj9LqKKQFiCj0nzeaigicm_Wdijw7vQWTJxJMdRXMZdHDQVwKZWgEvCghNLyVuq2s0vCyuaqaVIAA", "https://trumpia.crm.dynamics.com");
			refreshAccessToken.updateToken();		
						
			test = new DynamicsSchema(refreshAccessToken.accessToken);
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
