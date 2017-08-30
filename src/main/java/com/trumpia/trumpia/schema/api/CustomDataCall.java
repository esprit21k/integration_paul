package com.trumpia.trumpia.schema.api;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.trumpia.trumpia.model.TrumpiaAccountEntity;
import com.trumpia.trumpia.schema.model.CustomData;
import com.trumpia.util.Http.HttpRequest;


public class CustomDataCall {
	private String customDataURL;
	private String APIKey;
	public JSONArray customDataArray;
	public String customID;
	
	public CustomDataCall(String APIkey, String user, String baseURL) {
		this.customDataURL= baseURL+"/rest/v1/"+ user +"/customdata";
		this.APIKey = APIkey;
	}
	
	public void splitID(ArrayList<String> customidList) throws IOException, JSONException {
		JSONObject pageData = new JSONObject();
		JSONObject info = new JSONObject();
		JSONObject data = new JSONObject(); 
		JSONArray customArray = new JSONArray();
		for (int i=0; i<customidList.size(); i++) {
			pageData = getCustomdataDetail(customidList.get(i));
			info.put("required", requiredMatch(pageData.get("required")));
			info.put("options", pageData.get("options"));
			info.put("input_type", inputTypeMatch(pageData.get("input_type")));
			data.put("info", info);
			data.put("customdata_id", pageData.get("customdata_id"));
			customID = pageData.get("customdata_id").toString();
			data.put("name", pageData.get("name"));
			customArray.put(data);
		}
		customDataArray = customArray;
	}
	
	public ArrayList<String> listCustomID() throws IOException, JSONException {
		return extractID(getCustomList());
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
	
	public JSONObject getCustomList() throws IOException, JSONException { // get customdata list and return as JSONObject
		HashMap<String, String> headers = new HashMap<String, String>();
		
		headers.put("content-type", "application/json");
		headers.put("x-apikey", APIKey);
		HttpRequest request = new HttpRequest.Builder()
				.URL(customDataURL)
				.headers(headers)
				.build();
		String customDataListString = request.get();
		JSONObject customDataList = new JSONObject(customDataListString);
		return customDataList;
	}

	public JSONObject getCustomdataDetail(String customid) throws IOException, JSONException { // get customdata detail with customdata id 
		HashMap<String, String> headers = new HashMap<String, String>();
		
		headers.put("content-type", "application/json");
		headers.put("x-apikey", APIKey);
		
		HttpRequest request = new HttpRequest.Builder()
				.URL(customDataURL+"/"+customid)
				.headers(headers)
				.build();
		String customdataString = request.get();
		JSONObject customdata = new JSONObject(customdataString);
		return customdata;
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
