package com.example.schedule.config;

import org.springframework.context.annotation.Bean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.example.schedule.handler.CustomAuthenticationSuccessHandler;
import com.example.schedule.service.CustomUserDetailsService;

import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@EnableWebSecurity
@Configuration
public class SecurityConfig {

	@Lazy
	private final CustomUserDetailsService customUserDetailsService;

	private final CustomAuthenticationSuccessHandler customAuthenticationSuccessHandler;

    public SecurityConfig(
    		CustomUserDetailsService customUserDetailsService, 
    		CustomAuthenticationSuccessHandler customAuthenticationSuccessHandler
    		) {
        this.customUserDetailsService = customUserDetailsService;
        this.customAuthenticationSuccessHandler = customAuthenticationSuccessHandler;
    }
	
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
	    http
	        .csrf().disable()
	        .authorizeRequests()
	        	.antMatchers("/student.html").hasRole("STUDENT")
	        	.antMatchers("/studentlk.html").hasRole("STUDENT")
	        	.antMatchers("/teacher.html").hasRole("TEACHER")
	        	.antMatchers("/teacherlk.html").hasRole("TEACHER")
	        	.antMatchers("/admin.html").hasRole("ADMIN")
	        	.antMatchers("/adminlk.html").hasRole("ADMIN")
	            .anyRequest().authenticated() // Остальные запросы требуют аутентификации
	        .and()
	        .formLogin()
	        	.loginPage("/custom-login")
	        	.loginProcessingUrl("/login")
	        	.successHandler(customAuthenticationSuccessHandler)
	            .permitAll()
	        .and()
	        .logout()
	            .permitAll();
	    return http.build();
	}
	
	@Bean
    public AuthenticationManager authManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
