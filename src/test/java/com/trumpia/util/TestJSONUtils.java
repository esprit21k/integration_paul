package com.trumpia.util;

import static org.junit.Assert.*;

import org.junit.Test;

import com.fasterxml.jackson.databind.node.ObjectNode;

public class TestJSONUtils {

	@Test
	public void test() throws Exception {
		ObjectNode objectNode = JSONUtils.getNewObjectNode();
		String message = "{\"string\":\"hi\"}";
		objectNode = JSONUtils.stringToJSON(message);
		System.out.println(objectNode.toString());
	}

}
