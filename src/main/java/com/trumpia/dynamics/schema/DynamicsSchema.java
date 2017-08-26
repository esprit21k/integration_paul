package com.trumpia.dynamics.schema;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.json.JSONArray;
import org.json.JSONObject;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.trumpia.util.Http.HttpRequest;

public class DynamicsSchema {
	final static String METADATA_URL = "https://trumpia.crm.dynamics.com/api/data/v8.2/$metadata#contact";
	final static String PATH = "DynamicsPropertyInfo.xml";
	private HashMap<String, String> properties;
	private NodeList propertiesInfo;
	private String accessToken;

	public DynamicsSchema(String accessToken){
		properties = new HashMap<String, String>();
		this.accessToken = accessToken;
		//getDynamicsSchema();
	}
	
	public HashMap<String, String>getProperties() {
		return properties;
	}
	
	public JSONObject propertiesToJSON() {
		JSONObject property = new JSONObject();
		JSONArray propertyArray = new JSONArray();
		
		for(Map.Entry<String, String> entry : properties.entrySet()) {
			JSONObject json = new JSONObject();
			json.put(entry.getKey(), entry.getValue());
			propertyArray.put(json);
		}
		
		property.put("Properties", propertyArray);
		return property;
	}
	
	public void getDynamicsSchema() {
		if(new File(PATH).isFile())
			getDynamicsSchemaFromLocal();
		else
			getDynamicsSchemaFromDynamicsAPI();
	}
	
	private void getDynamicsSchemaFromLocal() {
		try {
			File file = new File(PATH);
			FileInputStream inputStream = new FileInputStream(file);
			BufferedReader rd = new BufferedReader(new InputStreamReader(inputStream));
			String line;
			StringBuffer response = new StringBuffer(); 
			while((line = rd.readLine()) != null) {
			 response.append(line);
			 response.append('\r');
			}
			rd.close();
			Document doc = parseXML(response.toString());
			doc.getDocumentElement().normalize();
			propertiesInfo = doc.getElementsByTagName("Property");
			storePropertyInfoToMap(propertiesInfo);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void getDynamicsSchemaFromDynamicsAPI(){
		try {
			
			String XMLstring = getMetaDataSchema();
			Document doc = parseXML(XMLstring);
			doc.getDocumentElement().normalize();
			propertiesInfo = findPropertiesInfo(doc.getElementsByTagName("EntityType"));
			storePropertyInfoToMap(propertiesInfo);
		} catch (Exception e) {
			e.getMessage();
			e.printStackTrace();
		}
	}
	
	private NodeList findPropertiesInfo(NodeList entityTypeList) throws Exception {
		
		for (int i = 0; i < entityTypeList.getLength(); i++) {
			Node nNode = entityTypeList.item(i);
			String valueOfNameAttribute = nNode.getAttributes().getNamedItem("Name").getNodeValue(); 
			if( valueOfNameAttribute.equals("contact")){
				return nNode.getChildNodes();
			}
		}

		throw new Exception("there is no contact info :: Dynamics metadata error");
	}

	private void storePropertyInfoToMap(NodeList contact) {
		for(int i = 0; i < contact.getLength(); i++) {
			if(contact.item(i).getNodeName() == "Property") {
				NamedNodeMap property = contact.item(i).getAttributes();
				properties.put(property.getNamedItem("Name").getNodeValue(),property.getNamedItem("Type").getNodeValue());
			}
		}
	}
	
	public void storeDynamicsSchemaAsXML() throws Exception {
		Document newXmlDocument = DocumentBuilderFactory.newInstance()
                .newDocumentBuilder().newDocument();
        Element root = newXmlDocument.createElement("root");
        newXmlDocument.appendChild(root);
        for (int i = 0; i < propertiesInfo.getLength(); i++) {
            Node node = propertiesInfo.item(i);
            Node copyNode = newXmlDocument.importNode(node, true);
            root.appendChild(copyNode);
        }
        
        Transformer transformer = TransformerFactory.newInstance().newTransformer();
        Result output = new StreamResult(new File(PATH));
        Source input = new DOMSource(newXmlDocument);

        transformer.transform(input, output);
	}

	private String getMetaDataSchema() throws Exception {
		HashMap<String, String> headers = new HashMap<String, String>();

		headers.put("Authorization", accessToken);
		headers.put("OData-MaxVersion", "4.0");
		headers.put("OData-Version", "4.0");

		HttpRequest request = new HttpRequest.Builder()
				.URL(METADATA_URL)
				.headers(headers)
				.build();

		return request.get();
	}

	private Document parseXML(String xml) throws Exception{
		DocumentBuilderFactory objDocumentBuilderFactory = null;
		DocumentBuilder objDocumentBuilder = null;
		Document doc = null;
		InputStream stream = new ByteArrayInputStream(xml.getBytes());
		try{
			objDocumentBuilderFactory = DocumentBuilderFactory.newInstance();
			objDocumentBuilder = objDocumentBuilderFactory.newDocumentBuilder();
			doc = objDocumentBuilder.parse(stream);
		}catch(Exception ex){
			throw ex;
		}       
		return doc;
	}

}
