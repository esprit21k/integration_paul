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
}
