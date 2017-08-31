package com.trumpia.trumpia.services;

import org.json.JSONArray;
import org.json.JSONObject;

import com.trumpia.trumpia.model.Subscription;
import com.trumpia.trumpia.model.TrumpiaAccountEntity;

public class SubscriptionPostUpdatedHandler implements SubscriptionPostHandler {

	TrumpiaAccountEntity trumpia;
	
	public SubscriptionPostUpdatedHandler(TrumpiaAccountEntity trumpia) {
		this.trumpia = trumpia;
	}

	public String postSubscription(Subscription subscription, String list) {
		String id = findSubscriptionId(subscription);
		deleteRepeatedContactInfo(id, subscription);
		JSONObject response = TrumpiaAPILibrary.postChangedSubscriptionInfo(createSubscriptionBody(subscription, list), id, trumpia);
		
		JSONArray request = new JSONArray(TrumpiaAPILibrary.getStatusReport(response.getString("request_id"), trumpia));
		//return id
		return request.getJSONObject(0).getString("subscription_id");
	}
	
	private String findSubscriptionId(Subscription subs) {
		JSONObject subsJSON = subs.toJSON();
		String Id;
		
		//delete repeated
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
	
	private void deleteRepeatedContactInfo(String id, Subscription subs) {
		JSONObject target = TrumpiaAPILibrary.searchSubscriptionBySubsId(id, trumpia);
		JSONObject subsJSON = subs.toJSON();
		
		if(target.has("email") && subs.getEmail() != null) {
			if(target.getString("email").equals(subs.getEmail()))
				subs.setEmail(null);
		}
		
		if(target.has("mobile") && subs.getMobileNumber() != null) {
			if(target.getJSONObject("mobile").equals(subsJSON.getJSONObject("mobile")))
				subs.setMobileNumber(null);
		}
		
		if(target.has("landline") && subs.getLandLine() != null) {
			if(target.getJSONObject("landline").equals(subsJSON.getJSONObject("landline")))
				subs.setLandLine(null);
		}
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
