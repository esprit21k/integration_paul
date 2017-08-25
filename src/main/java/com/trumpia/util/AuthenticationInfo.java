package com.trumpia.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;

import com.trumpia.data.UserRepository;
import com.trumpia.dynamics.data.DynamicsAccountRepository;
import com.trumpia.dynamics.model.DynamicsAccountEntity;
import com.trumpia.trumpia.data.TrumpiaAccountRepository;
import com.trumpia.trumpia.model.TrumpiaAccountEntity;

public class AuthenticationInfo {
	public static String getAuthenticationPrincipal() {
		return SecurityContextHolder.getContext().getAuthentication().getName();
	}

}
