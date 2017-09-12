package com.trumpia.util;

import java.io.ByteArrayInputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;

public class XMLUtils {
	private final static DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
	
	public static String getValueFromExpression(String xml, String expression) throws Exception{
		DocumentBuilder builder = builderFactory.newDocumentBuilder();

		Document xmlDocument = builder.parse(new ByteArrayInputStream(xml.getBytes()));
		
		XPath xPath =  XPathFactory.newInstance().newXPath();
		
		String output = xPath.compile(expression).evaluate(xmlDocument);
		return output;
	}

}
