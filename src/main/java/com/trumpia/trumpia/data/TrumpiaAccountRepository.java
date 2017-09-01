package com.trumpia.trumpia.data;

import org.springframework.data.repository.CrudRepository;

import com.trumpia.model.UserEntity;
import com.trumpia.trumpia.model.TrumpiaAccountEntity;

public interface TrumpiaAccountRepository extends CrudRepository<TrumpiaAccountEntity, Long>{
	TrumpiaAccountEntity findByUserEntity(UserEntity entity);
}
