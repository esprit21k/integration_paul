package com.trumpia.data;

import com.trumpia.model.UserEntity;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.*;
import org.springframework.data.repository.query.Param;

public interface UserRepository extends CrudRepository<UserEntity, Long>{
	
	List<UserEntity> findByUniqueId(String id);
	List<UserEntity> findByUsername(String username);
	UserEntity findOneByUsername(String username);
	@Query(nativeQuery=true,value="select count(*) FROM e_user WHERE LOWER(username) = LOWER(:username)")
	int getUserCountByUsername(@Param("username") String username);
	@Query(nativeQuery=true,value="select count(*) FROM e_user WHERE LOWER(email) = LOWER(:email)")
	int getUserCountByEmail(@Param("email") String email);
}