package com.trumpia.trumpia.services;

import static org.junit.Assert.assertEquals;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;

import com.trumpia.trumpia.model.TrumpiaAccountEntity;

/*
 * TEST ID..
 * ID : Johnhan
 * APIkey : 0c900c585477152326c20ff757625926 
 * 	 * SUBSCRIPTION EXAMPLE : 
 {
		"list_name" : "Test",
	"subscriptions" :
	[
		{"first_name" : "firstname",
		 "last_name" : "lastname",
		 "mobile" : { "number" : "2223334444",
		 			  "country_code" : "1"},
		 "voice_device" : "mobile"
		 }
	]
  } 
*/
public class TestTrumpiaAPIcaller {
	TrumpiaAccountEntity trumpia;
	String sampleSubscriptionBody = "{\"list_name\" : \"APIMobile\", \"subscriptions\":[    {      \"first_name\" : \"" + "firstname" + "\",      \"last_name\" : \"" + "lastname" + "\",\"mobile\" :      {        \"number\" : \"" + "7142545256" + "\",        \"country_code\" : \"1\"      },      \"voice_device\" : \"mobile\"    }  ]}";
	String sampleSubscriptionBodyForPost = "{\"list_name\" : \"APIMobile\",\"subscriptions\" :[{\"first_name\":\""+"post" + "\",\"last_name\" : \"" + "post" + "\",\"email\" : \"" + "test@test.com" + "\",\"landline\" :{\"number\" : \"" + "3004005000" + "\",\"country_code\" : \"1\"      },      \"voice_device\" : \"mobile\"    }  ]}";

	@Before
	public void setUp() {
		trumpia = new TrumpiaAccountEntity(); 
		trumpia.setAPIkey("0c900c585477152326c20ff757625926 ");
		trumpia.setUniqueId("Johnhan");
	}
	
	@Test
	public void TestTrumpiaAPIcall() {
		JSONObject response;
		//SEARCH TEST : MPSE2305	No subscription was found with given criteria - search type and search data.
		//SEARCH BY MOBILE
		response = TrumpiaAPIcaller.searchSubscriptionByMobile("7142545256", trumpia);
		assertEquals(response.get("status_code"), "MPSE2305");

		//PUT
		TrumpiaAPIcaller.putNewSubscriptionInfo(sampleSubscriptionBody, trumpia);
		JSONObject searchSubscriptionResponse = TrumpiaAPIcaller.searchSubscriptionByMobile("7142545256", trumpia);
		JSONArray subscriptionList = (JSONArray)searchSubscriptionResponse.get("subscription_id_list");
		assertEquals(subscriptionList.length(), 1);

		String InsertedSubscriptionId = subscriptionList.get(0).toString();
		
		//POST
		TrumpiaAPIcaller.postChangedSubscriptionInfo(sampleSubscriptionBodyForPost, InsertedSubscriptionId, trumpia);
		
		//SEARCH BY LANDLINE
		response = TrumpiaAPIcaller.searchSubscriptionByLandline("3004005000", trumpia);
		subscriptionList = (JSONArray)searchSubscriptionResponse.get("subscription_id_list");
		assertEquals(subscriptionList.length(), 1);
		assertEquals(subscriptionList.get(0).toString(), InsertedSubscriptionId);		
		
		//SEARCH BY EMAIL
		response = TrumpiaAPIcaller.searchSubscriptionByEmail("test@test.com", trumpia);
		subscriptionList = (JSONArray)searchSubscriptionResponse.get("subscription_id_list");
		assertEquals(subscriptionList.length(), 1);
		assertEquals(subscriptionList.get(0).toString(), InsertedSubscriptionId);
		
		//DELETE SUBSCRIPTION
		TrumpiaAPIcaller.deleteSubscriptionInfo(InsertedSubscriptionId , trumpia);
		response = TrumpiaAPIcaller.searchSubscriptionByMobile("7142545256", trumpia);
		assertEquals(response.get("status_code"), "MPSE2305");
	}
	
}

