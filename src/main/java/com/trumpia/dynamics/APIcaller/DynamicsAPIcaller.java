package com.trumpia.dynamics.APIcaller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.trumpia.dynamics.data.DynamicsAccountRepository;
import com.trumpia.dynamics.model.DynamicsAccountEntity;
import com.trumpia.dynamics.services.SubscriptionParser;
import com.trumpia.mapping.model.MappingEntity;
import com.trumpia.trumpia.model.Subscription;
import com.trumpia.util.JSONUtils;
import com.trumpia.util.Http.HttpRequest;

public class DynamicsAPIcaller {

	
	private DynamicsAccountEntity dynamicsAccount;
	private String accessToken;
	private String initializeUrl;
	private String resourceUrl;
	private String deltaToken;
	private String field = "contact";

	private List<Subscription> changedSubscription;
	private List<MappingEntity> schema;

	public DynamicsAPIcaller(DynamicsAccountEntity dynamicsAccount, String field, List<MappingEntity> schema) {
		this.accessToken = dynamicsAccount.getAccessToken();
		this.resourceUrl = dynamicsAccount.getResourceUrl();
		this.field = field;
		this.initializeUrl = resourceUrl+"/api/data/v8.2/"+field+"s?$select="+field+"id";
		this.deltaToken = null;
		this.schema = schema;
	}


	public List<Subscription> getContactChange() throws Exception {
		changedSubscription = new ArrayList<Subscription>();
		try {
			if(deltaToken == null) {
				getDeltaKey();
				retrieveChangedData(resourceUrl+"/api/data/v8.2/"+field+"s");
			}
			else
				retrieveChangedData(resourceUrl+"/api/data/v8.2/"+field+"s/?$"+deltaToken);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return changedSubscription;	
	} 

	private void getDeltaKey() throws Exception {
		HashMap<String, String> headers = new HashMap<String, String>();
		headers.put("Authorization", accessToken);
		headers.put("OData-MaxVersion", "4.0");
		headers.put("OData-Version", "4.0");
		headers.put("Prefer", "odata.track-changes");		

		HttpRequest request = new HttpRequest.Builder()
				.URL(initializeUrl)
				.headers(headers)
				.build();
		String msg = request.get();
		ObjectNode objectNode = JSONUtils.getNewObjectNode();	
		objectNode = JSONUtils.stringToJSON(msg);
		String deltaToken = objectNode.get("@odata.deltaLink").asText();
		deltaToken = deltaToken.substring(deltaToken.indexOf("deltatoken"));
		return;
	}

	private void retrieveChangedData(String url) throws Exception{
		ObjectNode json = JSONUtils.getNewObjectNode();
		json = JSONUtils.stringToJSON(sendSearchRequest(url));

		ArrayNode valueArray = JSONUtils.getNewArrayNode();
		valueArray = (ArrayNode)json.get("value");

		String deltaLink = json.get("@odata.deltaLink").asText();
		deltaToken = deltaLink.substring(deltaLink.indexOf("deltatoken"));
		getChangedData(valueArray);
		return;
	}

	private String sendSearchRequest(String url) throws IOException {
		HashMap<String, String> headers = new HashMap<String, String>();
		headers.put("Authorization", accessToken);
		headers.put("OData-MaxVersion", "4.0");
		headers.put("OData-Version", "4.0");
		headers.put("Prefer", "odata.track-changes");

		HttpRequest request = new HttpRequest.Builder()
				.URL(url)
				.headers(headers)
				.build();
		return request.get();
	}

	private void getChangedData(ArrayNode arr) throws Exception {
		for(Object tmp : arr) {
			SubscriptionParser subParser = new SubscriptionParser(JSONUtils.stringToJSON(tmp.toString()), schema);
			changedSubscription.add(subParser.getParsedSubscription());
		}
	}

	public List<Subscription> getChangedSubscription() {
		return changedSubscription;
	}
}
