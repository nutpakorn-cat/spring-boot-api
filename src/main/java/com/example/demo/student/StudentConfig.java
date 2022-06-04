package com.example.demo.student;

import java.time.LocalDate;
import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class StudentConfig {
    
    @Bean
    CommandLineRunner CommandLineRunner(
        StudentRepository repository
    ) {
        return args -> {
            repository.saveAll(
                List.of(
                    new Student(
                        1L,
                        "Test 555",
                        "abc@bcd",
                        LocalDate.of(2000, 1, 1)
                    )
                )
            );
        };
    }
}
