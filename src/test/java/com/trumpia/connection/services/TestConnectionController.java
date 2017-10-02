package com.trumpia.connection.services;

import static org.junit.Assert.*;

import java.util.Date;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

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
	
	@Autowired
	private void setConnectionRepository(ConnectionRepository connectionRepo) {
		this.connectionRepo = connectionRepo;
	}
	
	private UserEntity user;
	private TrumpiaAccountEntity trumEntity;
	private DynamicsAccountEntity dynamicsEntity;

	@Before
	public void before() {
		makeEntity();
	}
	
	@Test
	public void test() throws Exception {
		ObjectNode input = JSONUtils.getNewObjectNode();
		input.put("platform_id", "platform");
		input.put("thirdparty_id", "third");
		input.put("connection_name", "connectionName");
		input.put("post", true);
		input.put("delete", true);
		input.put("direction", "bi");
		System.out.println(putConnection(input.toString()));
	}
	
	public String putConnection(String input) throws Exception {
//		UserEntity user = userRepo.findOneByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
		UserEntity user = userRepo.findOneByUsername("test");
		ObjectNode requestData = JSONUtils.getNewObjectNode();
		APIResponse response = new APIResponse();
		try {
			requestData = JSONUtils.stringToJSON(input);
		} catch (Exception e) {
			e.printStackTrace();
			response.setError(true);
			response.setMessage("Invalid request data :"+input);
			return response.getJSONResponse();
		}

		TrumpiaAccountEntity trumEntity = trumRepo.findOneByUsername(requestData.get("platform_id").asText());
		DynamicsAccountEntity dynamicsEntity = dynamicsRepo.findOneByDynamicsId(requestData.get("thirdparty_id").asText());

		ConnectionEntity connection = new ConnectionEntity();
		String connectionName = requestData.get("connection_name").asText();
		boolean connectionNameExists = connectionRepo.getConnectionCountByName(connectionName) !=0;
		if (connectionNameExists) {
			response.setError(true);
			response.setMessage("Existing connection name");
			return response.getJSONResponse();
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
			return response.getJSONResponse();
		}
		if(requestData.get("delete").asBoolean())
			connection.setDeleteOption(true);
		else if(!requestData.get("delete").asBoolean())
			connection.setDeleteOption(false);
		else {
			response.setError(true);
			response.setMessage("Invalid delete option");
			return response.getJSONResponse();
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
			return response.getJSONResponse();
		}
		
		connection.setUserEntity(user);
		connection.setTrumpiaAccount(trumEntity);
		connection.setDynamicsAccount(dynamicsEntity);
		
		connectionRepo.save(connection);
		
		response.setError(false);
		response.setMessage("Successfully Connected");
		ObjectNode data = JSONUtils.getNewObjectNode();
		System.out.println(connection.toString());
//		data = JSONUtils.stringToJSON(connection.toString());
		response.setData(data);
		return response.getJSONResponse();
	}
	
	public void makeEntity() {
		user = new UserEntity();
		user.setEmail("test@mytrum.com");
		user.setUsername("test");
		user.setPassword("test");
		user.setUpdatedDate(new Date());
		userRepo.save(user);

		dynamicsEntity = new DynamicsAccountEntity();
		dynamicsEntity.setDynamicsId("third");
		dynamicsEntity.setResourceUrl("test");
		dynamicsEntity.setAccessToken("test");
		dynamicsEntity.setRefreshToken("test");
		dynamicsEntity.setRefreshToken("test");
		dynamicsEntity.setUserEntity(user);
		dynamicsRepo.save(dynamicsEntity);
		
		trumEntity = new TrumpiaAccountEntity();
		trumEntity.setUsername("platform");
		trumEntity.setBaseURL("baseURL");
		trumEntity.setUserEntity(user);
		trumEntity.setApikey("aPIkey");
		trumRepo.save(trumEntity);
	}
	
	
}
