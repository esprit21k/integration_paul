package com.trumpia.util;

import org.springframework.beans.factory.annotation.Autowired;

import com.trumpia.data.UserRepository;
import com.trumpia.dynamics.data.DynamicsAccountRepository;
import com.trumpia.dynamics.model.DynamicsAccountEntity;
import com.trumpia.trumpia.data.TrumpiaAccountRepository;
import com.trumpia.trumpia.model.TrumpiaAccountEntity;


public class EntityUtil {
	@Autowired
	static TrumpiaAccountRepository trumpiaRepo;
	@Autowired
	static DynamicsAccountRepository dynamicRepo;
	@Autowired
	static UserRepository userRepo;
	
	public static TrumpiaAccountEntity findTrumpiaEntityByPrincipal() {
		return trumpiaRepo.findByUserEntity(userRepo.findOneByUsername(AuthenticationInfo.getAuthenticationPrincipal()));
	}
	public static DynamicsAccountEntity findDynamicEntityByPrincipal() {
		return dynamicRepo.findByUserEntity(userRepo.findOneByUsername(AuthenticationInfo.getAuthenticationPrincipal()));
	}
}
