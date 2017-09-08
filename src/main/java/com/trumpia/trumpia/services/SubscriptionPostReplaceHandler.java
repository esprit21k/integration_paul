package com.trumpia.trumpia.services;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.trumpia.trumpia.model.Subscription;
import com.trumpia.trumpia.model.TrumpiaAccountEntity;

/*
 * Post Option - REPLACE
 * */

public class SubscriptionPostReplaceHandler implements SubscriptionPostHandler {
/*
 * Replace
 * find id, delete it & put subscription.
 * */
	TrumpiaAccountEntity trumpia;
	
	public SubscriptionPostReplaceHandler(TrumpiaAccountEntity trumpia) {
		this.trumpia = trumpia;
	}
	
	@Override
	public String postSubscription(Subscription subs, String list) {
		
		List<String> relatedIds = haveSameContactInfo(subs);
		deleteRelatedIds(relatedIds);

		//put subscription
		String subscriptionsBody = createSubscriptionBody(subs, list);
		System.out.println(subscriptionsBody);
		JSONObject response = TrumpiaAPILibrary.putNewSubscriptionInfo(subscriptionsBody, trumpia);
		JSONArray request = new JSONArray(TrumpiaAPILibrary.getStatusReport(response.getString("request_id"), trumpia));
		System.out.println(request.toString());
		//return id
		return request.getJSONObject(0).get("subscription_id").toString();
	}
	
	private List<String> haveSameContactInfo(Subscription subs){
		List<String> relatedIds = new ArrayList<String>();
		JSONObject subsJSON = subs.toJSON();

		if(subsJSON.has("mobile")) {
			JSONObject response = TrumpiaAPILibrary.searchSubscriptionByMobile(subsJSON.getJSONObject("mobile").getString("number"), trumpia);
			if(response.has("subscription_id_list"))
				relatedIds.add(response.getJSONArray("subscription_id_list").getString(0));
		}
		if(subsJSON.has("email")) {
			JSONObject response = TrumpiaAPILibrary.searchSubscriptionByEmail(subsJSON.getString("email"), trumpia);
			if(response.has("subscription_id_list"))
				relatedIds.add(response.getJSONArray("subscription_id_list").getString(0));
		}
		if(subsJSON.has("landline")) {
			JSONObject response = TrumpiaAPILibrary.searchSubscriptionByLandline(subsJSON.getJSONObject("landline").getString("number"), trumpia);
			if(response.has("subscription_id_list"))
				relatedIds.add(response.getJSONArray("subscription_id_list").getString(0));
		}
		
		HashSet<String> set = new HashSet<String>(relatedIds);
		List<String> relatedIdsWithDistinctData = new ArrayList<String>(set);
		
		return relatedIdsWithDistinctData;
	}
	
	private void deleteRelatedIds(List<String> relatedIds) {
		for(int i = 0 ; i < relatedIds.size() ; i++) 
			TrumpiaAPILibrary.deleteSubscriptionInfo(relatedIds.get(i), trumpia);
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
