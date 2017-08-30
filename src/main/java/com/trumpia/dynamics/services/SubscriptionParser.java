package com.trumpia.dynamics.services;

import java.util.List;

import org.json.JSONObject;

import com.trumpia.mapping.model.MappingEntity;
import com.trumpia.trumpia.model.Subscription;

public class SubscriptionParser {
	JSONObject input;
	List<MappingEntity> schema;
	Subscription subs;
	
	public SubscriptionParser(JSONObject input, List<MappingEntity> schema) {
		this.input = input;
		this.schema = schema;
		this.subs = new Subscription();
		
		parsing();
	}
	
	private void parsing() {
		subs.setId(input.get("id").toString()); 

		if(isDeletedSubscription()) 
			subs.setDeleted(true);
		else
			parsingInputBasedOnSchema();
	}
	
	private boolean isDeletedSubscription() {
		return input.has("reason");
	}
	
	private void parsingInputBasedOnSchema() {
		for(MappingEntity column : schema) {
			parsingColumn(column);
		}
	}
	
	private void parsingColumn(MappingEntity column) {
		if(isCustomData(column.getTrumpiaFieldName()))
			putCustomDataIntoCustomField(column.getCustomDataId(), (String)input.remove(column.getTargetFieldName()));
		else
			putData(column.getTrumpiaFieldName(), (String)input.remove(column.getTargetFieldName()));
	}
	
	private boolean isCustomData(String trumpiaFieldName) {
		if(trumpiaFieldName.equals("first_name")
				|trumpiaFieldName.equals("last_name")
				|trumpiaFieldName.equals("mobile")
				|trumpiaFieldName.equals("landline")
				|trumpiaFieldName.equals("email"))
			return false;
		else
			return true;
	}
	
	private void putCustomDataIntoCustomField(String customDataID, String customDataValue) {
		subs.getCustomField().put(customDataID, customDataValue);
	}
	
	private void putData(String dataName, String value) {
		if(dataName.equals("first_name"))
			subs.setFirstName(value);
		if(dataName.equals("last_name"))
			subs.setLastName(value);
		if(dataName.equals("mobile"))
			subs.setMobileNumber(value);
		if(dataName.equals("landline"))
			subs.setLandLine(value);
		if(dataName.equals("email"))
			subs.setEmail(value);
	}
	
	public Subscription getPasredSubscription() {
		return subs;
	}
	
}
