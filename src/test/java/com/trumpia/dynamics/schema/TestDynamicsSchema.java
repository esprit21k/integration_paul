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
	public void setUp() {
		try {
			RefreshAccessToken refreshAccessToken = new RefreshAccessToken("AQABAAAAAABnfiG-mA6NTae7CdWW7Qfd2cr918F76yI_lb2yH4UrcAZs61Uw9kFJrmEeBuqeCXnvrMC7tvF4BMMq8rMaT3sPeMkMUIdoJo8YRrOs8Hzk2fxn9OZfnfthUTX86hcspEkO4l2qwUTzUNh7nsNeyq-KqTrpnCysO1jl0MWx63ZUb5ji3U9CIIMsJxZwRTdocyRPUyte0A-K3hoNsCd7xv0mDoxOvdjMatn9ZszQYdRxzI4l86NEaroIKHqjrlha6OEs_z5c-sCpzsxBrxL_4foKkuciu2UHY4EN_IHTywzEidkpzdYOIXqJd4naRDBaEclpp9orhnX2aZPstzbbPK7PNZW5YA-WI6GPOO1wg8kf9JyUk19jecTBZNZSwIVClqOacDEnioZfooYVUwHAZRQFDh-OWNZAWuTC6UCaMoGddRMdm3HWnP3BivT_rDerz1rwu22HuMX0ihxjDs34mhLIm4fCwi8gTDSLm1RbcJh09eZ_hJ9v8S3rYGhpS5aq3Y6aE_DaGQS958X73srP0TKqku4XJutXXgyd-O7VemPFUQ0K0X_avmnHtFL4MOv9lOcmmHG5mKT3zfEWdlO4RMgEmwBvMB4niPU0W56BEHF9AG9dHWoBUNuBbRZaNL4fxShPolT38BG_giO9nhmiBLQl7d7Suheo9HcD7rTxXCu3e43qePTlIADD86Llw-WqCNkELaGkBIwe5Cbj9LqKKQFiCj0nzeaigicm_Wdijw7vQWTJxJMdRXMZdHDQVwKZWgEvCghNLyVuq2s0vCyuaqaVIAA", "https://trumpia.crm.dynamics.com");
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
	
	@Test
	public void testStoreDB() throws Exception {
		test.getDynamicsSchema(dynamicsAccountEntity);
		test.storeDynamicsSchemaDB();
		test.setPropertiesFromDB(test.dynamicsSchemaEntityLists);
		HashMap<String, String> properties = test.getProperties();
		assertTrue(properties.get("mobilephone").equals("Edm.String"));
		assertTrue(properties.get("spousesname").equals("Edm.String"));
		assertTrue(properties.get("merged").equals("Edm.Boolean"));
		System.out.println(test.propertiesToJSON().toString());
	}
	
	
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
