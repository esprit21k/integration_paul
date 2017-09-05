package com.trumpia.dynamics.schema;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.trumpia.dynamics.model.DynamicsAccountEntity;
import com.trumpia.dynamics.schema.data.DynamicsSchemaRepository;
import com.trumpia.dynamics.schema.model.DynamicsSchemaEntity;
import com.trumpia.util.Http.HttpRequest;
import static com.trumpia.util.LogUtils.getLogger;


@Component
public class DynamicsSchema {
	@Autowired
	private DynamicsSchemaRepository dynamicsSchemaRepository;

	private HashMap<String, String> properties;
	private NodeList propertiesInfo;
	private String accessToken;
	private String metadataURL;
	private DynamicsAccountEntity dynamicsAccountEntity;
	public List<DynamicsSchemaEntity> dynamicsSchemaEntityLists;
	private DynamicsSchemaEntity dynamicsSchemaEntity;

	public HashMap<String, String> getProperties() {
		return properties;
	}

	public ObjectNode propertiesToJSON() {
		ObjectMapper mapper = new ObjectMapper();
		ObjectNode property = mapper.createObjectNode();
		ArrayNode propertyArray = mapper.createArrayNode();

		for(Map.Entry<String, String> entry : properties.entrySet()) {
			ObjectNode json = mapper.createObjectNode();
			json.put(entry.getKey(), entry.getValue());
			propertyArray.add(json);
		}

		property.set("Properties", propertyArray);
		return property;
	}

	public void getDynamicsSchema(DynamicsAccountEntity dynamicsAccountEntity) {
		//		dynamicsAccountEntity = EntityUtil.findDynamicEntityByPrincipal();
		properties = new HashMap<String, String>();
		this.accessToken = dynamicsAccountEntity.getAccessToken();
		this.dynamicsAccountEntity = dynamicsAccountEntity;
		this.metadataURL = dynamicsAccountEntity.getResourceUrl()+"/api/data/v8.2/$metadata#contact";

		dynamicsSchemaEntityLists = dynamicsSchemaRepository.findByDynamicsAccountEntity(dynamicsAccountEntity);

		if(dynamicsSchemaEntityLists.size() == 0)
			getDynamicsSchemaFromDynamicsAPI();
		else
			setPropertiesFromDB(dynamicsSchemaEntityLists);
	}

	public void setPropertiesFromDB(List<DynamicsSchemaEntity> schema) {
		for(DynamicsSchemaEntity entity : schema)
			properties.put(entity.getName(), entity.getType());
	}

	private void getDynamicsSchemaFromDynamicsAPI() {
		try {
			String XMLstring = getMetaDataSchema();
			Document doc = parseXML(XMLstring);
			doc.getDocumentElement().normalize();
			if (!doc.getElementsByTagName("EntityType").equals(null))
				propertiesInfo = findPropertiesInfo(doc.getElementsByTagName("EntityType"));
			else
				throw new DynamicsSchemaException("Wrong XML data: "+doc.toString());
			storePropertyInfoToMap(propertiesInfo);
		} catch (Exception e) {
			e.getMessage();
			e.printStackTrace();
		}
	}

	private NodeList findPropertiesInfo(NodeList entityTypeList) {
		for (int i = 0; i < entityTypeList.getLength(); i++) {
			Node nNode = entityTypeList.item(i);
			String valueOfNameAttribute = nNode.getAttributes().getNamedItem("Name").getNodeValue(); 
			if( valueOfNameAttribute.equals("contact")){
				return nNode.getChildNodes();
			}
		}

		throw new DynamicsSchemaException("there is no contact info :: Dynamics metadata error - EntityType length: "+ entityTypeList.getLength());

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
		dynamicsSchemaRepository.save(dynamicsSchemaEntityLists);
		if (dynamicsSchemaEntityLists.isEmpty())
			throw new DynamicsSchemaException("Dynamics Schema Entity List is empty");
		
		for(Map.Entry<String, String> entry : properties.entrySet()) {
			dynamicsSchemaEntity = new DynamicsSchemaEntity();
			dynamicsSchemaEntity.setName(entry.getKey());
			dynamicsSchemaEntity.setType(entry.getValue());
			dynamicsSchemaEntity.setDynamicsAccountEntity(dynamicsAccountEntity);
			dynamicsSchemaEntityLists.add(dynamicsSchemaEntity);
		}
	}

	private String getMetaDataSchema() throws Exception {
		HashMap<String, String> headers = new HashMap<String, String>();

		headers.put("Authorization", accessToken);
		headers.put("OData-MaxVersion", "4.0");
		headers.put("OData-Version", "4.0");

		HttpRequest request = new HttpRequest.Builder()
				.URL(metadataURL)
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
		} catch(Exception ex){
			getLogger(DynamicsSchema.class).error(xml);
			throw ex;
		}       
		return doc;
	}

	public class DynamicsSchemaException extends RuntimeException{
		private static final long serialVersionUID = 6727732589593600785L;

		public DynamicsSchemaException(String message) {
			super(message);
		}
	}
}
