package com.trumpia.data;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import com.trumpia.model.DeletedUserEntity;

public interface DeletedUserRepository extends PagingAndSortingRepository<DeletedUserEntity, Long>{
	DeletedUserEntity findOneByUsername(String username);
	Page<DeletedUserEntity> findOneByUsername(Pageable page, String username);
	@Query(nativeQuery=true,value="select count(*) FROM e_deleted_user WHERE LOWER(username) = LOWER(:username)")
	int getUserCountByUsername(@Param("username") String username);
	@Query(nativeQuery=true,value="select count(*) FROM e_deleted_user WHERE LOWER(email) = LOWER(:email)")
	int getUserCountByEmail(@Param("email") String email);
}
