package com.trumpia.dynamics.schema;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.concurrent.ThreadSafe;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.test.context.ContextConfiguration;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.trumpia.dynamics.model.DynamicsAccountEntity;
import com.trumpia.dynamics.schema.data.DynamicsSchemaRepository;
import com.trumpia.dynamics.schema.data.DynamicsSchemaRepositoryConfig;
import com.trumpia.dynamics.schema.model.DynamicsSchemaEntity;
import com.trumpia.util.AuthenticationInfo;
import com.trumpia.util.EntityUtil;
import com.trumpia.util.Http.HttpRequest;


@Component
public class DynamicsSchema {
	@Autowired
	private DynamicsSchemaRepository dynamicsSchemaRepository;

	final static String METADATA_URL = "https://trumpia.crm.dynamics.com/api/data/v8.2/$metadata#contact";
	//final static String PATH = "DynamicsPropertyInfo.xml";
	private HashMap<String, String> properties;
	private NodeList propertiesInfo;
	private String accessToken;
	private DynamicsAccountEntity dynamicsAccountEntity;
	public List<DynamicsSchemaEntity> dynamicsSchemaEntityLists;
	private DynamicsSchemaEntity dynamicsSchemaEntity;

	public HashMap<String, String> getProperties() {
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

	public void getDynamicsSchema(DynamicsAccountEntity dynamicsAccountEntity) throws InterruptedException {
		properties = new HashMap<String, String>();
		this.accessToken = dynamicsAccountEntity.getAccessToken();
		this.dynamicsAccountEntity = dynamicsAccountEntity;
		//		dynamicsAccountEntity = EntityUtil.findDynamicEntityByPrincipal();

		dynamicsSchemaEntityLists = dynamicsSchemaRepository.findByDynamicsAccountEntity(dynamicsAccountEntity);
		if(dynamicsSchemaEntityLists.isEmpty()) {
			getDynamicsSchemaFromDynamicsAPI();

		}
		else {
			getDynamicsSchemaFromDB(dynamicsSchemaEntityLists);
		}
	}

	public void getDynamicsSchemaFromDB(List<DynamicsSchemaEntity> schema) {
		System.out.println(schema.size());
		for(DynamicsSchemaEntity entity : schema)
			setPropertiesFromDB(entity);
	}

	private void setPropertiesFromDB(DynamicsSchemaEntity entity) {
		System.out.println("setPropertiesFromDB");
		properties.put(entity.getName(), entity.getType());
	}

	public void getDynamicsSchemaFromDynamicsAPI() {
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

	public void storeDynamicsSchemaDB() throws Exception {
		for(Map.Entry<String, String> entry : properties.entrySet()) {
			dynamicsSchemaEntity = new DynamicsSchemaEntity();
			dynamicsSchemaEntity.setName(entry.getKey());
			dynamicsSchemaEntity.setType(entry.getValue());
			dynamicsSchemaEntity.setDynamicsAccountEntity(dynamicsAccountEntity);
			dynamicsSchemaRepository.save(dynamicsSchemaEntity);
			dynamicsSchemaEntityLists.add(dynamicsSchemaEntity);
		}
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
