package com.trumpia.account.schema;

import java.io.IOException;

import org.json.JSONException;
import org.json.JSONObject;

public class ContactSchema {
	JSONObject trumpiaSchema;
	DistributionList lists;
	CustomData custom;
	String aPIKey;
	String user;
	
	public ContactSchema(String aPIKey, String user) {
		this.aPIKey = aPIKey;
		this.user = user;
		trumpiaSchema = new JSONObject();
		lists = new DistributionList(aPIKey, user);
		custom = new CustomData(aPIKey, user);
	}
	
	public JSONObject getContactSchema() throws JSONException, IOException {
		trumpiaSchema.put("list_name", lists.getDistributionLists());
		trumpiaSchema.put("customdata", custom.getCustomdata());
		return trumpiaSchema;
	}
}
