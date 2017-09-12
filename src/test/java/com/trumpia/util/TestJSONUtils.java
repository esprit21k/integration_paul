package com.trumpia.util;

import static org.junit.Assert.*;

import java.io.ByteArrayInputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathFactory;

import org.junit.Test;
import org.w3c.dom.Document;

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
