package com.trumpia.dynamics.schema.data;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.trumpia.dynamics.model.DynamicsAccountEntity;
import com.trumpia.dynamics.schema.model.DynamicsSchemaEntity;

public interface DynamicsSchemaRepository extends CrudRepository<DynamicsSchemaEntity, Long>{
	@Query(nativeQuery=true,value="select count(*) FROM e_dynamics_schema WHERE LOWER(name) = LOWER(:name)")
	int getSchemafieldCountByName(@Param("name") String name);
	List<DynamicsSchemaEntity> findByDynamicsAccountEntity(DynamicsAccountEntity dynamicsAccountEntity);
//	DynamicsSchemaEntity FindAll();
}
