package com.example.studentplanner;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@EnableWebMvc
@SpringBootApplication
public class StudentPlannerApplication {
    public static void main(String[] args) {
        SpringApplication.run(StudentPlannerApplication.class, args);
    }

}

