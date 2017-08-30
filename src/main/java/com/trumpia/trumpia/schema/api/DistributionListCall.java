package com.trumpia.trumpia.schema.api;

import java.io.IOException;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.trumpia.trumpia.model.TrumpiaAccountEntity;
import com.trumpia.trumpia.schema.model.DistributionList;
import com.trumpia.util.Http.HttpRequest;

public class DistributionListCall {
	DistributionList distributionList;	
	private String listURL;
	private String APIKey;
	public JSONArray listNames;
	
	public DistributionListCall(String APIKey, String user, String baseURL) {
		this.APIKey = APIKey;
		this.listURL = baseURL+"/rest/v1/"+ user +"/list";
	}
	
	public void extractListNames(String lists) throws JSONException {
		JSONObject listObject = new JSONObject(lists);
		JSONArray listArray = new JSONArray(listObject.get("list").toString());
		JSONObject list = new JSONObject();
		listNames = new JSONArray();
		for(int i=0; i<listArray.length(); i++) {
			list = (JSONObject) listArray.get(i);
			listNames.put(list.get("list_name"));
		}
	}
	
	public String getLists() throws IOException { // trumpia/list get api call
		HashMap<String, String> headers = new HashMap<String,String>();
		headers.put("content-type", "application/json");
		headers.put("x-apikey", APIKey);

		HttpRequest request = new HttpRequest.Builder()
				.URL(listURL)
				.headers(headers)
				.build();
		
		String msg = request.get();
		return msg;
	}
}
