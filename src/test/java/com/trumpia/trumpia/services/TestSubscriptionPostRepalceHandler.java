package com.trumpia.trumpia.services;

import org.junit.Before;
import org.junit.Test;

import com.trumpia.trumpia.model.TrumpiaAccountEntity;

public class TestSubscriptionPostRepalceHandler {
	TrumpiaAccountEntity trumpia;
	SubscriptionPostReplaceHandler replace;
	
	@Before
	public void setUp() {
		trumpia = new TrumpiaAccountEntity();
		trumpia.setApikey("0c900c585477152326c20ff757625926");
		trumpia.setUniqueId("JohnHan");
		replace =  new SubscriptionPostReplaceHandler(trumpia);
	}
	
	/*
	 *setup;  
	 * */
	
	@Test
	public void test() {
		
	}

}

