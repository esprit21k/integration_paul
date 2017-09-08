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
			JSONObject request = new JSONObject(TrumpiaAPILibrary.getStatusReport(response.getString("request_id"), trumpia));
			return request.get("subscription_id").toString();
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
			JSONObject response = TrumpiaAPILibrary.searchSubscriptionByLandline(subsJSON.getJSONObject("landline").getString("number"), trumpia);
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
			if(target.getJSONObject("email").getString("value").equals(subs.getEmail()))
				subs.setEmail(null);			
		}
		
		if(target.has("mobile") && subs.getMobileNumber() != null) {
			if(target.getJSONObject("mobile").getString("value").equals(subsJSON.getJSONObject("mobile").getString("number"))
				&& target.getJSONObject("mobile").getString("country_code").equals(subsJSON.getJSONObject("mobile").getString("country_code")))
				subs.setMobileNumber(null);
		}
		
		if(target.has("landline") && subs.getLandLine() != null) {
			if(target.getJSONObject("landline").getString("value").equals(subsJSON.getJSONObject("landline").getString("number"))
					&& target.getJSONObject("landline").getString("country_code").equals(subsJSON.getJSONObject("landline").getString("country_code")))
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
