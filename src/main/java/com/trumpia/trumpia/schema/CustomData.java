package com.trumpia.trumpia.schema;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.trumpia.trumpia.model.TrumpiaAccountEntity;
import com.trumpia.util.Http.HttpRequest;


public class CustomData {
	private String customDataUrl;
	public JSONArray customData;
	public String customID; //for test
	private String APIKey;
	

	public CustomData(String APIkey, String user, String baseURL) {
		this.customDataUrl= baseURL+"/rest/v1/"+ user +"/customdata";
		this.APIKey = APIkey;
	}
	
	public JSONArray getCustomdata() throws IOException, JSONException {
		
		splitID(extractID(getCustomList()));
		return customData;
	}

	public ArrayList<String> listCustomID() throws IOException, JSONException {
		return extractID(getCustomList());
	}

	private JSONObject getCustomList() throws IOException, JSONException { // get customdata list and return as JSONObject
		HashMap<String, String> headers = new HashMap<String, String>();
		
		headers.put("content-type", "application/json");
		headers.put("x-apikey", APIKey);
		HttpRequest request = new HttpRequest.Builder()
				.URL(customDataUrl)
				.headers(headers)
				.build();
		String customDataListString = request.get();
		JSONObject customDataList = new JSONObject(customDataListString);
		return customDataList;
	}

	private JSONObject getEachData(String customid) throws IOException, JSONException { // get each customdata with customdata id
		HashMap<String, String> headers = new HashMap<String, String>();
		
		headers.put("content-type", "application/json");
		headers.put("x-apikey", APIKey);
		
		HttpRequest request = new HttpRequest.Builder()
				.URL(customDataUrl+"/"+customid)
				.headers(headers)
				.build();
		String eachCustomdataString = request.get();
		JSONObject eachCustomdata = new JSONObject(eachCustomdataString);
		return eachCustomdata;
	}

	private ArrayList<String> extractID(JSONObject customdataList) throws JSONException { // get customdata list, and extract id, and put in ArrayList customidList 
		JSONArray array = (JSONArray)customdataList.get("customdata");
		ArrayList<String> customidList = new ArrayList<String>();
		for (int i = 0; i < array.length(); i++) {
			JSONObject temp = (JSONObject)array.get(i);
			customidList.add(temp.get("customdata_id").toString());
		}
		return customidList;
	}

	private void splitID(ArrayList<String> customidList) throws IOException, JSONException {
		JSONObject pageData = new JSONObject();
		JSONObject info = new JSONObject();
		JSONObject data = new JSONObject(); 
		JSONArray customArray = new JSONArray();
		for (int i=0; i<customidList.size(); i++) {
			pageData = getEachData(customidList.get(i));
			info.put("required", requiredMatch(pageData.get("required")));
			info.put("options", pageData.get("options"));
			info.put("input_type", inputTypeMatch(pageData.get("input_type")));
			data.put("info", info);
			data.put("customdata_id", pageData.get("customdata_id"));
			customID = pageData.get("customdata_id").toString();
			data.put("name", pageData.get("name"));
			customArray.put(data);
		}
		customData = customArray;
	}

	public String inputTypeMatch(Object inputType) {
		String stringType = null;
		int intType = (Integer)inputType;
		switch(intType) {
		case 1 : stringType = "Text Field";
		case 3 : stringType = "Radio Buttons";
		case 4 : stringType = "Drop-down List";
		case 5 : stringType = "Date & Time";
		case 6 : stringType = "Text Area";
		case 7 : stringType = "Number";
		}
		return stringType;
	}
	
	public String requiredMatch(Object required) {
		String stringRequired = null;
		int intRequired = (Integer)required;
		switch(intRequired) {
		case 1 : stringRequired = "Required";
		case 2 : stringRequired = "Optional";
		}
		return stringRequired;
	}

}
