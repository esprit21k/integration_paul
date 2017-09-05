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
	public ArrayNode listNames;
	
	public DistributionListCall(String APIKey, String user, String baseURL) {
		this.APIKey = APIKey;
		this.listURL = baseURL+"/rest/v1/"+ user +"/list";
	}
	
	public void extractListNames(String lists) throws Exception {
		ObjectNode listObject = JSONUtils.getNewObjectNode();
		listObject = JSONUtils.stringToJSON(lists);
		ArrayNode listArray = JSONUtils.getNewArrayNode();
		listArray = (ArrayNode) listObject.get("list");
		ObjectNode list = JSONUtils.getNewObjectNode();
		listNames = JSONUtils.getNewArrayNode();
		for(int i=0; i<listArray.size(); i++) {
			list = JSONUtils.stringToJSON(listArray.get(i).toString());
			listNames.add(list.get("list_name"));
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
