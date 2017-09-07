package com.trumpia.trumpia.services;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import com.trumpia.util.AuthenticationUtil;

@RunWith(PowerMockRunner.class)
@PrepareForTest(AuthenticationUtil.class)
public class TestTrumpiaAPIcaller {
	@InjectMocks
	TrumpiaAPIcaller caller;
	
	@Before
	public void setUp() {
		
		
	}
	
	@Test
	public void test() {
		
	}

}
