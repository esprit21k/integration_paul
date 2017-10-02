package com.trumpia.trumpia.inbound.views;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.trumpia.trumpia.data.TrumpiaAccountRepository;
import com.trumpia.trumpia.inbound.model.InboundInfo;
import com.trumpia.trumpia.inbound.services.InboundInfoHandler;
import com.trumpia.trumpia.inbound.services.InboundParser;
import com.trumpia.trumpia.model.TrumpiaAccountEntity;

@RestController
@RequestMapping(path = "{username}/inbound")
public class InboundController {
	@Autowired
	private TrumpiaAccountRepository trumRepo;

	private InboundInfo inboundInfo;

	@RequestMapping(method = RequestMethod.GET)
	public void handleInboundMessage(@PathVariable String username, @RequestParam("xml") String query) throws Exception {
    	if (query.isEmpty())
    		return;
    	TrumpiaAccountEntity trumpiaAccount = trumRepo.findOneByUsername(username);
    	InboundInfoHandler inboundHandler = new InboundInfoHandler(trumpiaAccount, inboundInfo);
    	InboundParser parser = new InboundParser(query);
    	inboundInfo = new InboundInfo();
    	inboundInfo = parser.inboundInfo;
    	inboundHandler.checkTargetCompany();
	}
}
