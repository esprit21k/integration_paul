package com.trumpia.trumpia.schema;

import java.io.IOException;

import org.json.JSONException;
import org.json.JSONObject;

import com.trumpia.trumpia.schema.model.CustomData;
import com.trumpia.trumpia.schema.model.DistributionList;
import com.trumpia.util.JSONUtils;

public class ContactSchema {
	ObjectNode trumpiaSchema;
	DistributionList lists;
	CustomData custom;
	String APIKey;
	String user;
	String baseURL;
	
	public ContactSchema(String APIkey, String user, String baseURL) {
		this.APIKey = APIkey;
		this.user = user;
		this.baseURL = baseURL;
		trumpiaSchema = JSONUtils.getNewObjectNode();
		lists = new DistributionList(APIkey, user, baseURL);
		custom = new CustomData(APIkey, user, baseURL);
	}
	
	public ObjectNode getContactSchema() throws Exception {
		trumpiaSchema.set("list_name", lists.getDistributionLists());
		trumpiaSchema.set("customdata", custom.getCustomdata());
		return trumpiaSchema;
	}
}
