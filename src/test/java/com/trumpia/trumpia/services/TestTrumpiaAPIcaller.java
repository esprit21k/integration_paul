package com.trumpia.trumpia.services;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.trumpia.Main;
import com.trumpia.trumpia.data.SubscriptionIdMappingRepository;
import com.trumpia.trumpia.model.Subscription;
import com.trumpia.trumpia.model.SubscriptionIdMappingEntity;
import com.trumpia.trumpia.model.TrumpiaAccountEntity;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = {Main.class})
@ActiveProfiles("dev")
public class TestTrumpiaAPIcaller {
	private final String LISTNAME = "apicaller";
	private final String TESTID = "test-id-here";

	@Autowired
	private SubscriptionIdMappingRepository mappingRepo;
	
	@Autowired
	private TrumpiaAPIcaller caller;
	
	TrumpiaAccountEntity trumpia;
	List<Subscription> set;
	Subscription testData;
	String listId;

	@Before
	public void setUp(){
		//Test trumpia account
		trumpia = new TrumpiaAccountEntity();
		trumpia.setApikey("0c900c585477152326c20ff757625926");
		trumpia.setUsername("JohnHan");

		//init data
		set = new ArrayList<Subscription>();
		testData= new Subscription();

		//create test list
		JSONObject body = new JSONObject();
		body.put("list_name", LISTNAME);
		body.put("display_name", "apicaller");
		body.put("frequency", "2");
		body.put("description", "test for apicaller");
		
		JSONObject list = TrumpiaAPILibrary.setList(body.toString(), trumpia);
		listId = list.get("list_id").toString();
		
		triggerData();
	}

	@Test
	public void undelete() {
		caller.setDeleteOption(false);
		caller.setPost("update", trumpia);
		caller.setTrumpia(trumpia);

		//test data
		testData.setDeleted(true);
		testData.setId("test-id-here");
		set.add(testData);
		
		assertEquals(mappingRepo.count(),1);
		caller.handleSubscriptionList(set, LISTNAME);
 
		
		JSONObject 	response = TrumpiaAPILibrary.searchSubscriptionByEmail("email2@test.com", trumpia);
		JSONArray subscriptionList = (JSONArray)response.get("subscription_id_list");
		assertEquals(subscriptionList.length(), 1);
	}

	@Test
	public void delete() {
		caller.setDeleteOption(true);
		caller.setPost("update", trumpia);
		caller.setTrumpia(trumpia);

		//test data
		testData.setDeleted(true);
		testData.setId("test-id-here");

		set.add(testData);
		assertEquals(mappingRepo.count(),1);
		
		caller.handleSubscriptionList(set, LISTNAME);

		assertEquals(mappingRepo.count(),0);
		JSONObject response = TrumpiaAPILibrary.searchSubscriptionByEmail("email2@test.com", trumpia);
		assertEquals(response.get("status_code"), "MPSE2305");
	}

			
	@Test
	public void update() {
		caller.setDeleteOption(true);
		caller.setTrumpia(trumpia);
		caller.setPost("update", trumpia);
		
		
		//testData
		testData.setEmail("email2@test.com");
		testData.setLandLine("7142003000");
		testData.setId(TESTID);
		set.add(testData);

		assertEquals(mappingRepo.count(), 1);
		SubscriptionIdMappingEntity mappingInfo = mappingRepo.findOneByTargetSubscriptionId(TESTID);
		
		caller.handleSubscriptionList(set, LISTNAME);
		
		JSONObject response = TrumpiaAPILibrary.searchSubscriptionByEmail("email2@test.com", trumpia);
		JSONArray subscriptionList = (JSONArray)response.get("subscription_id_list");
		JSONObject testResult = TrumpiaAPILibrary.searchSubscriptionBySubsId(subscriptionList.getString(0), trumpia);
		
		assertEquals(mappingRepo.count(), 1);
		assertEquals(mappingInfo.getTargetSubscriptionId(), mappingRepo.findOneByTargetSubscriptionId(TESTID).getTargetSubscriptionId());
		assertEquals(mappingInfo.getTrumpiaId(), mappingRepo.findOneByTargetSubscriptionId(TESTID).getTrumpiaId());
		assertEquals(testResult.getJSONObject("mobile").getString("value"), "2343004000");
		assertEquals(testResult.getJSONObject("landline").getString("value"), "7142003000");
		assertEquals(testResult.getJSONObject("email").getString("value"), "email2@test.com");
	}

	@Test
	public void replace() {
		caller.setDeleteOption(true);
		caller.setTrumpia(trumpia);
		caller.setPost("replace", trumpia);
		
		//testData
		testData.setEmail("email2@test.com");
		testData.setLandLine("7142003000");
		testData.setId(TESTID);
		set.add(testData);
		
		assertEquals(mappingRepo.count(), 1);
		
		caller.handleSubscriptionList(set, LISTNAME);
		
		JSONObject response = TrumpiaAPILibrary.searchSubscriptionByEmail("email2@test.com", trumpia);
		JSONArray subscriptionList = (JSONArray)response.get("subscription_id_list");
		JSONObject testResult = TrumpiaAPILibrary.searchSubscriptionBySubsId(subscriptionList.getString(0), trumpia);
		
		assertEquals(mappingRepo.count(), 1);
		assertFalse(testResult.has("mobile"));
		assertEquals(testResult.getJSONObject("landline").getString("value"), "7142003000");
		assertEquals(testResult.getJSONObject("email").getString("value"), "email2@test.com");
	}
	
	@After
	public void tearUp() {
		//delete used list
		TrumpiaAPILibrary.deleteList(listId, trumpia);
		mappingRepo.deleteAll();
	}

	private void triggerData(){
		Subscription data = new Subscription();
		data.setEmail("email2@test.com");
		data.setMobileNumber("2343004000");

		JSONObject response = TrumpiaAPILibrary.putNewSubscriptionInfo(createSubscriptionBody(data, LISTNAME), trumpia);
		JSONArray request = new JSONArray(TrumpiaAPILibrary.getStatusReport(response.getString("request_id"), trumpia));
		System.out.println(request.toString());
		String trumpiaId = request.getJSONObject(0).get("subscription_id").toString();
		
		SubscriptionIdMappingEntity tmp = new SubscriptionIdMappingEntity(trumpiaId, TESTID);
		mappingRepo.save(tmp);
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
