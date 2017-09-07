package com.trumpia.trumpia.services;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.trumpia.data.UserRepository;
import com.trumpia.model.UserEntity;
import com.trumpia.trumpia.data.TrumpiaAccountRepository;
import com.trumpia.trumpia.model.TrumpiaAccountEntity;
import com.trumpia.util.APIResponse;
import com.trumpia.util.JSONUtils;

@RestController
@RequestMapping(path = "/trumpia/account")
public class AccountController {
	//trumpiaRepo	
	@Autowired
	private TrumpiaAccountRepository trumRepo;
	@Autowired
	private UserRepository userRepo;
	
	private UserEntity user = userRepo.findOneByUsername(SecurityContextHolder.getContext().getAuthentication().getName()); 
	/*
	 * INPUT:
	 {
		"uniqueID" : "testID1234",
		"APIKey" : "some-api-key-thing",
		"description" : "thisIsExample"
	 }
	 */

	@RequestMapping(method = RequestMethod.PUT)
	public String putTrumpiaAccount(@RequestBody String input) {
		TrumpiaAccountEntity account = new TrumpiaAccountEntity(new JSONObject(input));
		account.setUserEntity(user);
		//valid check
		if(!TrumpiaAPILibrary.validCheck(account))
			return authenticationFailJSON();
		else if(isAlreadyInDB(account))
			return failJSON(account);
		else {
			trumRepo.save(account);
			return successJSON(account);
		}
	}

	private boolean isAlreadyInDB(TrumpiaAccountEntity account) {
		if(trumRepo.findOneByApikey(account.getApikey()) != null && trumRepo.findOneByUniqueId(account.getUniqueId()) != null)
			return true;
		else
			return false;
	}

	private String authenticationFailJSON() {
		APIResponse response = new APIResponse();
		response.setError(true);
		response.setMessage("Requested information failed to be authenticated.");

		return response.getJSONResponse();
	}

	private String failJSON(TrumpiaAccountEntity account) {
		APIResponse response = new APIResponse();
		response.setError(true);
		response.setMessage("Account already registerd: " + account.getUniqueId());

		return response.getJSONResponse();
	}

	private String successJSON(TrumpiaAccountEntity account) {
		APIResponse response = new APIResponse();
		response.setError(false);
		response.setMessage("success");

		try {
			String id = "{\"id\":\"" + account.getId().toString()+"\"}";
			response.setData(JSONUtils.stringToJSON(id));
			return response.getJSONResponse();
		} catch (Exception e) {
			APIResponse fail = new APIResponse();
			fail.setError(true);
			fail.setMessage("JSON parsing error");
			return fail.getJSONResponse();
		}
	}

	//get
	/*
	 * Request URL example : /trumpia/account?size=2&page=1
	 * (page starts with index 0)
	 * RESPONSE:  
	 * {
		    "data": [
		        {
		            "description": "test",
		            "id": "test2"
		        },
		        {
		            "description": "test",
		            "id": "test2"
		        }
		    ],
		    "page": 2
		}
	 */
	@RequestMapping(method = RequestMethod.GET) // ?size=2&page=0 (page starts with index 0)
	public String getTrumpiaAccount(Pageable page) {
		Page<TrumpiaAccountEntity> fetchedPage = trumRepo.findByUserEntity(new PageRequest(page.getPageNumber(), page.getPageSize(), Sort.Direction.ASC, "updatedDate"), user);	

		JSONObject info = new JSONObject();
		JSONArray data = new JSONArray();

		for(TrumpiaAccountEntity content : fetchedPage.getContent())
			data.put(content.IdAndDescriptionJSON());

		info.put("page", page.getPageNumber()+1);
		info.put("data", data);
		return info.toString();
	}
}
