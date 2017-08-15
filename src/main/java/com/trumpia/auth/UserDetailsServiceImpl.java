package com.trumpia.auth;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.trumpia.data.UserRepository;
import com.trumpia.model.UserEntity;

import static java.util.Collections.emptyList;

import java.util.List;

@Service
public class UserDetailsServiceImpl implements UserDetailsService{

	@Autowired
	private UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        List<UserEntity> applicationUsers = userRepository.findByUsername(username);
        if (applicationUsers == null || applicationUsers.size() == 0) {
            throw new UsernameNotFoundException(username);
        }
        UserEntity user = applicationUsers.get(0);
        return new User(user.getUsername(), user.getPassword(), emptyList());
    }

}
