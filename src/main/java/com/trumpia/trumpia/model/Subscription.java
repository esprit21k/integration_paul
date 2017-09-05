package com.trumpia.trumpia.model;

import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.google.i18n.phonenumbers.NumberParseException;
import com.google.i18n.phonenumbers.Phonenumber.PhoneNumber;
import com.trumpia.util.JSONUtils;
import com.trumpia.util.PhoneNumberValidationUtils;

/*
 * DeleteSubscription toJSON
 * {
 *  "deletedDynamicID" : id-some-thing
 * }
 * 
 * Subscription toJSON
 * 
 * */

public class Subscription {
	private boolean isDeleted = false;
	private String mobileNumber = null;
	private String landLine;
	private String firstName;
	private String lastName;
	private String email = null;
	private String id;
	private HashMap<String, String> customField; // HashMap<customData_ID, customData_Value>
	
	public ObjectNode toJSON() {
		if(isDeleted)
			return deletedSubscriptionToJSON();
		else
			return subscriptionToJSON();
	}

	private ObjectNode deletedSubscriptionToJSON() {
		ObjectNode subscription = JSONUtils.getNewObjectNode();
		subscription.put("deletedDynamicID", id);
		return subscription;
	}

	private ObjectNode subscriptionToJSON() {
		ObjectNode subscription = JSONUtils.getNewObjectNode();
		ArrayNode customData = JSONUtils.getNewArrayNode();

		if(mobileNumber != null)
			getContactJSONobjectAndPut(subscription, "mobile", mobileNumber);
		if(landLine != null)
			getContactJSONobjectAndPut(subscription, "landLine", landLine);
		if(email != null)
			subscription.put("email", email);
		if(firstName != null)
			subscription.put("first_name", firstName);
		if(lastName != null)
			subscription.put("last_name", lastName);

		subscription.set("customdata", customData);

		return subscription;
	}

	private void getContactJSONobjectAndPut(ObjectNode subscription, String contactKey, String contactValue) {
		try {
			ObjectNode contactInfo = JSONUtils.getNewObjectNode();
			PhoneNumber contactNumber = PhoneNumberValidationUtils.parsingPhoneNumber(contactValue);
			contactInfo.put("number", contactNumber.getNationalNumber() + "");
			contactInfo.put("country_code", contactNumber.getCountryCode() + "");
			subscription.set(contactKey, contactInfo);
		} catch (NumberParseException e) {
			e.printStackTrace();
		}
	}

	private ArrayNode getCustomDataJSONArray() {
		ArrayNode jsonArray = JSONUtils.getNewArrayNode();

		for(Map.Entry<String, String> entry : customField.entrySet())
			jsonArray.add(createCustomData(entry));

		return jsonArray;
	}

	private ObjectNode createCustomData(Map.Entry<String, String> entry) {
		ObjectNode custom = JSONUtils.getNewObjectNode();
		custom.put("value", entry.getValue());
		custom.put("customdata_id", entry.getKey());
		return custom;
	}

	public boolean isDeleted() {
		return isDeleted;
	}
	public String getMobileNumber() {
		return mobileNumber;
	}
	public String getLandLine() {
		return landLine;
	}
	public String getFirstName() {
		return firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public String getEmail() {
		return email;
	}
	public String getId() {
		return id;
	}
	public HashMap<String, String> getCustomField() {
		return customField;
	}
	public void setDeleted(boolean isDeleted) {
		this.isDeleted = isDeleted;
	}
	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}
	public void setLandLine(String landLine) {
		this.landLine = landLine;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public void setId(String id) {
		this.id = id;
	}
	public void setCustomField(HashMap<String, String> customField) {
		this.customField = customField;
	}

}