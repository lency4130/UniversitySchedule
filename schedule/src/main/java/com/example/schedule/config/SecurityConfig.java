package com.example.schedule.config;

import org.springframework.context.annotation.Bean;

import java.util.List;

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
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
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
	    	.cors().and()
	        .csrf().disable()
	        .authorizeRequests()
	        	.antMatchers("/api/lessons/my-group").hasRole("STUDENT")
	        	.antMatchers("/api/files/upload").hasRole("TEACHER")
	        	.antMatchers("/api/grades/set").hasRole("TEACHER")
	        	.antMatchers("/api/student-group/create").hasRole("ADMIN")
	        	.antMatchers("/api/students/create").hasRole("ADMIN")
	        	.antMatchers("/api/teachers/create").hasRole("ADMIN")
	        	.antMatchers("/api/lessons/add-lesson").hasRole("ADMIN")
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
    
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(List.of("http://localhost:3000")); // Укажите порт вашего фронтенда
        configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE"));
        configuration.setAllowedHeaders(List.of("*"));
        configuration.setAllowCredentials(true);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
    
}
