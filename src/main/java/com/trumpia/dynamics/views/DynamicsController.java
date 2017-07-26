package com.trumpia.dynamics.views;

import static com.trumpia.util.LogUtils.getLogger;

import java.util.List;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.Principal;
import java.util.ArrayList;
import java.util.Arrays;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.Base64Utils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.trumpia.data.UserRepository;
import com.trumpia.dynamics.data.DynamicsAccountRepository;
import com.trumpia.dynamics.model.DynamicsAccountEntity;
import com.trumpia.model.UserEntity;
import com.trumpia.model.transientModel.LoginEntity;
import com.trumpia.util.Http.HttpRequest;

import okhttp3.FormBody;
import okhttp3.RequestBody;

@Controller
public class DynamicsController {

	@Autowired
	private DynamicsAccountRepository dynamicsAccountRepository;
	
	@Value("${external.dynamics.baseURL}")
	String baseURL;
	
	@Value("${external.dynamics.clientID}")
	String clientID;
	
	@Value("${external.dynamics.key}")
	String key;

	@RequestMapping("/registerDynamics")
	public String getForm(HttpSession session, Principal principal) {
		session.setAttribute("sessionUser", principal.getName());
		return "registerDynamics";
	}
	
	
	@RequestMapping("/oauth/dynamics")
	public String OAuthRegister(@RequestParam(name="code", required=false) String p_code,
							@RequestParam(name="session_state", required=false) String p_session_state,
							@RequestParam(name="state", required=false) String p_state,
							@RequestParam(name="error", required=false) String p_error,
							@RequestParam(name="error_description", required=false) String p_error_description,
							HttpServletRequest request) {
		if (p_error == null) {
			String localAddr = request.getLocalName();
			if (!localAddr.contains("http")) //Resolving hostname when run locally with localhost as the address
				localAddr = "http://174.35.126.191/oauth/dynamics"; 
			String requestURL = baseURL + "/oauth2/token";
			String valueDecoded = null;
			try {
				valueDecoded= new String(Base64Utils.decodeFromString(p_state ), "UTF-8");
			} catch (UnsupportedEncodingException e1) {
				return "redirect:/registerDynamics";
			}
			List<String> values = Arrays.asList(valueDecoded.split("="));
			String username = values.get(0);
			RequestBody reqBody = new FormBody.Builder().add("grant_type", "authorization_code")
									.add("client_id", clientID)
									.add("code", p_code)
									.add("redirect_uri", localAddr)
									.add("client_secret", key)
									.add("resource", values.get(1)).build();
			String response = null;
			try {
				response = new HttpRequest.Builder().URL(requestURL).setRawBody(reqBody).build().post();
			} catch (JsonProcessingException e) {
				e.printStackTrace();
				return "redirect:/registerDynamics";
			} catch (IOException e) {
				e.printStackTrace();
				return "redirect:/registerDynamics";
			}
			ObjectMapper mapper = new ObjectMapper();
			JsonNode jsonObj;
			try {
				jsonObj = mapper.readTree(response);
			} catch (JsonProcessingException e) {
				getLogger(DynamicsController.class).error("Invalid Response Body");
				//  STACKTRACE AND GENERAL EXCEPTION HANDLING
				e.printStackTrace();
				return "redirect:/registerDynamics";
			} catch (IOException e) {
				e.printStackTrace();
				return "redirect:/registerDynamics";
			}
			if (dynamicsAccountRepository.findOneByResourceUrl(values.get(1)) == null) {
				DynamicsAccountEntity dae = new DynamicsAccountEntity();
				dae.setAccessToken(jsonObj.get("access_token").asText());
				dae.setRefreshToken(jsonObj.get("refresh_token").asText());
				dynamicsAccountRepository.save(dae);
			}
		}
		return "HMM";
	}
	
	@RequestMapping("/showDynamics")
	public String fetchDynamics() {
		return "default";
	}
}
