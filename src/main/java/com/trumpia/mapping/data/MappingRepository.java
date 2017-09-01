package com.trumpia.mapping.data;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.trumpia.mapping.model.MappingEntity;
import com.trumpia.model.UserEntity;

public interface MappingRepository extends CrudRepository<MappingEntity, Long> {
	List<MappingEntity> findByUserId(UserEntity userId);
	List<MappingEntity> findByDescriptionAndUserId(String description, UserEntity userId);
}
