package com.trumpia.trumpia.services;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.trumpia.trumpia.model.Subscription;
import com.trumpia.trumpia.model.TrumpiaAccountEntity;

public class TrumpiaAPIcaller {
	private SubscriptionPostHandler post;
	private DeletedSubscriptionHandler delete;
	private List<Subscription> putList;
	//Need to fix
	private TrumpiaAccountEntity trumpia;
	
	//can get parameter as mappinginfo
	public TrumpiaAPIcaller(String postOption, String deleteOption){
		//Fix : init tumpiaAccountEntity;
		putList = new ArrayList<Subscription>();
		post = TrumpiaAPIcallerFactory.post(postOption, trumpia);
		delete = TrumpiaAPIcallerFactory.delete(deleteOption, trumpia);
	}
	
	public void handleSubscriptionList(List<Subscription> subscriptions, String listName) { 
		for(Subscription subs : subscriptions)
			handleSubscription(subs, listName);
				
		sendPutRequest(listName);
	}
	
	private void handleSubscription(Subscription subs, String listName) {
		if(noContactInfo(subs)) // this part need LOG!! 
			return; 
		if(subs.isDeleted())
			delete.deleteSubscription(subs);
		else
			upload(subs, listName);		
	}
	
	private boolean noContactInfo(Subscription subs) {
		return subs.getLandLine().equals(null) && subs.getEmail().equals(null) && subs.getMobileNumber().equals(null); 
	}
	
	
	private void upload(Subscription subs, String list) {
		if(isSubscriptionOnDatabase(subs))
			post.postSubscription(subs, list);
 		else
			putList.add(subs);
	}
	
	private boolean isSubscriptionOnDatabase(Subscription subs) {
		JSONObject subsJSON = subs.toJSON();
		if(subsJSON.has("email") && searchSubscriptionByEmail(subsJSON))
			return true;
		if(subsJSON.has("mobile") && searchSubscriptionByMobile(subsJSON))
			return true;
		if(subsJSON.has("landline") && searchSubscriptionByLandline(subsJSON))
			return true;

		return false;
	}
	
	private boolean searchSubscriptionByEmail(JSONObject subsJSON) {
		JSONObject response = TrumpiaAPILibrary.searchSubscriptionByEmail(subsJSON.getString("email"), trumpia);
		return isSubscriptionExist(response);
	}
	
	private boolean searchSubscriptionByMobile(JSONObject subsJSON) {
		String mobile = subsJSON.getJSONObject("mobile").getString("number");
		JSONObject response = TrumpiaAPILibrary.searchSubscriptionByMobile(mobile, trumpia);
		return isSubscriptionExist(response);
	}
	
	private boolean searchSubscriptionByLandline(JSONObject subsJSON) {
		String landline = subsJSON.getJSONObject("landline").getString("number");
		JSONObject response = TrumpiaAPILibrary.searchSubscriptionByMobile(landline, trumpia);
		return isSubscriptionExist(response);
	}

	private boolean isSubscriptionExist(JSONObject response) {
		if(response.has("subscription_id_list")) 
			return true;
		return false;
	}
	
	private void sendPutRequest(String listName) {
		if(putList.size() == 0) return;
		
		JSONObject subscriptionBody = new JSONObject();
		JSONArray subscriptionList = new JSONArray();
		
		for(Subscription subscription : putList)
			subscriptionList.put(subscription.toJSON());
		
		subscriptionBody.put("list_name", listName);
		subscriptionBody.put("subscriptions", subscriptionList);
		
		TrumpiaAPILibrary.putNewSubscriptionInfo(subscriptionBody.toString(), trumpia);
	}
	
	
}
