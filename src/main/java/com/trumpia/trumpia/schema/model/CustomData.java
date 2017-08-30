package com.trumpia.trumpia.schema.model;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.trumpia.trumpia.schema.api.CustomDataCall;
import com.trumpia.util.Http.HttpRequest;

public class CustomData {
	public CustomDataCall customDataCall;
	private JSONArray customDataArray;
	
	public CustomData(String APIkey, String user, String baseURL) {
		this.customDataCall = new CustomDataCall(APIkey, user, baseURL);
	}
	
	public JSONArray getCustomdata() throws IOException, JSONException {
		customDataCall.splitID(customDataCall.listCustomID());
		customDataArray = customDataCall.customDataArray;
		return customDataArray;
	}
}
