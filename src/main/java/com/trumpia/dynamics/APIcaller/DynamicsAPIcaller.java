package com.trumpia.dynamics.APIcaller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.trumpia.trumpia.model.Subscription;
import com.trumpia.util.Http.HttpRequest;

public class DynamicsAPIcaller {
	private String accessToken;
	private String initializeURL;
	private String deltaKeyURL;

	public JSONObject dynamicsResponse;
	private ArrayList<Subscription> changedSubscription = new ArrayList<Subscription>();
	
	public DynamicsAPIcaller(String accessToken, String resourseURL) {
		this.accessToken = accessToken;
		this.initializeURL = resourseURL+"/api/data/v8.2/contacts?&$select=firstname,lastname,mobilephone";
	}

	public ArrayList<Subscription> getContactChange() {
		try {
			System.out.println(accessToken);
			if(deltaKeyURL == null)
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
		headers.put("Authorization", accessToken);
		headers.put("OData-MaxVersion", "4.0");
		headers.put("OData-Version", "4.0");
		headers.put("Prefer", "odata.track-changes");		

		HttpRequest request = new HttpRequest.Builder()
				.URL(initializeURL)
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
		headers.put("Authorization", accessToken);
		headers.put("OData-MaxVersion", "4.0");
		headers.put("OData-Version", "4.0");

		HttpRequest request = new HttpRequest.Builder()
				.URL(deltaKeyURL)
				.headers(headers)
				.build();
		System.out.println("hi: "+request.get());
		return request.get();
	}
	
	private void updateDeltaKey(String key) {
		deltaKeyURL = key;
	}
	
	private void getChangedData(JSONArray arr) throws JSONException {
		for(Object tmp : arr) {
			//changedSubscription.add(new Subscription((JSONObject)tmp));
		}
	}
	public ArrayList<Subscription> getChangedSubscription() {
		return changedSubscription;
	}
}
