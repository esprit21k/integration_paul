package com.trumpia.dynamics.APIcaller;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.trumpia.Main;
import com.trumpia.dynamics.data.DynamicsAccountRepository;
import com.trumpia.dynamics.model.DynamicsAccountEntity;
import com.trumpia.mapping.data.MappingRepository;
import com.trumpia.mapping.model.MappingEntity;
import com.trumpia.util.JSONUtils;
import com.trumpia.util.Http.HttpRequest;

import okhttp3.MediaType;
import okhttp3.RequestBody;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = {Main.class})
@ActiveProfiles("dev")
public class TestDynamicsAPIcaller {
	@Autowired
	private DynamicsAccountRepository dynamicsRepo;
	
	@Autowired
	private MappingRepository mappingRepo;
	
	@Autowired
	public void setDynamicsRepository(DynamicsAccountRepository dynamicsRepo) {
		this.dynamicsRepo = dynamicsRepo;
	}
	
	private String accessToken;
	private String resourceUrl = "https://trumpia.crm.dynamics.com"; 
	private DynamicsAPIcaller dynamics;
	private DynamicsAccountEntity dynamicsEntity;
	private MappingEntity mappingEntity;
	private List<MappingEntity> schema = new ArrayList<MappingEntity>();
	
	
	@Before
	public void before() throws Exception {
		RefreshAccessToken token = new RefreshAccessToken("AQABAAAAAAA9kTklhVy7SJTGAzR-p1BczKpM8heBH0fUKu70a8bSG2m0jQUlt1SE5fgshF3SB29cHyyLRrPYRK_UY5Rsc1CWpHtKTiE214NIsVA5jVaJkO4vW6bDHc0a_2T6B6lIqUO1amGaLfnIw9lz1EwF5EPdMHT6XoGnU7p_3Oak3Te7tYPllPgwJl3YFTGOjD7zMAfSo_6xGW21vtdK3AZquSjnDP6SuYqNeEe97-ZPJMyvn39LipjlOXWdtBsf63JHql2iireiQl-4QD7ip-TGMWunEb9HuQ-jZvgNboFzdNo_H3c9Be2RVMGQVHSR-0tv381DSrxlb8-xiE6x-QHhRBb8LB7uQ2L0k9TM6XDpnDa9_zGpr0_0ygAhWPhyHv96lg3RZAT27RYHnFhqlcpbN1lwfPbbU-yOImLbWiuLA7kS_Cpte0-mw8Gaj--A6SZI7oWw3bxFV4tNFyblnGLE2UMUHd0RAZkkF2v1oxEX0XoYsrXA_m71RGoK4D91nFeMUJ7SztlOk5imKwn9MUJssa7vHc1E_XoBgTE-KHdLIqwVbtu374wHFkI9gOCO4e5AbFy9huqZkWbyHgtSTYIbFcZQ8ylv4ixsDnkwvFzTyGxkeVjSFVkjW-9LL9gSQquvdnjOGDO13Ox2jta3tcGesHYHzpEcUgF-Ssujhc04xW0YYiip45jUd01inQ9x-P9bxFadluDofZvrbYJVHId_p515j4P0y_9CevHz4lbmWXeB4Ly8d_oY03uxdCwe3gNdYmBQT3mwncSTG5k-vEivJcMaIAA", resourceUrl);
		token.updateToken();
		accessToken = token.accessToken;
		makeEntity();
		makeMapping("first_name", "firstname");
		makeMapping("last_name", "lastname");
		makeMapping("email", "emailaddress1");
		mappingRepo.save(schema);
		dynamics = new DynamicsAPIcaller(dynamicsEntity, schema);
		
	}
	
	@Test
	public void changeTest() throws Exception {
//		initializeTest
		assertEquals(getDynamicsContactNum(resourceUrl+"/api/data/v8.2/contacts"), dynamics.getContactChange().size());
		
//		changeTest
		ObjectNode contactBody = JSONUtils.getNewObjectNode();
		contactBody.put("firstname", "sample");
		contactBody.put("lastname", "account");
		contactBody.put("emailaddress1", "asdf@gmail.com");
		String contactId = makeDynamicsContact(contactBody);
		String change = dynamics.getContactChange().get(0).toString();
		System.out.println("change: "+change);
		assertEquals("sample", change.substring(change.indexOf("firstName")+10, change.indexOf("firstName")+16));
		System.out.println(deleteDynamicsContact(contactId));
	}
	
	private void makeEntity() {
		dynamicsEntity = new DynamicsAccountEntity();
		dynamicsEntity.setAccessToken(accessToken);
		dynamicsEntity.setResourceUrl(resourceUrl);
		dynamicsEntity.setField("contact");
		dynamicsRepo.save(dynamicsEntity);
	}
	
	private void makeMapping(String trumpiaField, String targetField) {
		mappingEntity = new MappingEntity();
		mappingEntity.setTrumpiaFieldName(trumpiaField);
		mappingEntity.setTargetFieldName(targetField);
		schema.add(mappingEntity);
	}
	
	private int getDynamicsContactNum(String resourceUrl) throws Exception {
		HashMap<String, String> headers = new HashMap<String, String>();
		headers.put("Authorization", accessToken);
		headers.put("OData-MaxVersion", "4.0");
		headers.put("OData-Version", "4.0");
		headers.put("Prefer", "odata.track-changes");

		HttpRequest request = new HttpRequest.Builder()
				.URL(resourceUrl)
				.headers(headers)
				.build();
		ArrayNode arrayNode = JSONUtils.getNewArrayNode();
		arrayNode = (ArrayNode)JSONUtils.stringToJSON(request.get()).get("value");
		return arrayNode.size();
	}
	
	private String makeDynamicsContact(ObjectNode contactBody) throws Exception {
		RequestBody body = RequestBody.create(MediaType.parse("application/json"), contactBody.toString());
		HashMap<String, String> headers = new HashMap<String, String>();
		headers.put("Authorization", accessToken);
		headers.put("OData-MaxVersion", "4.0");
		headers.put("OData-Version", "4.0");
		headers.put("Prefer", "return=representation");
		headers.put("Content-Type", "application/json");

		HttpRequest request = new HttpRequest.Builder()
				.URL(resourceUrl+"/api/data/v8.2/contacts?$select=contactid")
				.headers(headers)
				.setRawBody(body)
				.build();
		String msg = request.post();
		ObjectNode jsonNode = JSONUtils.getNewObjectNode();
		jsonNode = JSONUtils.stringToJSON(msg);
		String contactid = jsonNode.get("contactid").asText();
		return contactid;
	}
	
	private String deleteDynamicsContact(String contactid) throws IOException {
		HashMap<String, String> headers = new HashMap<String, String>();
		headers.put("Authorization", accessToken);
		headers.put("OData-MaxVersion", "4.0");
		headers.put("OData-Version", "4.0");

		HttpRequest request = new HttpRequest.Builder()
				.URL(resourceUrl+"/api/data/v8.2/contacts("+contactid+")")
				.headers(headers)
				.build();
		String msg = request.delete();
		return msg;
	}

}

