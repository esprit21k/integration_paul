package com.trumpia.mapping.services;

import org.springframework.beans.factory.annotation.Autowired;

import com.trumpia.mapping.data.MappingRepository;
import com.trumpia.mapping.model.MappingEntity;

public class MappingServiceImpl implements MappingService{

	private MappingRepository mappingRepo;
	
	@Autowired
	public void setProductRepository( MappingRepository mappingRepo) {
		this.mappingRepo = mappingRepo;
	}
	
	@Override
	public Iterable<MappingEntity> listMappingInfoByUserId(Long userid) {

		return null;
	}

	@Override
	public MappingEntity saveMappingInfo(MappingEntity mappingInfo) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void deleteMappingInfoByDescription(String description) {
		// TODO Auto-generated method stub
		
	}

}
