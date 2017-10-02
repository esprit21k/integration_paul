package com.trumpia.trumpia.util;

import org.json.JSONArray;
import org.json.JSONObject;

import com.trumpia.trumpia.model.Subscription;

public class SubscriptionBody {
	public static String createSubscriptionBody(Subscription subs, String list) {
		JSONObject body = new JSONObject();
		JSONArray subscriptions = new JSONArray();
		subscriptions.put(subs.toJSON());
		
		body.put("list_name", list);
		body.put("subscriptions", subscriptions);
		
		return body.toString();
	}
}
