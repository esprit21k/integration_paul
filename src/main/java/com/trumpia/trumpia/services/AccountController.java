package com.trumpia.trumpia.services;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.trumpia.trumpia.data.TrumpiaAccountRepository;
import com.trumpia.trumpia.model.TrumpiaAccountEntity;

@RestController
@RequestMapping(path = "/trumpia/account")
public class AccountController {
	//trumpiaRepo	
	@Autowired
	private TrumpiaAccountRepository trumRepo;
	//put
	
	@RequestMapping(method = RequestMethod.PUT)
	public String putTrumpiaAccount(HttpServletRequest request,
			@RequestParam(value ="uniqueId") String uniqueId,
			@RequestParam(value ="APIKey") String APIKey,
			@RequestParam(value ="description") String description,
			@RequestParam(required = false) String baseURL
			) {
		
		TrumpiaAccountEntity account = new TrumpiaAccountEntity();
		account.setUniqueId(uniqueId);
		account.setAPIkey(APIKey);
		account.setDescription(description);
		//user Entity have to?
		
		if(baseURL != null)
			account.setBaseURL(baseURL);
		
		trumRepo.save(account);
		//Id available now
		return account.getId().toString();
	}
	//get
	@RequestMapping(method = RequestMethod.GET)
	public String getTrumpiaAccount(Pageable page) {
		Page<TrumpiaAccountEntity> fetchedPage = trumRepo.findAll(page);
		return fetchedPage.toString();
	}
}
