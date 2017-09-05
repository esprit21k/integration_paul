package com.trumpia.trumpia.schema.model;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import com.fasterxml.jackson.databind.node.ArrayNode;
import com.trumpia.trumpia.schema.api.DistributionListCall;
import com.trumpia.util.Http.HttpRequest;

public class DistributionList {
	private DistributionListCall distributionListCall;
	private ArrayNode listNames;
	
	public DistributionList(String APIKey, String user, String baseURL) {
		this.distributionListCall = new DistributionListCall(APIKey, user, baseURL);
	}
	
	public ArrayNode getDistributionLists() throws Exception {
		distributionListCall.extractListNames(distributionListCall.getLists());
		listNames = distributionListCall.listNames;
		return listNames;
	}


}
