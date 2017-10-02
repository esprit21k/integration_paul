package com.trumpia.trumpia.data;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.trumpia.model.UserEntity;
import com.trumpia.trumpia.model.TrumpiaAccountEntity;

public interface TrumpiaAccountRepository extends PagingAndSortingRepository<TrumpiaAccountEntity, Long>{
	TrumpiaAccountEntity findByUserEntity(UserEntity entity);
	TrumpiaAccountEntity findByUniqueId(String uniqueId);
}
