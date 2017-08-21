package com.trumpia.account.data;

import org.springframework.data.repository.CrudRepository;

import com.trumpia.account.model.TrumpiaAccountEntity;
import com.trumpia.model.UserEntity;

public interface TrumpiaAccountRepository extends CrudRepository<TrumpiaAccountEntity, Long>{
	TrumpiaAccountEntity findByUserEntity(UserEntity entity);
}
