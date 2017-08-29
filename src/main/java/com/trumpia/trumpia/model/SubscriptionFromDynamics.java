package com.trumpia.trumpia.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.google.i18n.phonenumbers.NumberParseException;
import com.google.i18n.phonenumbers.Phonenumber.PhoneNumber;
import com.trumpia.mapping.model.MappingEntity;
import com.trumpia.util.PhoneNumberValidationUtils;



public class SubscriptionFromDynamics implements Subscription {


	private boolean isDeleted = false;

	private String mobileNumber = null;
	private String landLine;
	private String firstName;
	private String lastName;
	private String email = null;
	private String id;
	private HashMap<String, String> customField; // HashMap<customData_ID, customData_Value>

	public SubscriptionFromDynamics(JSONObject input, List<MappingEntity> schema) {
		customField = new HashMap<String, String>();
		id = input.get("id").toString();

		if(isDeletedSubscription(input)) 
			isDeleted = true;
		else
			parsingJSONBasedOnSchema(input, schema);  
	}

	private boolean isDeletedSubscription(JSONObject input) {
		return input.has("reason");
	}

	private void parsingJSONBasedOnSchema(JSONObject input, List<MappingEntity> schema) {
		for(MappingEntity column : schema) {
			parsingColumn(input, column);
		}
	}

	private void parsingColumn(JSONObject input, MappingEntity column) {
		if(isCustomData(column.getTrumpiaFieldName()))
			putCustomDataIntoCustomField(column.getCustomDataId(), (String)input.remove(column.getDynamicFieldName()));
		else
			putData(column.getTrumpiaFieldName(), (String)input.remove(column.getDynamicFieldName()));
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
		customField.put(customDataID, customDataValue);
	}

	private void putData(String dataName, String value) {
		if(dataName.equals("first_name"))
			this.firstName = value;
		if(dataName.equals("last_name"))
			this.lastName = value;
		if(dataName.equals("mobile"))
			this.mobileNumber = value;
		if(dataName.equals("landline"))
			this.landLine = value;
		if(dataName.equals("email"))
			this.email = value;
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

		if(mobileNumber != null) {
			getContactJSONobjectAndPut(subscription, "mobile", mobileNumber);
		}
		if(landLine != null) {
			getContactJSONobjectAndPut(subscription, "landLine", landLine);
		}
		if(email != null)
			subscription.put("email", email);
		if(firstName != null)
			subscription.put("first_name", firstName);

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

		for(Map.Entry<String, String> entry : customField.entrySet()) {
			JSONObject tmp = new JSONObject();
			tmp.put("value", entry.getValue());
			tmp.put("customdata_id", entry.getKey());
			jsonArray.put(tmp);
		}
		return jsonArray;
	}

	public String toString() {
		return this.toJSON().toString();
	}

	public String getId() {
		return id;
	}

	public String getFirstName() {
		return firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public String getMobileNumber() {
		return mobileNumber;
	}
	public String getEmail() {
		return email;
	}
	public String getLandLine() {
		return landLine;
	}
	public boolean isDeleted() {
		return isDeleted;
	}
	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}

	public void setLandLine(String landLine) {
		this.landLine = landLine;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

}