package com.trumpia.trumpia.data;

import org.springframework.data.repository.CrudRepository;

import com.trumpia.trumpia.model.SubscriptionIdMappingEntity;

public interface SubscriptionIdMappingRepository extends CrudRepository<SubscriptionIdMappingEntity, Long>{
	SubscriptionIdMappingEntity findOneBySubscriptionId(String subscriptionId);
	SubscriptionIdMappingEntity findOneByTargetSubscriptionId(String targetId);
}
