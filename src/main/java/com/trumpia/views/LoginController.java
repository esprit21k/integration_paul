package com.trumpia.views;

import static com.trumpia.util.LogUtils.getLogger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.trumpia.data.DeletedUserRepository;
import com.trumpia.data.UserRepository;
import com.trumpia.model.DeletedUserEntity;
import com.trumpia.model.UserEntity;
import com.trumpia.util.APIResponse;
import com.trumpia.util.FormValidationUtils;
import com.trumpia.util.JSONUtils;

@Controller
public class LoginController {

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private DeletedUserRepository deletedUserRepo;
	
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	@RequestMapping(path="/", method = RequestMethod.GET)
	public String getIndex(HttpServletRequest request) {
		return "index";
	}
	

	@ResponseBody
    @PostMapping("/signup")
    public String signUp(@org.springframework.web.bind.annotation.RequestBody UserEntity user, HttpServletResponse servletResponse) throws JsonProcessingException {
		getLogger(LoginController.class).debug("Attempting signup with username : {}, email : {}", user.getUsername(), user.getEmail());
		APIResponse response = new APIResponse();
		boolean usernameExists = userRepository.getUserCountByUsername(user.getUsername()) !=0;
		boolean emailExists = userRepository.getUserCountByEmail(user.getEmail()) !=0;
		if (usernameExists || emailExists){
			response.setError(true);
			response.setMessage("User already exists");
			getLogger(LoginController.class).info("User already exists for username : {}, email : {}", user.getUsername(), user.getEmail());
			return response.getJSONResponse();
		}
		try {
			user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
			userRepository.save(user);
			getLogger(LoginController.class).info("User created for username : {}, email : {}", user.getUsername(), user.getEmail());
		} catch (Exception e) {
			response.setError(true);
			return response.getJSONResponse();
		}
		response.setError(false);
		response.setMessage("Successfully signed up.");
		response.setData(JSONUtils.getNewObjectNode().put("email", user.getEmail()));
		return response.getJSONResponse();
    }
	
	@RequestMapping(path = "account/{user_id}", method = RequestMethod.GET)
	public String getAccount(@PathVariable String user_id) throws Exception {
		UserEntity user = userRepository.findOneByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
		APIResponse response = new APIResponse();
		if (user.getId() != userRepository.findOneByUsername(user_id).getId()) {
			response.setError(true);
			response.setMessage("Authentication failed");
			return response.getJSONResponse();
		}
		ObjectNode data = JSONUtils.getNewObjectNode();
		data = JSONUtils.stringToJSON(user.toString());
		response.setError(false);
		response.setMessage("User data retrieved");
		response.setData(data);
		return response.getJSONResponse(); 
	}
	
	@ResponseBody
	@RequestMapping(path ="account/{user_id}", method = RequestMethod.POST)
	public String updateAccount(@PathVariable String user_id, @RequestBody String input, HttpServletResponse servletResponse) throws Exception {
		UserEntity user = userRepository.findOneByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
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
		if (requestData.get("email") != null) {
			boolean emailExists = userRepository.getUserCountByEmail(user.getEmail()) !=0;
			if (!FormValidationUtils.emailValidator(requestData.get("email").asText())) {
				response.setError(true);
				response.setMessage("Invalid email address");
				return response.getJSONResponse();
			}
			if (emailExists) {
				response.setError(true);
				response.setMessage("Email already exists");
				return response.getJSONResponse();
			}
			user.setEmail(requestData.get("email").asText());
		}
		if (requestData.get("password") != null) {
			if (!FormValidationUtils.pwValidator(requestData.get("password").asText())) {
				response.setError(true);
				response.setMessage("Invalid password");
				return response.getJSONResponse();
			}
			user.setPassword(bCryptPasswordEncoder.encode(requestData.get("password").asText()));
		}
		ObjectNode data = JSONUtils.getNewObjectNode();
		data = JSONUtils.stringToJSON(user.toString());
		response.setError(false);
		response.setMessage("User data updated");
		response.setData(data);
		return response.getJSONResponse();
	}
	
	@ResponseBody
	@RequestMapping(path = "account/{user_id}", method = RequestMethod.DELETE)
	public String deleteAccount(@PathVariable String user_id, HttpServletResponse servletResponse ) throws Exception {
		UserEntity user = userRepository.findOneByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
		if (user.getId() != userRepository.findOneByUsername(user_id).getId())
			return null;
		DeletedUserEntity deletedUser = new DeletedUserEntity(user); 
		deletedUserRepo.save(deletedUser);
		userRepository.delete(user);
		APIResponse response = new APIResponse();
		ObjectNode data = JSONUtils.getNewObjectNode();
		data = JSONUtils.stringToJSON(deletedUser.toString());
		response.setError(false);
		response.setMessage("User Account deleted");
		response.setData(data);
		return response.getJSONResponse();
	}
}
