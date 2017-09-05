package com.trumpia.trumpia.schema.api;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.trumpia.trumpia.model.TrumpiaAccountEntity;
import com.trumpia.trumpia.schema.model.CustomData;
import com.trumpia.util.JSONUtils;
import com.trumpia.util.Http.HttpRequest;


public class CustomDataCall {
	private String customDataURL;
	private String APIKey;
	public ArrayNode customDataArray;
	public String customID;
	
	public CustomDataCall(String APIkey, String user, String baseURL) {
		this.customDataURL= baseURL+"/rest/v1/"+ user +"/customdata";
		this.APIKey = APIkey;
	}
	
	public void splitID(ArrayList<String> customidList) throws Exception {
		ObjectNode pageData = JSONUtils.getNewObjectNode();
		ObjectNode info = JSONUtils.getNewObjectNode();
		ObjectNode data = JSONUtils.getNewObjectNode();
		ArrayNode customArray = JSONUtils.getNewArrayNode();
		for (int i=0; i<customidList.size(); i++) {
			pageData = getCustomdataDetail(customidList.get(i));
			info.set("options", pageData.get("options"));
			info.put("input_type", inputTypeMatch(pageData.get("input_type").asInt()));
			info.put("required", requiredMatch(pageData.get("required").asInt()));
			data.set("name", pageData.get("name"));
			data.put("customdata_id", pageData.get("customdata_id").asText());
			data.set("info", info);
			customArray.add(data);
			customID = pageData.get("customdata_id").toString();
		}
		customDataArray = customArray;
	}
	
	public ArrayList<String> listCustomID() throws Exception {
		return extractID(getCustomList());
	}

	private ArrayList<String> extractID(ObjectNode customdataList) { // get customdata list, and extract id, and put in ArrayList customidList 
		ArrayNode array = (ArrayNode)customdataList.get("customdata");
		ArrayList<String> customidList = new ArrayList<String>();
		for (int i = 0; i < array.size(); i++) {
			ObjectNode temp = (ObjectNode)array.get(i);
			customidList.add(temp.get("customdata_id").toString());
		}
		return customidList;
	}
	
	public ObjectNode getCustomList() throws Exception { // get customdata list and return as JSONObject
		HashMap<String, String> headers = new HashMap<String, String>();
		
		headers.put("content-type", "application/json");
		headers.put("x-apikey", APIKey);
		HttpRequest request = new HttpRequest.Builder()
				.URL(customDataURL)
				.headers(headers)
				.build();
		String customDataListString = request.get();
		ObjectNode customDataList = JSONUtils.getNewObjectNode();
		customDataList = JSONUtils.stringToJSON(customDataListString);
		return customDataList;
	}

	public ObjectNode getCustomdataDetail(String customid) throws Exception{ // get customdata detail with customdata id 
		HashMap<String, String> headers = new HashMap<String, String>();
		
		headers.put("content-type", "application/json");
		headers.put("x-apikey", APIKey);
		
		HttpRequest request = new HttpRequest.Builder()
				.URL(customDataURL+"/"+customid)
				.headers(headers)
				.build();
		String customdataString = request.get();
		ObjectNode customData = JSONUtils.getNewObjectNode();
		customData = JSONUtils.stringToJSON(customdataString);
		return customData;
	}

	public String inputTypeMatch(Object inputType) { //바로 return
		int intType = (Integer)inputType;
		switch(intType) {
		case 1 : return "Text Field";
		case 3 : return "Radio Buttons";
		case 4 : return "Drop-down List";
		case 5 : return "Date & Time";
		case 6 : return "Text Area";
		case 7 : return "Number";
		}
		return "Unknown type";
	}
	
	public String requiredMatch(Object required) {
		int intRequired = (Integer)required;
		switch(intRequired) {
		case 1 : return "Required";
		case 2 : return "Optional";
		}
		return null;
	}

}
