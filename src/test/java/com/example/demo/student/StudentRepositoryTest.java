package com.example.demo.student;

import java.time.LocalDate;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
public class StudentRepositoryTest {

    @Autowired
    private StudentRepository underTest;

    @Test
    void itShouldFindStudentByEmail() {
        Student student = new Student(
            1L,
            "abc",
            "abc@bcd",
            LocalDate.now()
        );
        underTest.save(student);
        
        Optional<Student> result = underTest.findStudentByEmail("abc@bcd");

        assertThat(result.isPresent()).isTrue();
        assertEquals(result.get(), student);
    }
}
