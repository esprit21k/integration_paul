package com.trumpia.trumpia.schema;

import java.io.IOException;

import org.json.JSONException;
import org.json.JSONObject;

import com.trumpia.trumpia.schema.model.CustomData;
import com.trumpia.trumpia.schema.model.DistributionList;

public class ContactSchema {
	JSONObject trumpiaSchema;
	DistributionList lists;
	CustomData custom;
	String APIKey;
	String user;
	String baseURL;
	
	public ContactSchema(String APIkey, String user, String baseURL) {
		this.APIKey = APIkey;
		this.user = user;
		this.baseURL = baseURL;
		trumpiaSchema = new JSONObject();
		lists = new DistributionList(APIkey, user, baseURL);
		custom = new CustomData(APIkey, user, baseURL);
	}
	
	public JSONObject getContactSchema() throws JSONException, IOException {
		trumpiaSchema.put("list_name", lists.getDistributionLists());
		trumpiaSchema.put("customdata", custom.getCustomdata());
		return trumpiaSchema;
	}
}
