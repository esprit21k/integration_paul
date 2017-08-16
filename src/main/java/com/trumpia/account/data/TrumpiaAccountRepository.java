package com.trumpia.account.data;

import org.springframework.data.repository.CrudRepository;

import com.trumpia.account.model.TrumpiaAccountEntity;

public interface TrumpiaAccountRepository extends CrudRepository<TrumpiaAccountEntity, Long>{

}
