package com.trumpia.trumpia.services;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;

import com.trumpia.trumpia.data.SubscriptionIdMappingRepository;
import com.trumpia.trumpia.model.Subscription;
import com.trumpia.trumpia.model.SubscriptionIdMappingEntity;
import com.trumpia.trumpia.model.TrumpiaAccountEntity;

public class TrumpiaAPIcaller {
	@Autowired
	private SubscriptionIdMappingRepository mappingRepo;
	private SubscriptionPostHandler post;
	private boolean deleteOption;
	private List<Subscription> putList;
	//Need to fix
	private TrumpiaAccountEntity trumpia;
	
	/*can get parameter as mappinginfo
	postOption = update / replace
	deleteOption = delete / undelete
	*/
	public TrumpiaAPIcaller(TrumpiaAccountEntity trumpia, String postOption, boolean deleteOption){
		this.trumpia = trumpia; 
		putList = new ArrayList<Subscription>();
		post = TrumpiaAPIcallerFactory.post(postOption, trumpia);
		this.deleteOption = deleteOption;
	}
	
	public void handleSubscriptionList(List<Subscription> subscriptions, String listName) { 
		for(Subscription subs : subscriptions)
			handleSubscription(subs, listName);
				
		sendPutRequest(listName);//db접근
	}
	
	private void handleSubscription(Subscription subs, String listName) {
		if(subs.noContactInfo()) // this part need LOG!! 
			return; 
		if(subs.isDeleted())
			deleteSubs(subs);
		else
			upload(subs, listName);		
	}
	
	private void deleteSubs(Subscription subs) {
		SubscriptionIdMappingEntity map = mappingRepo.findOneByTargetSubscriptionId(subs.getId());
		if(map != null) {
			if(deleteOption) {
				TrumpiaAPILibrary.deleteSubscriptionInfo(map.getTrumpiaId(), trumpia);
				mappingRepo.delete(map.getId());
			}	
		}
	}
	
	private void upload(Subscription subs, String list) {
		if(isSubscriptionOnDatabase(subs))
			postAndSaveToRepo(subs, list);
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
	
	private void postAndSaveToRepo(Subscription subs, String list) {
		String trumpiaId = post.postSubscription(subs, list);
		String targetId = subs.getId();
		
		SubscriptionIdMappingEntity map = mappingRepo.findOneByTargetSubscriptionId(targetId);
		
		//if trumpiaId == null, exception happened
		if(trumpiaId != null) {
			if(map != null)
				mappingRepo.delete(map.getId());
			
			mappingRepo.save(createMappingEntity(trumpiaId, targetId));
		}		
	}
	
	private SubscriptionIdMappingEntity createMappingEntity(String subsId, String targetId) {
		SubscriptionIdMappingEntity tmp = new SubscriptionIdMappingEntity(subsId, targetId);
		return tmp;
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

		JSONObject response = TrumpiaAPILibrary.putNewSubscriptionInfo(createSubscriptionBody(listName).toString(), trumpia);
		saveSubscriptionMappingInfo(response);
	}

	private JSONObject createSubscriptionBody(String listName) {
		JSONObject subscriptionBody = new JSONObject();
		JSONArray subscriptionList = new JSONArray();
		
		for(Subscription subscription : putList)
			subscriptionList.put(subscription.toJSON());
		
		subscriptionBody.put("list_name", listName);
		subscriptionBody.put("subscriptions", subscriptionList);
		return subscriptionBody;
	}

	private void saveSubscriptionMappingInfo(JSONObject response) {
		JSONArray request = new JSONArray(TrumpiaAPILibrary.getStatusReport(response.getString("request_id"), trumpia));

		for(int i = 0 ; i < request.length(); i++) {
			if(request.getJSONObject(i).has("subscription_id")) {
				String trumpiaId = request.getJSONObject(i).getString("subscription_id");
				String targetId = putList.get(i).getId();
				mappingRepo.save(createMappingEntity(trumpiaId, targetId));
			}
		}
	}
}
