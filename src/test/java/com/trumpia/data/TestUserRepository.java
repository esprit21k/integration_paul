package com.trumpia.data;

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

import com.trumpia.Main;
import com.trumpia.model.UserEntity;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = {Main.class})
@ActiveProfiles("dev")
public class TestUserRepository {
	@Autowired 
	private UserRepository repo;

	@Autowired
	public void setUserRepository(UserRepository repo) {
		this.repo = repo;
	}

	private UserEntity testEntity;

	@Before
	public void setUp() {

	}

	@Test
	public void test() {
		//create
		testEntity = new UserEntity();
		testEntity.setEmail("test@mytrum.com");
		testEntity.setUsername("test");
		testEntity.setPassword("test");
		testEntity.setUpdatedDate(new Date());

		//Save
		assertNull(testEntity.getId());
		repo.save(testEntity);
		assertNotNull(testEntity.getId());

		//Fetch
		UserEntity fetchedEntity = repo.findOne(testEntity.getId());
		assertEquals(testEntity.getId(), fetchedEntity.getId());

		//Update
		fetchedEntity.setEmail("Updated one");
		repo.save(fetchedEntity);
		UserEntity fetchedAndUpdatedEntity = repo.findOne(fetchedEntity.getId());
		assertEquals(fetchedEntity.getEmail(), fetchedAndUpdatedEntity.getEmail());

		//Delete
		repo.delete(fetchedEntity);
		assertNull(repo.findOne(fetchedEntity.getId()));
	}
}
