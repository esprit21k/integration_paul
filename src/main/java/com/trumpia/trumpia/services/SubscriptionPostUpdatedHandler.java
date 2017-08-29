package com.trumpia.trumpia.services;

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
		//delete repeated contact info
		
		//request body
		//post	
		return null;
	}
	
	private String findSubscriptionId(Subscription subs) {
		JSONObject subsJSON = subs.toJSON();
		String Id;
		
		//delete repeated
		if(subsJSON.has("email")) {
			JSONObject response = TrumpiaAPILibrary.searchSubscriptionByEmail(subsJSON.getString("email"), trumpia);
			if(response.has("subscription_id_list")) {
				
				return response.getJSONArray("subscription_id_list").getString(0);
			}
				
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
