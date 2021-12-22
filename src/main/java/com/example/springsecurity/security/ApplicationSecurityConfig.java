package com.example.springsecurity.security;

import static com.example.springsecurity.security.ApplicationUserRole.ADMIN;
import static com.example.springsecurity.security.ApplicationUserRole.ADMINTRAINEE;
import static com.example.springsecurity.security.ApplicationUserRole.STUDENT;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class ApplicationSecurityConfig extends WebSecurityConfigurerAdapter {
	
	private final PasswordEncoder passwordEncoder;
	
	@Autowired
	public ApplicationSecurityConfig(PasswordEncoder passwordEncoder)
	{
		this.passwordEncoder=passwordEncoder;
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception
	{
		http
			.csrf().disable()
			.authorizeRequests()
			.antMatchers("/","index","/css/*","/js/*").permitAll()
			.antMatchers("/api/**").hasRole(ApplicationUserRole.STUDENT.name())
//			.antMatchers(HttpMethod.DELETE,"/management/api/**").hasAuthority(COURSE_WRITE.getPermission())
//			.antMatchers(HttpMethod.POST,"/management/api/**").hasAuthority(COURSE_WRITE.getPermission())
//			.antMatchers(HttpMethod.PUT,"/management/api/**").hasAuthority(COURSE_WRITE.getPermission())
//			.antMatchers(HttpMethod.GET,"/management/api/**").hasAnyRole(ADMIN.name(),ADMINTRAINEE.name())
			.anyRequest()
			.authenticated()
			.and()
			.formLogin()
			.loginPage("/login").permitAll()
			.defaultSuccessUrl("/home",true)
			.and()
			.rememberMe();
	}

	@Override
	@Bean
	protected UserDetailsService userDetailsService() {
		
		UserDetails jagadeeshUser = User
										.builder()
										.username("Jagadeesh")
										.password(passwordEncoder.encode("Jagadeesh"))
//										.roles(STUDENT.name())
										.authorities(STUDENT.getGrantedAuthorities())
										.build();
		
		
		UserDetails adminUser = User
										.builder()
										.username("admin")
										.password(passwordEncoder.encode("Jagadeesh"))
//										.roles(ADMIN.name())
										.authorities(ADMIN.getGrantedAuthorities())
										.build();
		
		UserDetails readUser = User
									.builder()
									.username("read")
									.password(passwordEncoder.encode("Jagadeesh"))
//									.roles(ADMINTRAINEE.name())
									.authorities(ADMINTRAINEE.getGrantedAuthorities())
									.build();
		
		return new InMemoryUserDetailsManager(jagadeeshUser, adminUser, readUser);
		
	}

}
