package com.trumpia.trumpia.services;

import static org.junit.Assert.assertEquals;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.trumpia.trumpia.model.Subscription;
import com.trumpia.trumpia.model.TrumpiaAccountEntity;

public class TestSubscriptionPostUpdateHandler {
	TrumpiaAccountEntity trumpia;
	SubscriptionPostUpdatedHandler update;

	String [] ids = new String[3];
	String listId;
	
	@Before
	public void setUp() {

		///Trumpia account 
		trumpia = new TrumpiaAccountEntity();
		trumpia.setApikey("0c900c585477152326c20ff757625926");
		trumpia.setUniqueId("JohnHan");
		update =  new SubscriptionPostUpdatedHandler(trumpia);

		// list info
		JSONObject body = new JSONObject();
		body.put("list_name", "testUpdated");
		body.put("display_name", "testUpdated");
		body.put("frequency", "2");
		body.put("description", "test for update");

		JSONObject list = TrumpiaAPILibrary.setList(body.toString(), trumpia);
		listId = list.get("list_id").toString();

		Subscription[] subs = new Subscription[3];
		subs[0]	= new Subscription();
		subs[0].setMobileNumber("7142555255");

		subs[1] = new Subscription();
		subs[1].setEmail("testupdated@gmail.com");

		subs[2] = new Subscription();
		subs[2].setLandLine("7143004000");

		for(int i = 0; i < subs.length ; i++) {
			String tmp = createSubscriptionBody(subs[i], "testUpdated");
			JSONObject response = TrumpiaAPILibrary.putNewSubscriptionInfo(tmp, trumpia);
			String report = TrumpiaAPILibrary.getStatusReport(response.getString("request_id"), trumpia);			
			JSONArray reportArray = new JSONArray(report);
			ids[i] = reportArray.getJSONObject(0).get("subscription_id").toString();
		}
	}
	
	@Test
	public void illegalArgumentTest() {
		Subscription input = new Subscription();
		input.setMobileNumber("7142555255");		
		input.setEmail("testUpdated@gmail.com");
		input.setLandLine("7143004000");
		String id = update.postSubscription(input, "testUpdated");
		
		assertEquals(id, null);
		
		Subscription input2 = new Subscription();
		input2.setEmail("testUpdated@gmail.com");
		input2.setLandLine("7143004000");
		String id2 = update.postSubscription(input2, "testUpdated");
		
		assertEquals(id2, null);

		Subscription input3 = new Subscription();
		input3.setMobileNumber("7142555255");
		input3.setLandLine("7143004000");
		String id3 = update.postSubscription(input3, "testUpdated");
		
		assertEquals(id3, null);
		
		Subscription input4 = new Subscription();
		input4.setMobileNumber("7142555255");
		input4.setEmail("testUpdated@gmail.com");
		String id4 = update.postSubscription(input3, "testUpdated");
		
		assertEquals(id4, null);
	}
	
	@Test//(expected=IllegalArgumentException.class)
	public void postTesting() {
		Subscription input = new Subscription();
		input.setMobileNumber("7142555255");		
		input.setEmail("differentData@gmail.com");
		String id = update.postSubscription(input, "testUpdated");
		
		assertEquals(id, ids[0]);
		
		Subscription input2 = new Subscription();
		input2.setMobileNumber("7142545256");		
		input2.setEmail("testupdated@gmail.com");
		String id2 = update.postSubscription(input2, "testUpdated");
		
		assertEquals(id2, ids[1]);
		
		Subscription input3 = new Subscription();
		input3.setMobileNumber("7142545254");		
		input3.setLandLine("7143004000");
		String id3 = update.postSubscription(input3, "testUpdated");
		
		assertEquals(id3, ids[2]);
	}
	
	@After
	public void tearUp() {
		TrumpiaAPILibrary.deleteList(listId, trumpia);
	}
	
	private String createSubscriptionBody(Subscription subs, String list) {
		JSONObject body = new JSONObject();
		JSONArray subscriptions = new JSONArray();
		subscriptions.put(subs.toJSON());

		body.put("list_name", list);
		body.put("subscriptions", subscriptions);

		return body.toString();
	}	


}
