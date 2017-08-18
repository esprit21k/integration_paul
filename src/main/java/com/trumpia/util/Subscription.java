package com.trumpia.util;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;



public class Subscription {
		
	
	private String mobileNumber = null;
	private String landLine;
	private String firstName;
	private String lastName;
	private String email = null;
	private ArrayList<CustomField> customField;

	public Subscription(JSONObject jsonObject) {

		System.out.println(jsonObject.toString());
		this.lastName = (String)jsonObject.remove("lastname");
		
		this.customField = new ArrayList<CustomField>();
		
		if(!jsonObject.isNull("firstname"))
			this.firstName = (String)jsonObject.remove("firstnmae");
		if(!jsonObject.isNull("mobilephone"))
			this.mobileNumber = (String)jsonObject.remove("mobilephone");
		if(!jsonObject.isNull("telephone1"))
			this.landLine = (String)jsonObject.remove("telephone1");
		if(!jsonObject.isNull("emailaddress1"))
			this.email = (String)jsonObject.remove("emailaddress1");
		
		String[] keys = JSONObject.getNames(jsonObject);
		System.out.println(keys);

		for(String key : keys) {
			if(jsonObject.isNull(key)) {jsonObject.remove(key); break;}
			CustomField customData = new CustomField(key, jsonObject.get(key).toString());
			customField.add(customData);
		}
	}

	public JSONObject toJSON() throws JSONException {
		JSONObject lastObject = new JSONObject();
		JSONObject subscriptionInfo = new JSONObject();
		
		JSONArray customDataInfo = new JSONArray();

		/*for(int i = 0 ; i < customField.size(); i++) {
			CustomField customData = customField.get(i);			
			if(CustomDataInfoHolder.customDataField.get(customData.getKey()) == null) continue;
			JSONObject tmp = new JSONObject();
			tmp.put("value", customData.getValue());
			tmp.put("customdata_id", CustomDataInfoHolder.customDataField.get(customData.getKey()));
			customDataInfo.put(tmp);
		}*/

		if(mobileNumber != null) {
			JSONObject mobileInfo = new JSONObject();
			mobileInfo.put("number", mobileNumber);
			mobileInfo.put("country_code", "1");
			subscriptionInfo.put("mobile", mobileInfo);
		}
		if(landLine != null) {
			JSONObject landLineInfo = new JSONObject();
			landLineInfo.put("number", mobileNumber);
			landLineInfo.put("country_code", "1");
			subscriptionInfo.put("landLine", landLineInfo);
		}
		if(email != null)
			subscriptionInfo.put("email", email);

		subscriptionInfo.put("first_name", firstName);
		subscriptionInfo.put("last_name", lastName);
		subscriptionInfo.put("customdata", customDataInfo);

		lastObject.put("list_name", "APIMobile"); //List name can modified
		lastObject.append("subscriptions", subscriptionInfo);
	
		return lastObject; //{"subscriptions":[{"mobile":{"number":"4156351895","country_code":"1"},"last_name":"last","customdata":[],"first_name":"first","email":"test@test13.com"}],"list_name":"APIMobile"}
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

}

class CustomField{
	private String key;
	private String value;

	public CustomField(String key, String value) {
		this.key = key;
		this.value = value;
	}

	public String getKey() {
		return key;
	}	

	public String getValue() {
		return value;
	}
} 
