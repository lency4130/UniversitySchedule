package com.example.schedule;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.ApplicationContext;

// import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
// import org.springframework.security.crypto.password.PasswordEncoder;
import com.example.schedule.model.Subject;
@ComponentScan(basePackages = "com.example.schedule")
@SpringBootApplication
public class ScheduleApplication {
    public static void main(String[] args) {
    	ApplicationContext context = SpringApplication.run(ScheduleApplication.class, args);
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String rawPassword = "12345"; // замените на ваш пароль
        String encodedPassword = encoder.encode(rawPassword);
        System.out.println(encodedPassword);
        String[] beanNames = context.getBeanDefinitionNames();
        for (String beanName : beanNames) {
            System.out.println(beanName);
        }
    }

}

