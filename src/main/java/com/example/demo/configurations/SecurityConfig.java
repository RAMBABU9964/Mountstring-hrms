package com.example.demo.configurations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.session.RedisSessionProperties.ConfigureAction;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.example.demo.service.CustomSuccessHandler;
import com.example.demo.service.CustomUserDetailService;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
	
	@Autowired
	CustomUserDetailService customUserDetailService;
	
	@Autowired
	CustomSuccessHandler customSuccessHandler;

	@Bean
	public static PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
		
	}
	
	
	
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
		
		http.csrf(c -> c.disable())
		.authorizeHttpRequests(request -> request.requestMatchers("/admin-page")
				.hasAuthority("ADMIN").requestMatchers("/employee-page").hasAuthority("EMPLOYEE")
				.requestMatchers("/pro-admin-page").hasAuthority("PRO-ADMIN")
				.requestMatchers( "/css/**").permitAll()
				.requestMatchers( "/").permitAll()
				.requestMatchers("/forgot-password").permitAll()
				.requestMatchers("/password-request").permitAll()
				.requestMatchers("/password").permitAll()
				.requestMatchers("HomePage").permitAll()
				.requestMatchers("/reset-password").permitAll().anyRequest()
				.authenticated())
		
		.formLogin(form -> form.loginPage("/login").loginProcessingUrl("/login")
				.successHandler(customSuccessHandler)
				.permitAll())
		
		.logout(form -> form.invalidateHttpSession(true).clearAuthentication(true)
				.logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
				.logoutSuccessUrl("/?logout").permitAll());
		
		
		return http.build();
		
	}
	
	
	
	@Autowired
	public void ConfigureAction(AuthenticationManagerBuilder auth)throws Exception{
		auth.userDetailsService(customUserDetailService).passwordEncoder(passwordEncoder());
	}
}
