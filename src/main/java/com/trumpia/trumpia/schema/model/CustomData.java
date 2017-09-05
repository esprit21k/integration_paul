package com.trumpia.trumpia.schema.model;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.fasterxml.jackson.databind.node.ArrayNode;
import com.trumpia.trumpia.schema.api.CustomDataCall;
import com.trumpia.util.JSONUtils;
import com.trumpia.util.Http.HttpRequest;

public class CustomData {
	public CustomDataCall customDataCall;
	private ArrayNode customDataArray;
	
	public CustomData(String APIkey, String user, String baseURL) {
		this.customDataCall = new CustomDataCall(APIkey, user, baseURL);
	}
	
	public ArrayNode getCustomdata() throws Exception {
		customDataCall.splitID(customDataCall.listCustomID());
		customDataArray = JSONUtils.getNewArrayNode();
		customDataArray = customDataCall.customDataArray; 
		return customDataArray;
	}
}
