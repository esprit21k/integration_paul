package com.trumpia.security;

import java.util.HashSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.trumpia.data.UserRepository;
import com.trumpia.model.UserEntity;

@Service
public class UserAuthenticationService implements UserDetailsService{
    
    @Autowired
    UserRepository myUserRepository;
    
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity userInfo = myUserRepository.findOneByUsername(username);
        if (userInfo == null) {
            throw new UsernameNotFoundException("User " + username + " was not found in the database");
        }
        HashSet<GrantedAuthority> fakeRoles = new HashSet<GrantedAuthority>();
        fakeRoles.add(new SimpleGrantedAuthority("USER"));
         
        UserDetails userDetails = (UserDetails) new User(userInfo.getUsername(), //
                userInfo.getApikey(), fakeRoles);
 
        return userDetails;
    }

}
