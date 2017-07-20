package com.trumpia.views;

import static com.trumpia.util.LogUtils.getLogger;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.trumpia.SecurityService;
import com.trumpia.data.UserRepository;
import com.trumpia.model.UserEntity;
import com.trumpia.model.transientModel.LoginEntity;
import com.trumpia.util.Http.HttpRequest;

import okhttp3.FormBody;
import okhttp3.RequestBody;

@Controller
public class LoginController {

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private SecurityService securityService;
	
	@RequestMapping(path="/login", method = RequestMethod.GET)
	public String getLogin(HttpServletRequest request) {
		return "login";
	}

	@RequestMapping(path="/login", method = RequestMethod.POST)
	public String postLogin(@ModelAttribute LoginEntity login, HttpServletRequest request) {
		if (login.isValid()) {
			UserEntity userQueryResult = userRepository.findOneByUsername(login.getUsername());
			if (userQueryResult == null) {
				UserEntity user = new UserEntity(login.getUsername(),login.getAPIKey());
			    userRepository.save(user);
			    getLogger(this).info("User created with apikey : {} and uniqueId : {}", login.getAPIKey(), user.getUniqueId());
			    userQueryResult = user;
			}
			securityService.autologin(userQueryResult.getUsername(), userQueryResult.getApikey());
			 return "redirect:/registerDynamics";
		}
		return "login";
	}
}
