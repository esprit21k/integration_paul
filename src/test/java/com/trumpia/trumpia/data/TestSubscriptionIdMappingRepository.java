package com.trumpia.trumpia.data;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.trumpia.Main;
import com.trumpia.trumpia.model.SubscriptionIdMappingEntity;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = {Main.class})
@ActiveProfiles("dev")
public class TestSubscriptionIdMappingRepository {

	@Autowired
	SubscriptionIdMappingRepository repo;
	@Autowired
	public void setRepository(SubscriptionIdMappingRepository repo) {
		this.repo = repo;
	}
	@Test
	public void test() {
		SubscriptionIdMappingEntity test = new SubscriptionIdMappingEntity("trumpia", "target");
		
		//save
		assertNull(test.getId());
		repo.save(test);
		assertNotNull(test.getId());
		
		//fetch
		SubscriptionIdMappingEntity fetchedEntity = repo.findOne(test.getId());
		assertEquals(test.getId(), fetchedEntity.getId());

		//Update
		fetchedEntity.setTargetSubscriptionId("changed");
		repo.save(fetchedEntity);
		SubscriptionIdMappingEntity fetchedAndUpdatedEntity = repo.findOne(fetchedEntity.getId());
		assertEquals(fetchedEntity.getTargetSubscriptionId(), fetchedAndUpdatedEntity.getTargetSubscriptionId());

		//Delete
		repo.delete(fetchedEntity);
		assertNull(repo.findOne(fetchedEntity.getId()));
	}

}
