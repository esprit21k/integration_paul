package com.trumpia.data;

import com.trumpia.model.UserEntity;

import java.util.List;

import org.springframework.data.repository.*;

public interface UserRepository extends CrudRepository<UserEntity, Long>{
	
	List<UserEntity> findByApikeyIgnoreCase(String apikey);
	List<UserEntity> findByUniqueId(String id);
	List<UserEntity> findByUsername(String username);
	UserEntity findOneByUsername(String username);

}