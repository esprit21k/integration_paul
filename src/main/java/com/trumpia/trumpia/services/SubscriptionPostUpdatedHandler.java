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
		try {
			String id = findSubscriptionId(subscription);
			deleteRepeatedContactInfo(id, subscription);
			JSONObject response = TrumpiaAPILibrary.postChangedSubscriptionInfo(createSubscriptionBody(subscription, list), id, trumpia);
			
			JSONArray request = new JSONArray(TrumpiaAPILibrary.getStatusReport(response.getString("request_id"), trumpia));
			return request.getJSONObject(0).getString("subscription_id");
		}
		catch(IllegalArgumentException e) {
			e.getMessage();
			//need log
		}
		
		return null;
	}
		
	private String findSubscriptionId(Subscription subs) {
		JSONObject subsJSON = subs.toJSON();
		String id = null;

		if(subsJSON.has("mobile")) {
			JSONObject response = TrumpiaAPILibrary.searchSubscriptionByMobile(subsJSON.getJSONObject("mobile").getString("number"), trumpia);
			if(response.has("subscription_id_list"))
				id = response.getJSONArray("subscription_id_list").getString(0);
		}
		
		if(subsJSON.has("email")) {
			JSONObject response = TrumpiaAPILibrary.searchSubscriptionByEmail(subsJSON.getString("email"), trumpia);
			if(response.has("subscription_id_list")) {
				if(id == null) {
					id = response.getJSONArray("subscription_id_list").getString(0);
				}
				else {
					if(id != response.getJSONArray("subscription_id_list").getString(0))
						throw new IllegalArgumentException("email");
				}
			}
		}
		
		if(subsJSON.has("landline")) {
			JSONObject response = TrumpiaAPILibrary.searchSubscriptionByMobile(subsJSON.getJSONObject("lnadline").getString("number"), trumpia);
			if(response.has("subscription_id_list")) {
				if(id == null) {
					id = response.getJSONArray("subscription_id_list").getString(0);
				}
				else {
					if(id != response.getJSONArray("subscription_id_list").getString(0))
						throw new IllegalArgumentException("landline");
				}
			}
		}
		
		return id;
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
