package com.trumpia.trumpia.services;

import com.trumpia.trumpia.model.Subscription;

public interface SubscriptionPostHandler {
	public void postSubscription(Subscription subscription, String list);
}
