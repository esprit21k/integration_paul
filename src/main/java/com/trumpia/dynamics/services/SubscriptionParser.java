package com.trumpia.dynamics.services;

import java.util.List;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.trumpia.mapping.model.MappingEntity;
import com.trumpia.trumpia.model.Subscription;
import com.trumpia.util.JSONUtils;

public class SubscriptionParser {
	ObjectNode input = JSONUtils.getNewObjectNode();
	List<MappingEntity> schema;
	Subscription subs;

	public SubscriptionParser(ObjectNode input, List<MappingEntity> schema) {
		this.input = input;
		this.schema = schema;
		this.subs = new Subscription();

		parsing();
		System.out.println("subs: "+subs.toString());
	}

	private void parsing() {	
		if(isDeletedSubscription()) {
			subs.setId(input.get("id").asText()); 
			subs.setDeleted(true);
		}
		else {
			subs.setId(input.get("contactid").asText());
			parsingInputBasedOnSchema();
		}
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
			putCustomDataIntoCustomField(column.getCustomDataId(), input.remove(column.getTargetFieldName()).asText());
		else
			putData(column.getTrumpiaFieldName(), input.remove(column.getTargetFieldName()).asText());
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

	public Subscription getParsedSubscription() {
		return subs;
	}	
}
