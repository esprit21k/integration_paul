package com.trumpia.trumpia.model;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.google.i18n.phonenumbers.NumberParseException;
import com.google.i18n.phonenumbers.Phonenumber.PhoneNumber;
import com.trumpia.util.PhoneNumberValidationUtils;



public class SubscriptionFromDynamics implements Subscription {


	private String mobileNumber = null;
	private String landLine;
	private String firstName;
	private String lastName;
	private String email = null;
	private HashMap<String, String> customField; // HashMap<customData_name, customData_Value>
	private HashMap<String, String> customDataSchema; // HashMap<customData_name, customDataId>

	public SubscriptionFromDynamics(JSONObject jsonObject, HashMap<String, String> customDataSchema) {

		this.customDataSchema = customDataSchema;
		this.lastName = (String)jsonObject.remove("lastname");

		if(!jsonObject.isNull("firstname"))
			this.firstName = (String)jsonObject.remove("firstnmae");
		if(!jsonObject.isNull("mobilephone"))
			this.mobileNumber = (String)jsonObject.remove("mobilephone");
		if(!jsonObject.isNull("telephone1"))
			this.landLine = (String)jsonObject.remove("telephone1");
		if(!jsonObject.isNull("emailaddress1"))
			this.email = (String)jsonObject.remove("emailaddress1");

		getCustomField(jsonObject, JSONObject.getNames(jsonObject));
	}

	private void getCustomField(JSONObject jsonObject, String[] keys) {
		for(String key: keys) {
			if(jsonObject.isNull(key)) {
				jsonObject.remove(key);
				break;
			}
			customField.put(key, jsonObject.get(key).toString());
		}
	}

	public JSONObject toJSON() throws JSONException {
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
			subscription.put(contactKey, contactValue);
		} catch (NumberParseException e) {
			e.printStackTrace();
		}
	}

	private JSONArray getCustomDataJSONArray() {
		JSONArray jsonArray = new JSONArray();

		for(Map.Entry<String, String> entry : customField.entrySet()) {
			JSONObject tmp = new JSONObject();
			tmp.put("value", entry.getValue());
			tmp.put("customdata_id", customDataSchema.get(entry.getKey()));
			jsonArray.put(tmp);
		}
		return jsonArray;
	}

	public String toString() {
		return this.toJSON().toString();
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

	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}

	public void setLandLine(String landLine) {
		this.landLine = landLine;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	@Override
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	@Override
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

}
