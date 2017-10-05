package com.trumpia.connection.services;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import java.util.Date;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.bind.annotation.PathVariable;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.trumpia.Main;
import com.trumpia.connection.data.ConnectionRepository;
import com.trumpia.connection.model.ConnectionEntity;
import com.trumpia.data.UserRepository;
import com.trumpia.dynamics.data.DynamicsAccountRepository;
import com.trumpia.dynamics.model.DynamicsAccountEntity;
import com.trumpia.model.UserEntity;
import com.trumpia.trumpia.data.TrumpiaAccountRepository;
import com.trumpia.trumpia.model.TrumpiaAccountEntity;
import com.trumpia.util.APIResponse;
import com.trumpia.util.JSONUtils;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = {Main.class})
@ActiveProfiles("dev")
public class TestConnectionController {
	@Autowired
	private ConnectionRepository connectionRepo;

	@Autowired
	private UserRepository userRepo;

	@Autowired
	private TrumpiaAccountRepository trumRepo;

	@Autowired
	private DynamicsAccountRepository dynamicsRepo;

//	UserEntity user = new UserEntity();
	
	private ObjectNode input;
	
	@Before
	public void before() {
		makeEntity();
		input = JSONUtils.getNewObjectNode();
		input.put("platform_id", "platform");
		input.put("thirdparty_id", "third");
		input.put("connection_name", "connection");
		input.put("post", true);
		input.put("delete", true);
		input.put("direction", "bi");
	}
	
	@Test
	public void putTest() throws Exception {
		APIResponse response = putConnection(input.toString());
		System.out.println(response.getJSONResponse());
		UserEntity user = userRepo.findOneByUsername("test");
		System.out.println(connectionRepo.findOneByUserEntityAndConnectionName(user, "connection").getConnectionName());
		assertEquals(connectionRepo.findOneByUserEntityAndConnectionName(user, "connection").getConnectionName(), "connection");
	}
	
	@Test
	public void readTest() throws Exception {
		UserEntity user = userRepo.findOneByUsername("test");
		putConnection(input.toString());
		ConnectionEntity connection = connectionRepo.findOneByUserEntityAndConnectionName(user, "connection");
		APIResponse response = new APIResponse();
		response.setError(false);
		response.setMessage("Successfully read connection: "+"connection");
		ObjectNode data = JSONUtils.getNewObjectNode();
		data = JSONUtils.stringToJSON(connection.toString());
		response.setData(data);
		System.out.println(response.getJSONResponse());
		assertNotNull(data);
	}
	
	@Test
	public void postTest() throws Exception {
		putConnection(input.toString());
		ObjectNode input2 = JSONUtils.getNewObjectNode();
		input2.put("platform_id", "platform");
		input2.put("thirdparty_id", "third");
		input2.put("connection_name", "modifiedName");
		input2.put("direction", "bi");
		APIResponse response = postConnection(input2.toString());
		System.out.println(response.getJSONResponse());
		assertEquals(response.getData().get("connectionName").asText(), "modifiedName");
		
	}
	
	@Test
	public void deleteTest() throws Exception {
		putConnection(input.toString());
		APIResponse response = deleteConnection("connection");
		System.out.println(response.getJSONResponse());
		assertFalse(response.isError());
	}
	
	@After
	public void emptyRepo() {
		connectionRepo.deleteAll();
		dynamicsRepo.deleteAll();
		trumRepo.deleteAll();
		userRepo.deleteAll();
	}
	
	public APIResponse putConnection(String input) throws Exception {
		UserEntity user = userRepo.findOneByUsername("test");
		ObjectNode requestData = JSONUtils.getNewObjectNode();
		APIResponse response = new APIResponse();
		try {
			requestData = JSONUtils.stringToJSON(input);
		} catch (Exception e) {
			e.printStackTrace();
			response.setError(true);
			response.setMessage("Invalid request data :"+input);
			return response;
		}

		TrumpiaAccountEntity trumEntity = trumRepo.findOneByUsername(requestData.get("platform_id").asText());
		DynamicsAccountEntity dynamicsEntity = dynamicsRepo.findOneByDynamicsId(requestData.get("thirdparty_id").asText());

		ConnectionEntity connection = new ConnectionEntity();
		String connectionName = requestData.get("connection_name").asText();
		boolean connectionNameExists = connectionRepo.getConnectionCountByName(connectionName) !=0;
		if (connectionNameExists) {
			response.setError(true);
			response.setMessage("Existing connection name");
			return response;
		}
		else
			connection.setConnectionName(connectionName);
		if(requestData.get("post").asBoolean())
			connection.setPostOption(true);
		else if (!requestData.get("post").asBoolean())
			connection.setPostOption(false);
		else {
			response.setError(true);
			response.setMessage("Invalid post option");
			return response;
		}
		if(requestData.get("delete").asBoolean())
			connection.setDeleteOption(true);
		else if(!requestData.get("delete").asBoolean())
			connection.setDeleteOption(false);
		else {
			response.setError(true);
			response.setMessage("Invalid delete option");
			return response;
		}
		if(requestData.get("direction").asText().equals("bi"))
			connection.setDirection("bi");
		else if (requestData.get("direction").asText().equals("toPlatform"))
			connection.setDirection("toPlatform");
		else if (requestData.get("direction").asText().equals("toThirdparty"))
			connection.setDirection("toThirdparty");
		else {
			response.setError(true);
			response.setMessage("Invalid direction option");
			return response;
		}
		
		connection.setUserEntity(user);
		connection.setTrumpiaAccount(trumEntity);
		connection.setDynamicsAccount(dynamicsEntity);
		
		connectionRepo.save(connection);
		
		response.setError(false);
		response.setMessage("Successfully Connected");
		ObjectNode data = JSONUtils.getNewObjectNode();
		data = JSONUtils.stringToJSON(connection.toString());
		response.setData(data);
		return response;
	}
	
