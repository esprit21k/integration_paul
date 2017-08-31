package com.trumpia.trumpia.services;

import com.trumpia.trumpia.model.Subscription;
import com.trumpia.trumpia.model.TrumpiaAccountEntity;

public class UndeletingSubscriptionHandler implements DeletedSubscriptionHandler{

	TrumpiaAccountEntity trumpia;
	
	public UndeletingSubscriptionHandler(TrumpiaAccountEntity trumpia) {
		this.trumpia = trumpia;
	}
	@Override
	public void deleteSubscription(String subscriptionId) {
		//DO NOTHING
	}

}
