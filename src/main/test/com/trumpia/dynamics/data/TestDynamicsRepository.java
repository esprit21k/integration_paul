package com.trumpia.dynamics.data;



import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.util.Date;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.trumpia.data.UserRepository;
import com.trumpia.dynamics.data.DynamicsAccountRepository;
import com.trumpia.dynamics.data.DynamicsRepositoryConfig;
import com.trumpia.dynamics.model.DynamicsAccountEntity;
import com.trumpia.model.UserEntity;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = {DynamicsRepositoryConfig.class})
@ActiveProfiles("dev")
public class TestDynamicsRepository {
	
	@Autowired 
	private UserRepository userRepo;
	
	@Autowired 
	private DynamicsAccountRepository dynamicsRepo;
	
	@Autowired
	public void setUserRepository(UserRepository userRepo) {
		this.userRepo = userRepo;
	}

	@Autowired
	public void setDynamicsRepository(DynamicsAccountRepository dynamicsRepo) {
		this.dynamicsRepo = dynamicsRepo;
	}

	private UserEntity userEntity;

	@Before
	public void setUp() {

	}

	@Test
	public void test() {
		//create
		userEntity = new UserEntity();
		userEntity.setEmail("test@mytrum.com");
		userEntity.setUsername("test");
		userEntity.setUniqueId("test");
		userEntity.setPassword("test");
		userEntity.setUpdatedDate(new Date());
		userRepo.save(userEntity);

		DynamicsAccountEntity dynamicsEntity = new DynamicsAccountEntity();
		dynamicsEntity.setResourceUrl("test");
		dynamicsEntity.setAccessToken("test");
		dynamicsEntity.setRefreshToken("test");
		dynamicsEntity.setRefreshToken("test");
		dynamicsEntity.setUserEntity(userEntity);
		
		
		//Save
		
		assertNull(dynamicsEntity.getId());
		dynamicsRepo.save(dynamicsEntity);
		assertNotNull(dynamicsEntity.getId());

		//Fetch
		DynamicsAccountEntity fetchedEntity = dynamicsRepo.findOne(dynamicsEntity.getId());
		assertEquals(dynamicsEntity.getId(), fetchedEntity.getId());

		//Update
		fetchedEntity.setAccessToken("updated");
		dynamicsRepo.save(fetchedEntity);
		DynamicsAccountEntity fetchedAndUpdatedEntity = dynamicsRepo.findOne(fetchedEntity.getId());
		assertEquals(fetchedEntity.getAccessToken(), fetchedAndUpdatedEntity.getAccessToken());

		//Delete
		dynamicsRepo.delete(fetchedEntity);
		assertNull(dynamicsRepo.findOne(fetchedEntity.getId()));
	}
}

