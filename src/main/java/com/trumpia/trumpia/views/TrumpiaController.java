package com.trumpia.trumpia.views;

import java.io.IOException;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.trumpia.data.UserRepository;
import com.trumpia.model.UserEntity;
import com.trumpia.trumpia.data.TrumpiaAccountRepository;
import com.trumpia.trumpia.model.TrumpiaAccountEntity;
import com.trumpia.trumpia.schema.ContactSchema;


@RestController
@RequestMapping(path = "/trumpia")
public class TrumpiaController {
	
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private TrumpiaAccountRepository trumpiaAccountRepository;
	
	//GET schema
	@RequestMapping(path="/{username}/schema", method = RequestMethod.GET)
	public String getTrumpiaSchema(@PathVariable String username) throws Exception {
		List<UserEntity> userEntityList = userRepository.findByUniqueId(username);
		//unique id must return one entity
		UserEntity userEntity = userEntityList.get(0);
		TrumpiaAccountEntity fetchedTrumpia = trumpiaAccountRepository.findByUserEntity(userEntity);
		
		String APIkey = fetchedTrumpia.getAPIkey();
		String user = fetchedTrumpia.getUniqueId();
		String baseURL = fetchedTrumpia.getBaseURL();
		
		ContactSchema trumpiaUserSchema = new ContactSchema(APIkey, user, baseURL);
		
		JSONObject trumpiaUserSchemaJSON = null;
		
		try {
			trumpiaUserSchemaJSON = trumpiaUserSchema.getContactSchema();
		} catch (JSONException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return trumpiaUserSchemaJSON.toString();
	}

	/*//Subscription
	@RequestMapping(path="/{username}/subscription", method = RequestMethod.PUT)
	public String putSubscription() {
		
	}*/
	
	
}
