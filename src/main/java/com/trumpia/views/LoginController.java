package com.trumpia.views;

import static com.trumpia.util.LogUtils.getLogger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.trumpia.data.UserRepository;
import com.trumpia.model.UserEntity;
import com.trumpia.util.APIResponse;
import com.trumpia.util.JSONUtils;

@Controller
public class LoginController {

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	@RequestMapping(path="/", method = RequestMethod.GET)
	public String getIndex(HttpServletRequest request) {
		return "index";
	}
	

	@ResponseBody
    @PostMapping("/signup")
    public String signUp(@org.springframework.web.bind.annotation.RequestBody UserEntity user, HttpServletResponse servletResponse) {
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

}
