package com.trumpia.trumpia.services;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.trumpia.Main;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = {Main.class})
@ActiveProfiles("dev")
public class TestAccountController {
	@LocalServerPort 
	private int port;
	
	@Test
	public void test() {
		String url = "http://localhost:" + String.valueOf(port) + "/trumpia/account";
	}
}
