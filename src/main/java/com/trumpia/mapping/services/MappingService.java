package com.trumpia.mapping.services;

import com.trumpia.mapping.model.MappingEntity;

public interface MappingService {
	
	public Iterable<MappingEntity> listMappingInfoByUserId(Long userid);
		
	public MappingEntity saveMappingInfo(MappingEntity mappingInfo);
	
	public void deleteMappingInfoByDescription(String description); 
}