	public APIResponse postConnection(String input) throws Exception {
		UserEntity user = userRepo.findOneByUsername("test");
		ObjectNode requestData = JSONUtils.getNewObjectNode();
		APIResponse response = new APIResponse();
		try {
			requestData = JSONUtils.stringToJSON(input);
		} catch (Exception e) {
			e.printStackTrace();
			response.setError(true);
			response.setMessage("Invalid request data :"+input);
			return response;
		}
		ConnectionEntity connection = connectionRepo.findOneByUserEntityAndConnectionName(user, "connection");
		if(requestData.get("post") == null) { 
		}
		else if(requestData.get("post").asText().equals("true"))
			connection.setPostOption(true);
		else if (requestData.get("post").asText().equals("false"))
			connection.setPostOption(false);
		else {
			response.setError(true);
		response.setMessage("Invalid post option");
		return response;
		}
		if (requestData.get("delete") == null) {
		}
		else if(requestData.get("delete").asText().equals("true"))
			connection.setDeleteOption(true);
		else if(requestData.get("delete").asText().equals("false"))
			connection.setDeleteOption(false);
		else {
			response.setError(true);
			response.setMessage("Invalid delete option");
			return response;
		}
		if(requestData.get("direction") == null) {
		}
		else if(requestData.get("direction").asText().equals("bi"))
			connection.setDirection("bi");
		else if (requestData.get("direction").asText().equals("toPlatform"))
			connection.setDirection("toPlatform");
		else if (requestData.get("direction").asText().equals("toThirdparty"))
			connection.setDirection("toThirdparty");
		else {
			response.setError(true);
			response.setMessage("Invalid direction option");
			return response;
		}
		
		if(requestData.get("connection_name") == null) {
		}
		else
			connection.setConnectionName(requestData.get("connection_name").asText());

		connectionRepo.save(connection);
		response.setError(false);
		response.setMessage("Successfully updated connection: ");
		ObjectNode data = JSONUtils.getNewObjectNode();
		data = JSONUtils.stringToJSON(connection.toString());
		response.setData(data);
		return response;
	}
	
	public APIResponse deleteConnection(String connection_id) throws JsonProcessingException {
		UserEntity user = userRepo.findOneByUsername("test");
		ConnectionEntity connection = connectionRepo.findOneByUserEntityAndConnectionName(user, connection_id);
		System.out.println("delete : "+connection.toString());
		connectionRepo.delete(connection);
		APIResponse response = new APIResponse();
		if (connectionRepo.findOneByUserEntityAndConnectionName(user, connection_id) == null) {
			response.setError(false);
			response.setMessage("Connection id: "+connection_id+" is successfully deleted");
			return response;
		}
		else {
			response.setError(true);
			response.setMessage("Deleting Connection id: "+connection_id+" is failed");
			return response;
		}
	}
	
	public void makeEntity() {
		UserEntity user = new UserEntity();
		user.setEmail("test@mytrum.com");
		user.setUsername("test");
		user.setPassword("test");
		user.setUpdatedDate(new Date());
		userRepo.save(user);

		DynamicsAccountEntity dynamicsEntity = new DynamicsAccountEntity();
		dynamicsEntity.setDynamicsId("third");
		dynamicsEntity.setResourceUrl("test");
		dynamicsEntity.setAccessToken("test");
		dynamicsEntity.setRefreshToken("test");
		dynamicsEntity.setRefreshToken("test");
		dynamicsEntity.setUserEntity(user);
		dynamicsRepo.save(dynamicsEntity);
		
		TrumpiaAccountEntity trumEntity = new TrumpiaAccountEntity();
		trumEntity.setUsername("platform");
		trumEntity.setBaseURL("baseURL");
		trumEntity.setUserEntity(user);
		trumEntity.setApikey("aPIkey");
		trumRepo.save(trumEntity);
	}
	
	
}
