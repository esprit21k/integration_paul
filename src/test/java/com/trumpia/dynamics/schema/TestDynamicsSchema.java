package com.trumpia.dynamics.schema;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.trumpia.Main;
import com.trumpia.data.UserRepository;
import com.trumpia.dynamics.APIcaller.RefreshAccessToken;
import com.trumpia.dynamics.data.DynamicsAccountRepository;
import com.trumpia.dynamics.model.DynamicsAccountEntity;
import com.trumpia.model.UserEntity;
import static com.trumpia.util.LogUtils.getLogger;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = {Main.class})
@ActiveProfiles("dev")
public class TestDynamicsSchema {
	@Autowired 
	private UserRepository userRepo;
		
	@Autowired
	private DynamicsAccountRepository dynamicsRepo;
	
	@Autowired 
	private DynamicsSchema test;
	
	private UserEntity userEntity;
	private DynamicsAccountEntity dynamicsAccountEntity;
	private String accessToken;
	
	@Before
	public void setUp() throws Exception {
		try {
			RefreshAccessToken refreshAccessToken = new RefreshAccessToken("AQABAAAAAAA9kTklhVy7SJTGAzR-p1Bcl8fxzcqzJ86soQzO6HFaKRFbWlxEziD0oFFt5me91HenV11FoohDAtoWAnEmEMy5xQq9ScNzzBzWG4_-squanBKAvYzAr3XssGGw73Ha8j93p2DV6uk0N-nta8a8H1yL1O66bR6V8jPlmUcCu3-PD7t4JLxi5Ml5yaRM5ZHbg6fbFQ3bx9wiPMzJSyW7BZr7PiG8ywIQ4XRTV5sCQSrGo5yFIobw16m7BXznKEgFmIAGqixZF0E9ElQ3Dg7YVfWBt7nX-jwVy7Lf2qJ2i4Cr_x1F67_Wc2jhEApZoduqbIRIZ6rQJBUpjbVW1yFTgdB6j6Sxd0qqHBbTGSE_2S5jQB5W3ejFEjZ2ZtliqnfzWkEsbcf_6zcnBldkziQI5DJBWisQyyuQ6QpPzZ6kxhyROLdPvVpTwjEbv_av7Hu1uGeYHKxpRbKXHB7UcHFXE8MSg_DFiuhNVaypjmYyg8piqX0HeBk2moaiKCApuY4Ngn9yox-oNpJDc6mb3Hc9ayTiZaaBVsbej5cndfndBivfWJ_tshnoOWRkaFugmW9JiYuAql-M4zgUZy3rCKkjM9eaNcK_-dg-X5KJyjIvQNTK35sgNF2Usn8Finsd8JqrDgiUpFyx8t7kEtZC4lUO3VkB-bnT1mN2zSID9T4Ji0wN2pJ1HzMJlXX7V9QhGE1NWA1Wm2r1uS14n6hKI1qhnOGfG6yJJVlp89BPpeXR5R9_SBjk5yw7FrSdUee4DInYdG2uwCrsrRFgeGGg8wuESr-vIAA", "https://trumpia.crm.dynamics.com");
			refreshAccessToken.updateToken();	
			accessToken = refreshAccessToken.accessToken;
			makeEntity();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Test
	public void testProperties() throws Exception {
		test.getDynamicsSchema(dynamicsAccountEntity);
		HashMap<String, String> properties = test.getProperties();
		getLogger(TestDynamicsSchema.class).error("accessToken: "+accessToken);
		assertTrue(properties.get("mobilephone").equals("Edm.String"));
		assertTrue(properties.get("spousesname").equals("Edm.String"));
		assertTrue(properties.get("merged").equals("Edm.Boolean"));
		System.out.println(test.propertiesToJSON().toString());
//		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//		System.out.println(authentication.toString());
	}
	
//	@Test
//	public void testStoreDB() throws Exception {
//		test.getDynamicsSchema(dynamicsAccountEntity);
//		test.storeDynamicsSchemaDB();
//		test.setPropertiesFromDB(test.dynamicsSchemaEntityLists);
//		HashMap<String, String> properties = test.getProperties();
//		assertTrue(properties.get("mobilephone").equals("Edm.String"));
//		assertTrue(properties.get("spousesname").equals("Edm.String"));
//		assertTrue(properties.get("merged").equals("Edm.Boolean"));
//		System.out.println(test.propertiesToJSON().toString());
//	}
	
	
	private void makeEntity() {
		userEntity = new UserEntity();
		userEntity.setEmail("test@mytrum.com");
		userEntity.setUsername("test");
		userEntity.setUniqueId("test");
		userEntity.setPassword("test");
		userEntity.setUpdatedDate(new Date());
		userRepo.save(userEntity);
		
		dynamicsAccountEntity = new DynamicsAccountEntity();
		dynamicsAccountEntity.setAccessToken(accessToken);
		dynamicsAccountEntity.setRefreshToken("refreshToken");
		dynamicsAccountEntity.setResourceUrl("https://trumpia.crm.dynamics.com");
		dynamicsAccountEntity.setUserEntity(userEntity);
		dynamicsRepo.save(dynamicsAccountEntity);
	}

}
