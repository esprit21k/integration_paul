package com.trumpia.util;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.io.IOException;

public class JSONUtils {
	private final static ObjectMapper objectMapper = new ObjectMapper();


	public static ObjectNode getNewObjectNode() {
		return objectMapper.createObjectNode();
	}

	public static ArrayNode getNewArrayNode() {
		return objectMapper.createArrayNode();
	}

	public static JsonNode getNewJsonNode() {
		return objectMapper.createObjectNode();
	}

	public static String toJSONString(ObjectNode objectNode) throws JsonProcessingException {
		return objectMapper.writeValueAsString(objectNode);
	}

	public static ObjectNode stringToJSON(String message) throws Exception {
		JsonFactory factory = objectMapper.getFactory();
		JsonParser jsonParser = factory.createParser(message);
		ObjectNode objectNode = objectMapper.readTree(jsonParser);	
		return objectNode;
	}

}
