package com.example.schedule.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
	    http
	        .csrf().disable()
	        .authorizeRequests()
	            .antMatchers("/api/grades/**").permitAll() // Разрешаем доступ ко всем запросам на /api/grades
	            .anyRequest().authenticated() // Остальные запросы требуют аутентификации
	        .and()
	        .formLogin()
	            .permitAll()
	        .and()
	        .logout()
	            .permitAll();
	    return http.build();
	}

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
