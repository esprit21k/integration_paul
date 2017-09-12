package com.trumpia.trumpia.services;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.trumpia.trumpia.model.Subscription;
import com.trumpia.trumpia.model.TrumpiaAccountEntity;
import com.trumpia.trumpia.util.SubscriptionBody;

public class TestSubscriptionPostRepalceHandler {
	TrumpiaAccountEntity trumpia;
	SubscriptionPostReplaceHandler replace;

	String [] ids = new String[3];
	String listId;

	@Before
	public void setUp() {

		///Trumpia account 
		trumpia = new TrumpiaAccountEntity();
		trumpia.setApikey("0c900c585477152326c20ff757625926");
		trumpia.setUsername("JohnHan");
		replace =  new SubscriptionPostReplaceHandler(trumpia);

		// list info
		JSONObject body = new JSONObject();
		body.put("list_name", "test1qw32");
		body.put("display_name", "test1qw32");
		body.put("frequency", "2");
		body.put("description", "test for replace");

		JSONObject list = TrumpiaAPILibrary.setList(body.toString(), trumpia);
		listId = list.get("list_id").toString();

		Subscription[] subs = new Subscription[3];
		subs[0]	= new Subscription();
		subs[0].setMobileNumber("7142555255");

		subs[1] = new Subscription();
		subs[1].setEmail("test1qw32@gmail.com");

		subs[2] = new Subscription();
		subs[2].setLandLine("7143004000");

		for(int i = 0; i < subs.length ; i++) {
			String tmp = SubscriptionBody.createSubscriptionBody(subs[i], "test1qw32");
			JSONObject response = TrumpiaAPILibrary.putNewSubscriptionInfo(tmp, trumpia);
			String report = TrumpiaAPILibrary.getStatusReport(response.getString("request_id"), trumpia);			
			JSONArray reportArray = new JSONArray(report);
			ids[i] = reportArray.getJSONObject(0).get("subscription_id").toString();
		}
	}

	@Test
	public void test() {
		Subscription input = new Subscription();
		input.setMobileNumber("7142555255");		
		input.setEmail("test1qw32@gmail.com");
		input.setLandLine("7143004000");
		String id = replace.postSubscription(input, "test1qw32");

		//is deleted
		for(int i = 0 ; i < ids.length; i++) {
			JSONObject response = TrumpiaAPILibrary.searchSubscriptionBySubsId(ids[i], trumpia);
			assertTrue(response.getString("status_code").equals("MPSE2303"));	
		}

		//is right data posted?
		JSONObject response = TrumpiaAPILibrary.searchSubscriptionBySubsId(id, trumpia);
		assertEquals(response.getJSONObject("mobile").getString("value"), "7142555255");
		assertEquals(response.getJSONObject("landline").getString("value"), "7143004000");
		assertEquals(response.getJSONObject("email").getString("value"), "test1qw32@gmail.com");
	}

	@After
	public void tearUp() {
		for(int i = 0 ; i < ids.length ; i++)
			TrumpiaAPILibrary.deleteSubscriptionInfo(ids[i], trumpia);

		TrumpiaAPILibrary.deleteList(listId, trumpia);
	}
}

