package com.trumpia.connection.data;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import com.trumpia.connection.model.ConnectionEntity;
import com.trumpia.dynamics.model.DynamicsAccountEntity;
import com.trumpia.model.UserEntity;
import com.trumpia.trumpia.model.TrumpiaAccountEntity;

public interface ConnectionRepository extends PagingAndSortingRepository<ConnectionEntity, Long>{
	ConnectionEntity findOneByConnectionName(UserEntity userEntity, String connection_id);
	
	@Query(nativeQuery=true,value="select count(*) FROM e_connection WHERE LOWER(connection_id) = LOWER(:connection_id)")
	int getConnectionCountByName(@Param("connection_id") String connectionName);
	
	Page<ConnectionEntity> findPageByUserEntity(Pageable page, UserEntity entity);
}
