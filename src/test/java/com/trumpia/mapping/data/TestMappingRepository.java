package com.trumpia.mapping.data;

import static org.junit.Assert.*;

import java.util.Date;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.trumpia.Main;
import com.trumpia.data.UserRepository;
import com.trumpia.mapping.model.MappingEntity;
import com.trumpia.model.UserEntity;


@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = {Main.class})
@ActiveProfiles("dev")
public class TestMappingRepository {

	@Autowired 
	private UserRepository userRepo;

	@Autowired 
	private MappingRepository mappingRepo;

	@Autowired
	public void setUserRepository(UserRepository userRepo) {
		this.userRepo = userRepo;
	}

	@Autowired
	public void setMappingRepository(MappingRepository mappingRepo) {
		this.mappingRepo = mappingRepo;
	}

	private UserEntity userEntity;
	private MappingEntity mappingEntity;

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

		mappingEntity = new MappingEntity();
		mappingEntity.setDescription("description");
		mappingEntity.setTrumpiaFieldName("trumpiaFieldName");
		mappingEntity.setTargetFieldName("serviceFieldName");
		mappingEntity.setTargetCompany("Dynamics");
		mappingEntity.setUserId(userEntity);

		//save
		assertNull(mappingEntity.getId());
		mappingRepo.save(mappingEntity);
		assertNotNull(mappingEntity.getId());

		//Fetch
		MappingEntity fetchedEntity = mappingRepo.findOne(mappingEntity.getId());
		assertEquals(mappingEntity.getId(), fetchedEntity.getId());
		
		//Update
		fetchedEntity.setDescription("updated");
		mappingRepo.save(fetchedEntity);
		MappingEntity fetchedAndUpdatedEntity = mappingRepo.findOne(fetchedEntity.getId());
		assertEquals(fetchedEntity.getDescription(), fetchedAndUpdatedEntity.getDescription());
		
		//Delete
		mappingRepo.delete(fetchedEntity);
		assertNull(mappingRepo.findOne(fetchedEntity.getId()));
	}

}
