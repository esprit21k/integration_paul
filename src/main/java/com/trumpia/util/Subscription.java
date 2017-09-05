package com.trumpia.util;

import java.util.ArrayList;
import java.util.Iterator;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

public class Subscription {
		
	
	private String mobileNumber = null;
	private String landLine;
	private String firstName;
	private String lastName;
	private String email = null;
	private ArrayList<CustomField> customField;

	public Subscription(ObjectNode jsonObject) {

		System.out.println(jsonObject.toString()); 
		this.lastName = jsonObject.get("lastname").toString();
		jsonObject.remove("lastname");
		
		
		this.customField = new ArrayList<CustomField>();
		
		if(jsonObject.has("firstname")) {
			this.lastName = jsonObject.get("firstname").toString();
			jsonObject.remove("firstname");
		}
		if(jsonObject.has("mobilephone")) {
			this.lastName = jsonObject.get("mobilephone").toString();
			jsonObject.remove("mobilephone");
		}
		if(jsonObject.has("telephone1")) {
			this.lastName = jsonObject.get("telephone1").toString();
			jsonObject.remove("telephone1");
		}
		if(jsonObject.has("emailaddress1")) {
			this.lastName = jsonObject.get("emailaddress1").toString();
			jsonObject.remove("emailaddress1");
		}
		
		Iterator<String> keys = jsonObject.fieldNames();
//		String[] keys = JSONObject.getNames(jsonObject);
		System.out.println(keys);

		while(keys.hasNext()) {
			String key = keys.next();
			CustomField customData = new CustomField(key, jsonObject.get(key).toString());
			customField.add(customData);
		}
	}

	public ObjectNode toJSON() {
		ObjectMapper mapper = new ObjectMapper();
		ObjectNode lastObject = mapper.createObjectNode();
		ObjectNode subscriptionInfo = mapper.createObjectNode();
//		ArrayNode customDataInfo = mapper.createArrayNode();

		/*for(int i = 0 ; i < customField.size(); i++) {
			CustomField customData = customField.get(i);			
			if(CustomDataInfoHolder.customDataField.get(customData.getKey()) == null) continue;
			JSONObject tmp = new JSONObject();
			tmp.put("value", customData.getValue());
			tmp.put("customdata_id", CustomDataInfoHolder.customDataField.get(customData.getKey()));
			customDataInfo.put(tmp);
		}*/

		if(mobileNumber != null) {
			ObjectNode mobileInfo = mapper.createObjectNode();
			mobileInfo.put("number", mobileNumber);
			mobileInfo.put("country_code", "1");
			subscriptionInfo.set("mobile", mobileInfo);
		}
		if(landLine != null) {
			ObjectNode landLineInfo = mapper.createObjectNode();
			landLineInfo.put("number", mobileNumber);
			landLineInfo.put("country_code", "1");
			subscriptionInfo.set("landLine", landLineInfo);
		}
		if(email != null)
			subscriptionInfo.put("email", email);

		subscriptionInfo.put("first_name", firstName);
		subscriptionInfo.put("last_name", lastName);
//		subscriptionInfo.set("customdata", customDataInfo);

		lastObject.put("list_name", "APIMobile"); //List name can modified
		lastObject.set("subscriptions", subscriptionInfo);
	
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
