package com.trumpia.trumpia.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;

import com.trumpia.dynamics.services.SubscriptionParser;
import com.trumpia.mapping.model.MappingEntity;



public class TestSubscription {
	List<MappingEntity> testSchema;
	JSONObject testObject;
	
	@Before
	public void setUp() {
		//testScheam
		testSchema = new ArrayList<MappingEntity>();
		testSchema.add(makeEntity("firstname", "first_name"));
		testSchema.add(makeEntity("lastname", "last_name"));
		testSchema.add(makeEntity("mobilephone", "mobile"));
		testSchema.add(makeCustomEntity("customData1", "customData1", "12345"));
		testSchema.add(makeCustomEntity("customData2", "customData2", "78901"));
		testSchema.add(makeCustomEntity("customData3", "customData3", "45621"));
		//testJSON
		testObject = new JSONObject();
		testObject.put("id", "something-id-is-inside");
		testObject.put("firstname", "firstname");
		testObject.put("lastname", "lastname");
		testObject.put("mobilephone", "7142545256");
		testObject.put("customData1", "data_customData1");
		testObject.put("customData2", "data_customData2");
		testObject.put("customData3", "data_customData3");
	}
	@Test
	public void TestSubscriptiontoJSON() {	
		//TEST
		SubscriptionParser subs = new SubscriptionParser(testObject, testSchema);
		Subscription subscriptiontest = subs.getParsedSubscription();
		JSONObject result = subscriptiontest.toJSON();
		assertNotNull(result.remove("first_name"));
		assertNotNull(result.remove("last_name"));
		assertNotNull(result.remove("mobile"));
		assertEquals(((JSONArray)result.get("customdata")).length() , 3);
	}
	@Test
	public void TestDeletedSubscriptionToJSON() {
		//DeletedJSON object
		JSONObject deletedObject =new JSONObject();
		deletedObject.put("id", "something-id-is-inside");
		deletedObject.put("reason", "deleted");
		
		//DeletedSubscription
		SubscriptionParser subs = new SubscriptionParser(deletedObject, testSchema);
		Subscription subscriptiontest = subs.getParsedSubscription();
		assertEquals(subscriptiontest.toJSON().get("deletedDynamicID"), "something-id-is-inside");
	}
	@Test
	public void TestSubscriptionPrivateField() {
		//TEST
		SubscriptionParser subs = new SubscriptionParser(testObject, testSchema);
		Subscription subscriptiontest = subs.getParsedSubscription();
		assertEquals(subscriptiontest.getFirstName(), "firstname");
		assertEquals(subscriptiontest.getLastName(), "lastname");
		assertEquals(subscriptiontest.getMobileNumber(), "7142545256");
		
	}

	private MappingEntity makeEntity(String dynamic, String trumpia) {
		MappingEntity tmp = new MappingEntity();
		tmp.setTargetFieldName(dynamic);
		tmp.setTrumpiaFieldName(trumpia);
		return tmp;
	}
	private MappingEntity makeCustomEntity(String dynamic, String trumpia, String Id) {
		MappingEntity customData = new MappingEntity();
		customData.setCustomDataId(Id);
		customData.setTrumpiaFieldName(trumpia);
		customData.setTargetFieldName(dynamic);
		return customData;
	}
}
