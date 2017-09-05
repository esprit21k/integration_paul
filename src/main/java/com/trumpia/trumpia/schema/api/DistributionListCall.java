package com.trumpia.trumpia.schema.api;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.trumpia.trumpia.model.TrumpiaAccountEntity;
import com.trumpia.trumpia.schema.model.DistributionList;
import com.trumpia.util.Http.HttpRequest;
import com.trumpia.util.JSONUtils;

public class DistributionListCall {
	DistributionList distributionList;	
	private String listURL;
	private String APIKey;
	public ArrayList<String> listNames;
	
	public DistributionListCall(String APIKey, String user, String baseURL) {
		this.APIKey = APIKey;
		this.listURL = baseURL+"/rest/v1/"+ user +"/list";
	}
	
	public void extractListNames(String lists) throws Exception {
		ObjectNode listObject = JSONUtils.getNewObjectNode();
		listObject = JSONUtils.StringToJSON(lists);
//		System.out.println(listObject);
		ArrayNode listArray = JSONUtils.getNewArrayNode();
		listArray = (ArrayNode) listObject.get("list");
//		System.out.println(listArray);
		ObjectNode list = JSONUtils.getNewObjectNode();
//		listNames = JSONUtils.getNewArrayNode();
//		System.out.println("size: "+listArray.size());
		for(int i=0; i<listArray.size(); i++) {
			System.out.println(listArray.get(i).toString());
			list.get(listArray.get(i).toString());
		}
		System.out.println("listnames: "+listNames);
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
