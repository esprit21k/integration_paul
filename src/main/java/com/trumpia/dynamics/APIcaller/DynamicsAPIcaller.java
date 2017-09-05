package com.trumpia.dynamics.APIcaller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.io.JsonEOFException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.trumpia.util.Subscription;
import com.trumpia.util.Http.HttpRequest;

public class DynamicsAPIcaller {
	private String accessToken;
	private String initializeURL;
	private String deltaKeyURL;
	private ObjectMapper mapper = new ObjectMapper();
	JsonFactory factory = mapper.getFactory();

	private ArrayList<Subscription> changedSubscription = new ArrayList<Subscription>();
	
	public DynamicsAPIcaller(String accessToken, String resourseURL) {
		this.accessToken = accessToken;
		this.initializeURL = resourseURL+"/api/data/v8.2/contacts?&$select=firstname,lastname,mobilephone";
	}

	public ArrayList<Subscription> getContactChange() {
		try {
			System.out.println(accessToken);
			if(deltaKeyURL == null)
				getDeltaKey();
			else
				retrieveChangedData();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return changedSubscription;	
		
	} 

	private void getDeltaKey() throws IOException {
		HashMap<String, String> headers = new HashMap<String, String>();
		headers.put("Authorization", accessToken);
		headers.put("OData-MaxVersion", "4.0");
		headers.put("OData-Version", "4.0");
		headers.put("Prefer", "odata.track-changes");		

		HttpRequest request = new HttpRequest.Builder()
				.URL(initializeURL)
				.headers(headers)
				.build();
		
		ObjectNode objectNode;
		JsonParser jp = factory.createParser(request.get());
		
		objectNode = mapper.readTree(jp);
		updateDeltaKey(objectNode.get("@odata.deltaLink").toString());
		return;
	}
	
	private void retrieveChangedData() throws IOException{
		JsonParser jp = factory.createParser(sendSearchRequest());
		ObjectNode json = mapper.readTree(jp);
		JsonParser value = factory.createParser(json.get("value").toString());
		ArrayNode valueArray = mapper.readTree(value);

		updateDeltaKey(json.get("@odata.deltaLink").toString());
		getChangedData(valueArray);
		return;
	}
	private String sendSearchRequest() throws IOException {
		HashMap<String, String> headers = new HashMap<String, String>();
		headers.put("Authorization", accessToken);
		headers.put("OData-MaxVersion", "4.0");
		headers.put("OData-Version", "4.0");

		HttpRequest request = new HttpRequest.Builder()
				.URL(deltaKeyURL)
				.headers(headers)
				.build();
		System.out.println("hi: "+request.get());
		return request.get();
	}
	
	private void updateDeltaKey(String key) {
		deltaKeyURL = key;
	}
	
	private void getChangedData(ArrayNode arr) {
		for(Object tmp : arr) {
			changedSubscription.add(new Subscription((ObjectNode)tmp));
		}
	}
	public ArrayList<Subscription> getChangedSubscription() {
		return changedSubscription;
	}
}
