package com.trumpia.account.schema;

import java.io.IOException;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.trumpia.util.Http.HttpRequest;

public class DistributionList {
	private String listUrl;
	private String aPIKey;
	
	public DistributionList(String aPIKey, String user) {
		this.listUrl= "http://api.trumpia.com/rest/v1/"+ user +"/list";
		this.aPIKey = aPIKey;
	}
	
	public JSONArray getDistributionLists() throws JSONException, IOException {
		return extractListNames(getLists()); 
	}
	
	private String getLists() throws IOException { // trumpia/list get api call
		HashMap<String, String> headers = new HashMap<String,String>();
		headers.put("content-type", "application/json");
		headers.put("x-apikey", aPIKey);

		HttpRequest request = new HttpRequest.Builder()
				.URL(listUrl)
				.headers(headers)
				.build();
		
		String msg = request.get();
		return msg;
	}
	
	private JSONArray extractListNames(String lists) throws JSONException {
		JSONObject listObject = new JSONObject(lists);
		JSONArray listArray = new JSONArray(listObject.get("list").toString());
		JSONObject list = new JSONObject();
		JSONArray listNames = new JSONArray();
		for(int i=0; i<listArray.length(); i++) {
			list = (JSONObject) listArray.get(i);
			listNames.put(list.get("list_name"));
		}
		return listNames;
	}
}
