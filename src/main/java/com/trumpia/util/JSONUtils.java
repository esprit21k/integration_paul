package com.trumpia.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import static com.trumpia.util.LogUtils.getLogger;

public class JSONUtils {
	private final static ObjectMapper objectMapper = new ObjectMapper();

	public static ObjectNode getNewObjectNode() {
		return objectMapper.createObjectNode();
	}

	public static ArrayNode getNewArrayNode() {
		return objectMapper.createArrayNode();
	}
	
	public static String toJSONString(ObjectNode objectNode) {
		try {
			return objectMapper.writeValueAsString(objectNode);
		} catch (JsonProcessingException e) {
			return "{\"error\" : True}";
		}
	}
}
