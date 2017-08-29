package com.trumpia.trumpia.services;

import com.trumpia.trumpia.model.TrumpiaAccountEntity;

public class TrumpiaAPIcallerFactory {
	static SubscriptionPostHandler post(String option, TrumpiaAccountEntity trumpia) {
		if(option.equals("update"))
			return new SubscriptionPostUpdatedHandler(trumpia);
		if(option.equals("replace"))
			return new SubscriptionPostReplaceHandler(trumpia);

		return null;
	}

	static DeletedSubscriptionHandler delete(String option, TrumpiaAccountEntity trumpia) {
		if(option.equals("delete"))
			return new DeletingSubscriptionHandler(trumpia);
		if(option.equals("undelete"))
			return new UndeletingSubscriptionHandler(trumpia);

		return null;
	}
}
