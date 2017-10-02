package com.trumpia.trumpia.inbound.services;

import com.trumpia.trumpia.inbound.model.InboundInfo;
import com.trumpia.trumpia.model.TrumpiaAccountEntity;

public class InboundInfoHandler {
	private TrumpiaAccountEntity trumpiaAccount;
	private InboundInfo inboundInfo;
	
	public InboundInfoHandler(TrumpiaAccountEntity trumpiaAccount, InboundInfo inboundInfo) {
		this.trumpiaAccount = trumpiaAccount;
		this.inboundInfo = inboundInfo;
	}
	
	public void checkTargetCompany() {
		
	}
}
