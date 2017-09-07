package com.trumpia.data;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.trumpia.model.UserEntity;

public interface UserRepository extends CrudRepository<UserEntity, Long>{	
	List<UserEntity> findByUsername(String username);
	UserEntity findOneByUsername(String username);
	@Query(nativeQuery=true,value="select count(*) FROM e_user WHERE LOWER(username) = LOWER(:username)")
	int getUserCountByUsername(@Param("username") String username);
	@Query(nativeQuery=true,value="select count(*) FROM e_user WHERE LOWER(email) = LOWER(:email)")
	int getUserCountByEmail(@Param("email") String email);
}