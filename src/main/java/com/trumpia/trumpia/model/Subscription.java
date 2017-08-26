package com.trumpia.trumpia.model;

import org.json.JSONObject;
/*
{"mobile":
	{"number":"4156351895","country_code":"1"}
,"last_name":"last"
,"customdata":[]
,"first_name":"first"
,"email":"test@test13.com"
}
*/
public interface Subscription {
	public JSONObject toJSON();
	
	public boolean isDeleted();
	
	public String getFirstName();
	public String getLastName();
	public String getMobileNumber();
	public String getEmail();
	public String getLandLine();
	
	public void setFirstName(String firstName);
	public void setLastName(String lastName);
	public void setMobileNumber(String mobileNumber);
	public void setLandLine(String landLine);
	public void setEmail(String email);
}
