package com.trumpia.trumpia.schema;

import java.io.IOException;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.trumpia.trumpia.model.TrumpiaAccountEntity;
import com.trumpia.util.Http.HttpRequest;

public class DistributionList {
	private String listUrl;
	private String APIKey;
	
	public DistributionList(String APIkey, String user, String baseURL) {
		this.listUrl= baseURL+"/rest/v1/"+ user +"/list";
		this.APIKey = APIkey;
	}
	
	public JSONArray getDistributionLists() throws JSONException, IOException {
		return extractListNames(getLists()); 
	}
	
	private String getLists() throws IOException { // trumpia/list get api call
		HashMap<String, String> headers = new HashMap<String,String>();
		headers.put("content-type", "application/json");
		headers.put("x-apikey", APIKey);

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
