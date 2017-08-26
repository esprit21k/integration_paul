package com.trumpia.trumpia.services;

public class TrumpiaAPIcallerFactory {
	static SubscriptionPostHandler post(String option) {
		if(option.equals("update"))
			return new SubscriptionPostUpdatedHandler();
		if(option.equals("replace"))
			return new SubscriptionPostReplaceHandler();

		return null;
	}

	static DeletedSubscriptionHandler delete(String option) {
		if(option.equals("delete"))
			return new DeletingSubscriptionHandler();
		if(option.equals("undelete"))
			return new UndeletingSubscriptionHandler();

		return null;
	}
}
