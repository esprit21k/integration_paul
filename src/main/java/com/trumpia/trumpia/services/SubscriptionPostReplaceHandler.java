package com.trumpia.trumpia.services;

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
	
	public String postSubscription(Subscription subs, String list) {
		//find subscription id
		String subsID = findSubscriptionId(subs);
		//delete it
		TrumpiaAPILibrary.deleteSubscriptionInfo(subsID, trumpia);
		//put subscription
		String subscriptionsBody = createSubscriptionBody(subs, list);
		JSONObject response = TrumpiaAPILibrary.putNewSubscriptionInfo(subscriptionsBody, trumpia);
		
		JSONArray request = new JSONArray(TrumpiaAPILibrary.getStatusReport(response.getString("request_id"), trumpia));
		//return id
		return request.getJSONObject(0).getString("subscription_id");
	}

	private String createSubscriptionBody(Subscription subs, String list) {
		JSONObject body = new JSONObject();
		JSONArray subscriptions = new JSONArray();
		subscriptions.put(subs.toJSON());
		
		body.put("list_name", list);
		body.put("subscriptions", subscriptions);
		
		return body.toString();
	}	
	
	private String findSubscriptionId(Subscription subs) {
		JSONObject subsJSON = subs.toJSON();
		if(subsJSON.has("email")) {
			JSONObject response = TrumpiaAPILibrary.searchSubscriptionByEmail(subsJSON.getString("email"), trumpia);
			if(response.has("subscription_id_list"))
				return response.getJSONArray("subscription_id_list").getString(0);
		}
		if(subsJSON.has("mobile")) {
			JSONObject response = TrumpiaAPILibrary.searchSubscriptionByMobile(subsJSON.getJSONObject("mobile").getString("number"), trumpia);
			if(response.has("subscription_id_list"))
				return response.getJSONArray("subscription_id_list").getString(0);
		}
		if(subsJSON.has("landline")) {
			JSONObject response = TrumpiaAPILibrary.searchSubscriptionByMobile(subsJSON.getJSONObject("lnadline").getString("number"), trumpia);
			if(response.has("subscription_id_list"))
				return response.getJSONArray("subscription_id_list").getString(0);
		}		
		return null;
	}
}
