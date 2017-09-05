package com.trumpia.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

public class APIResponse {

	ObjectNode body = JSONUtils.getNewObjectNode();
	private boolean error = true;
	private String message = "Internal Default Error";
	private JsonNode data;

	public boolean isError() {
		return error;
	}

	public void setError(boolean error) {
		this.error = error;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public JsonNode getData() {
		return data;
	}

	public void setData(ObjectNode data) {
		this.data = data;
	}
	
	public APIResponse() {
	}
	
	public String getJSONResponse() throws JsonProcessingException {
		body.put("error", error);
		body.put("message", message);
		body.set("data", data);
		return JSONUtils.toJSONString(body);
	}
	
}
