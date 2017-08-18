package com.trumpia.dynamics.APIcaller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.trumpia.util.Subscription;
import com.trumpia.dynamics.APIcaller.RefreshAccessToken;
import com.trumpia.util.Http.HttpRequest;

public class DynamicsAPIcaller {
	public static String ACCESS_TOKEN;
	//URL should be changed by USER
	public static String POST_URL; 
	private static String DELTA_KEY_URL= null;

	public JSONObject dynamicsResponse;
	private ArrayList<Subscription> changedSubscription = new ArrayList<Subscription>();

	public ArrayList<Subscription> getContactChange() {
		try {
			ACCESS_TOKEN = RefreshAccessToken.updateAccessToken();
			System.out.println(ACCESS_TOKEN);
			if(DELTA_KEY_URL == null)
				getDeltaKey();
			else
				retrieveChangedData();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (JSONException e) {
			e.printStackTrace();
		}

		return changedSubscription;	
	} 

	private void getDeltaKey() throws IOException, JSONException {
		HashMap<String, String> headers = new HashMap<String, String>();
		headers.put("Authorization", ACCESS_TOKEN);
		headers.put("OData-MaxVersion", "4.0");
		headers.put("OData-Version", "4.0");
		headers.put("Prefer", "odata.track-changes");		

		HttpRequest request = new HttpRequest.Builder()
				.URL(POST_URL)
				.headers(headers)
				.build();
		JSONObject json = new JSONObject(request.get());
		updateDeltaKey(json.get("@odata.deltaLink").toString());
		return;
	}
	
	private void retrieveChangedData() throws IOException, JSONException {
		JSONObject json = new JSONObject(sendSearchRequest());

		updateDeltaKey(json.getString("@odata.deltaLink"));
		getChangedData(json.getJSONArray("value"));
		return;
	}
	private String sendSearchRequest() throws IOException {
		HashMap<String, String> headers = new HashMap<String, String>();
		headers.put("Authorization", ACCESS_TOKEN);
		headers.put("OData-MaxVersion", "4.0");
		headers.put("OData-Version", "4.0");

		HttpRequest request = new HttpRequest.Builder()
				.URL(DELTA_KEY_URL)
				.headers(headers)
				.build();
		System.out.println("hi: "+request.get());
		return request.get();
	}
	
	private void updateDeltaKey(String key) {
		DELTA_KEY_URL = key;
	}
	
	private void getChangedData(JSONArray arr) throws JSONException {
		for(Object tmp : arr) {
			changedSubscription.add(new Subscription((JSONObject)tmp));
		}
	}
	public ArrayList<Subscription> getChangedSubscription() {
		return changedSubscription;
	}
}
