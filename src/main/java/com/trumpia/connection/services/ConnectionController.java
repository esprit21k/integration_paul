package com.trumpia.connection.services;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
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

@RestController
@RequestMapping(path = "/connection")
public class ConnectionController {
	@Autowired
	private ConnectionRepository connectionRepo;
	
	@Autowired
	private UserRepository userRepo;
	
	@Autowired
	private TrumpiaAccountRepository trumRepo;
	
	@Autowired
	private DynamicsAccountRepository dynamicsRepo;
	
	@RequestMapping(method = RequestMethod.PUT)
	public String putConnection(@RequestBody String input) throws Exception {
		UserEntity user = userRepo.findOneByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
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
		data = JSONUtils.stringToJSON(connection.toString());
		response.setData(data);
		return response.getJSONResponse();
	}
	
	@RequestMapping(method = RequestMethod.GET) // ?size=2&page=0 (page starts with index 0)
	public String getAllConnection(Pageable page) {
		UserEntity user = userRepo.findOneByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
		Page<ConnectionEntity> fetchedPage = connectionRepo.findPageByUserEntity(new PageRequest(page.getPageNumber(), page.getPageSize(), Sort.Direction.ASC, "updatedDate"), user);
		
		ObjectNode info = JSONUtils.getNewObjectNode();
		ArrayNode data = JSONUtils.getNewArrayNode();
		
		for(ConnectionEntity content : fetchedPage.getContent()) {
			data.add(content.getConnectionName());
			data.add(content.getTrumpiaAccount().getUsername());
			data.add(content.getDynamicsAccount().getDynamicsId());
		}

		info.put("page", page.getPageNumber()+1);
		info.set("data", data);
		return info.asText();
	}
	
	@RequestMapping(path = "/{connection_id}", method = RequestMethod.GET)
	public String getConnection0(@PathVariable String connection_id) {
		UserEntity user = userRepo.findOneByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
		ConnectionEntity connection = connectionRepo.findOneByConnectionName(user, connection_id);
		
		return connection.toString();
	}
	
	@RequestMapping(path = "/{connection_id}", method = RequestMethod.POST)
	public String postConnection(@RequestBody String input, @PathVariable String connection_id) {
		UserEntity user = userRepo.findOneByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
		ObjectNode requestData = JSONUtils.getNewObjectNode();
		try {
			requestData = JSONUtils.stringToJSON(input);
		} catch (Exception e) {
			e.printStackTrace();
			return "Invalid request data :"+input;
		}
		ConnectionEntity connection = connectionRepo.findOneByConnectionName(user, connection_id);
		if(requestData.get("post").asText().equals("true"))
			connection.setPostOption(true);
		else if (requestData.get("post").asText().equals("false"))
			connection.setPostOption(false);
		else
			return "Invalid post option";
		if(requestData.get("delete").asText().equals("true"))
			connection.setDeleteOption(true);
		else if(requestData.get("delete").asText().equals("false"))
			connection.setDeleteOption(false);
		else
			return "Invalid delete option";
		connection.setConnectionName(requestData.get("connection_name").asText());

		connectionRepo.save(connection);
		
		return connection.toString();
	}

}
