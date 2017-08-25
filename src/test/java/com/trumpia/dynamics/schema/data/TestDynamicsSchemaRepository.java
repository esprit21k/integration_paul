package com.trumpia.dynamics.schema.data;

import static org.junit.Assert.*;

import java.util.Date;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.trumpia.data.UserRepository;
import com.trumpia.dynamics.data.DynamicsAccountRepository;
import com.trumpia.dynamics.model.DynamicsAccountEntity;
import com.trumpia.dynamics.schema.model.DynamicsSchemaEntity;
import com.trumpia.model.UserEntity;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = {DynamicsSchemaRepositoryConfig.class})
@ActiveProfiles("dev")
public class TestDynamicsSchemaRepository {
	
	@Autowired 
	private UserRepository userRepo;
	
	@Autowired
	private DynamicsSchemaRepository dynamicsSchemaRepo;
	
	@Autowired
	private DynamicsAccountRepository dynamicsRepo;
	
	@Autowired
	public void setUserRepository(UserRepository userRepo) {
		this.userRepo = userRepo;
	}
	
	@Autowired
	public void setDynamicsSchemaRepository(DynamicsSchemaRepository dynamicsSchemaRepo) {
		this.dynamicsSchemaRepo = dynamicsSchemaRepo;
	}
	
	@Autowired
	public void setDynamicsAccountRepository(DynamicsAccountRepository dynamicsRepo) {
		this.dynamicsRepo = dynamicsRepo;
	}

	private UserEntity userEntity;
	private DynamicsAccountEntity dynamicsAccountEntity;
	
	@Test
	public void test() {
		userEntity = new UserEntity();
		userEntity.setEmail("test@mytrum.com");
		userEntity.setUsername("test");
		userEntity.setUniqueId("test");
		userEntity.setPassword("test");
		userEntity.setUpdatedDate(new Date());
		userRepo.save(userEntity);
		
		dynamicsAccountEntity = new DynamicsAccountEntity();
		dynamicsAccountEntity.setAccessToken("accessToken");
		dynamicsAccountEntity.setRefreshToken("refreshToken");
		dynamicsAccountEntity.setResourceUrl("resourceUrl");
		dynamicsAccountEntity.setUserEntity(userEntity);
		dynamicsRepo.save(dynamicsAccountEntity);
		
		DynamicsSchemaEntity dynamicsSchemaEntity = new DynamicsSchemaEntity();
		dynamicsSchemaEntity.setName("name");
		dynamicsSchemaEntity.setType("type");
		dynamicsSchemaEntity.setDynamicsAccountEntity(dynamicsAccountEntity);
		
		//Save
		assertNull(dynamicsSchemaEntity.getId());
		dynamicsSchemaRepo.save(dynamicsSchemaEntity);
		assertNotNull(dynamicsSchemaEntity.getId());

		//Fetch
		DynamicsSchemaEntity fetchedEntity = dynamicsSchemaRepo.findOne(dynamicsSchemaEntity.getId());
		assertEquals(dynamicsSchemaEntity.getId(), fetchedEntity.getId());

		//Update
		fetchedEntity.setType("updated");
		dynamicsSchemaRepo.save(fetchedEntity);
		DynamicsSchemaEntity fetchedAndUpdatedEntity = dynamicsSchemaRepo.findOne(fetchedEntity.getId());
		assertEquals(fetchedEntity.getType(), fetchedAndUpdatedEntity.getType());

		//Delete
		dynamicsSchemaRepo.delete(fetchedEntity);
		assertNull(dynamicsSchemaRepo.findOne(fetchedEntity.getId()));
	}

}
