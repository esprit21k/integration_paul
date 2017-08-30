package com.trumpia.trumpia.services;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.trumpia.trumpia.data.TrumpiaAccountRepository;
import com.trumpia.trumpia.model.TrumpiaAccountEntity;

@RestController
@RequestMapping(path = "/trumpia/account")
public class AccountController {
	//trumpiaRepo	
	@Autowired
	private TrumpiaAccountRepository trumRepo;

	/*
	 * INPUT:
	 {
		"uniqueID" : "testID1234",
		"APIKey" : "some-api-key-thing",
		"description" : "thisIsExample"
	 }
	 * OUTPUT: (fail)
	 *	{ "FAIL" : "testID1234 is already in Database" }
	 *
	 * OUTPUT: (success)
	 *  { "id" : "TrumpiaAccoutRepository-id"}
	 */
	//put	
	@RequestMapping(method = RequestMethod.PUT)
	public String putTrumpiaAccount(@RequestBody String input) {

		TrumpiaAccountEntity account = createTrumpiaAccount(new JSONObject(input));
		
		if(isAlreadyInDB(account))
			return failJSON(account);
		else {
			trumRepo.save(account);
			return successJSON(account);
		}
	}
	
	private TrumpiaAccountEntity createTrumpiaAccount(JSONObject parsed) {
		TrumpiaAccountEntity account = new TrumpiaAccountEntity();
		
		account.setAPIkey(parsed.getString("APIKey"));
		account.setDescription(parsed.getString("description"));
		account.setUniqueId(parsed.getString("uniqueID"));
		
		return account;
	}
	
	private boolean isAlreadyInDB(TrumpiaAccountEntity account) {
		if(trumRepo.findOneByApikey(account.getAPIkey()) != null && trumRepo.findOneByUniqueId(account.getUniqueId()) != null)
			return true;
		else
			return false;
	}
	
	private String failJSON(TrumpiaAccountEntity account) {
		JSONObject msg = new JSONObject();
		msg.put("FAIL", account.getUniqueId() + " is already in Database");
		
		return msg.toString();
	}
	
	private String successJSON(TrumpiaAccountEntity account) {
		JSONObject msg = new JSONObject();
		msg.put("id", account.getId().toString());
		return msg.toString();
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
		Page<TrumpiaAccountEntity> fetchedPage = trumRepo.findAll(new PageRequest(page.getPageNumber(), page.getPageSize(), Sort.Direction.ASC, "updatedDate"));	
		
		JSONObject info = new JSONObject();
		JSONArray data = new JSONArray();
		
		for(TrumpiaAccountEntity content : fetchedPage.getContent())
			data.put(contentJSON(content));
		
		info.put("page", page.getPageNumber()+1);
		info.put("data", data);
		return info.toString();
	}

	private JSONObject contentJSON(TrumpiaAccountEntity content) {
		JSONObject tmp = new JSONObject();
		tmp.put("description",content.getDescription());
		tmp.put("id",content.getUniqueId());
		return tmp;
	}
}
