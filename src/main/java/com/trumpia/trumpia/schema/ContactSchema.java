package com.trumpia.trumpia.schema;

import java.io.IOException;

import org.json.JSONException;
import org.json.JSONObject;

public class ContactSchema {
	JSONObject trumpiaSchema;
	DistributionList lists;
	CustomData custom;
	String APIKey;
	String user;
	String baseURL;
	
	public ContactSchema(String APIKey, String user, String baseURL) {
		this.APIKey = APIKey;
		this.user = user;
		this.baseURL = baseURL;
		trumpiaSchema = new JSONObject();
		lists = new DistributionList(APIKey, user, baseURL);
		custom = new CustomData(APIKey, user, baseURL);
	}
	
	public JSONObject getContactSchema() throws JSONException, IOException {
		trumpiaSchema.put("list_name", lists.getDistributionLists());
		trumpiaSchema.put("customdata", custom.getCustomdata());
		return trumpiaSchema;
	}
}
