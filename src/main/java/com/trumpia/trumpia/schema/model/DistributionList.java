package com.trumpia.trumpia.schema.model;

import java.io.IOException;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;

import com.trumpia.trumpia.schema.api.DistributionListCall;
import com.trumpia.util.Http.HttpRequest;

public class DistributionList {
	private DistributionListCall distributionListCall;
	private JSONArray listNames;
	
	public DistributionList(String APIKey, String user, String baseURL) {
		this.distributionListCall = new DistributionListCall(APIKey, user, baseURL);
	}
	
	public JSONArray getDistributionLists() throws JSONException, IOException {
		distributionListCall.extractListNames(distributionListCall.getLists());
		listNames = distributionListCall.listNames;
		return listNames;
	}


}
