package com.trumpia;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import com.trumpia.data.UserRepository;
import com.trumpia.security.UserAuthenticationService;
@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter{
 
    
    @Autowired
    UserRepository myUserRepository;
    
    @Autowired
    UserAuthenticationService userAuthService;
 
    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
 
        // Users in memory.
 
        // For User in database.
        auth.userDetailsService(userAuthService);
 
    }
 
    @Override
    protected void configure(HttpSecurity http) throws Exception {
 
        http.csrf().disable();
 
        // The pages does not require login
        http.authorizeRequests().antMatchers("/", "/login", "/logout").permitAll()
        .anyRequest().authenticated();
 
        http.authorizeRequests().and().exceptionHandling().accessDeniedPage("/403");
 /*
        http.authorizeRequests().and().formLogin()	
                .loginPage("/login")//
                .defaultSuccessUrl("/registerDynamics")//
                .failureUrl("/login?error=true")//
                .usernameParameter("username")//
                .passwordParameter("APIKey")
                // Config for Logout Page
                .and().logout().logoutUrl("/logout").logoutSuccessUrl("/logoutSuccessful");
 */
 
    }
}
