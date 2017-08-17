package com.trumpia;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.trumpia.auth.JWTAuthenticationFilter;
import com.trumpia.auth.JWTAuthorizationFilter;
import com.trumpia.auth.SecurityHandler;
import com.trumpia.data.UserRepository;
import org.springframework.http.HttpMethod;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter{
 
	
	@Autowired
    private UserDetailsService userDetailsService;
    
    
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;;
 
    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
 
        // Users in memory.
 
 
    }
 
    @Override
	public void configure(WebSecurity web) throws Exception {
 
    	web.ignoring().antMatchers("/static/**");
        /*
        // The pages does not require login
        http.authorizeRequests().antMatchers("/", "/login", "/logout").permitAll()
        .anyRequest().authenticated();
 
        http.authorizeRequests().and().exceptionHandling().accessDeniedPage("/403");
 
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
 
    @Override
    protected void configure(HttpSecurity http) throws Exception {
 
        http.csrf().disable().authorizeRequests()
        	.antMatchers("/").permitAll()
        	.antMatchers("/login").permitAll()
        	.antMatchers("/signup").permitAll()
        	.anyRequest().authenticated()
        	.and()
        	.addFilter(new JWTAuthenticationFilter(authenticationManager()))
            .addFilter(new JWTAuthorizationFilter(authenticationManager()));
        /*
        // The pages does not require login
        http.authorizeRequests().antMatchers("/", "/login", "/logout").permitAll()
        .anyRequest().authenticated();
 
        http.authorizeRequests().and().exceptionHandling().accessDeniedPage("/403");
 
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
    
    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder);
    }
}
