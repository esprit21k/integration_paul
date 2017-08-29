package com.trumpia.trumpia.services;

import com.trumpia.trumpia.model.Subscription;
import com.trumpia.trumpia.model.TrumpiaAccountEntity;

public class DeletingSubscriptionHandler implements DeletedSubscriptionHandler{
	TrumpiaAccountEntity trumpia;
		
	public DeletingSubscriptionHandler(TrumpiaAccountEntity trumpia) {
		this.trumpia = trumpia;
	}
	
	@Override
	public void deleteSubscription(Subscription subscription) {
		// TODO Auto-generated method stub
		
	}
	

}
