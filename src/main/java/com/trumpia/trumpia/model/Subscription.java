package com.trumpia.trumpia.model;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.google.i18n.phonenumbers.NumberParseException;
import com.google.i18n.phonenumbers.Phonenumber.PhoneNumber;
import com.trumpia.util.PhoneNumberValidationUtils;

/*
 * DeleteSubscription toJSON
 * {
 * 	"deletedDynamicID" : id-some-thing
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
	
	public Subscription() {
		this.customField = new HashMap<String,String>();
	}
	public JSONObject toJSON() throws JSONException {
		if(isDeleted)
			return deletedSubscriptionToJSON();
		else
			return subscriptionToJSON();
	}
	
	private JSONObject deletedSubscriptionToJSON() {
		JSONObject subscription = new JSONObject();
		subscription.put("deletedDynamicID", id);
		return subscription;
	}
	
	private JSONObject subscriptionToJSON() {
		JSONObject subscription = new JSONObject();
		JSONArray customData = getCustomDataJSONArray();
		
		if(mobileNumber != null)
			getContactJSONobjectAndPut(subscription, "mobile", mobileNumber);
		if(landLine != null)
			getContactJSONobjectAndPut(subscription, "landline", landLine);
		if(email != null)
			subscription.put("email", email);
		if(firstName != null)
			subscription.put("first_name", firstName);
		if(lastName != null)
			subscription.put("last_name", lastName);
		
		subscription.put("customdata", customData);
		
		return subscription;
	}
	
	private void getContactJSONobjectAndPut(JSONObject subscription, String contactKey, String contactValue) {
		try {
			JSONObject contactInfo = new JSONObject();
			PhoneNumber contactNumber = PhoneNumberValidationUtils.parsingPhoneNumber(contactValue);
			contactInfo.put("number", contactNumber.getNationalNumber() + "");
			contactInfo.put("country_code", contactNumber.getCountryCode() + "");
			subscription.put(contactKey, contactInfo);
		} catch (NumberParseException e) {
			e.printStackTrace();
		}
	}

	private JSONArray getCustomDataJSONArray() {
		JSONArray jsonArray = new JSONArray();

		for(Map.Entry<String, String> entry : customField.entrySet())
			jsonArray.put(createCustomData(entry));

		return jsonArray;
	}

	private JSONObject createCustomData(Map.Entry<String, String> entry) {
		JSONObject custom = new JSONObject();
		custom.put("value", entry.getValue());
		custom.put("customdata_id", entry.getKey());
		return custom;
	}

	public boolean noContactInfo() {
		if(this.landLine==null && this.mobileNumber == null && this.email == null)
			return true;
		else
			return false;
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
	