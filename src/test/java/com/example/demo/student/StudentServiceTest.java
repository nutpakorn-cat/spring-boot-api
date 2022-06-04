package com.example.demo.student;

import static org.mockito.Mockito.verify;

import java.time.LocalDate;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.BDDMockito.*;

@ExtendWith(MockitoExtension.class)
public class StudentServiceTest {
    
    @Mock
    private StudentRepository studentRepository;

    private StudentService underTest;

    @BeforeEach
    void setup() {
        underTest = new StudentService(studentRepository);
    }

    @Test
    void getStudents() {
        underTest.getStudents();
        verify(studentRepository).findAll();
    }

    @Test
    void addNewStudentWithoutError() {
        Student student = getMockStudent();

        underTest.addNewStudent(student);

        ArgumentCaptor<Student> studentArgumentCaptor
            = ArgumentCaptor.forClass(Student.class);
        verify(studentRepository)
            .save(studentArgumentCaptor.capture());

        assertThat(studentArgumentCaptor.getValue()).isEqualTo(student);
    }

    @Test
    void addNewStudentWithError() {
        Student student = getMockStudent();

        given(studentRepository.findStudentByEmail(student.getEmail()))
            .willReturn(Optional.of(student));

        assertThatThrownBy(() -> underTest.addNewStudent(student))
            .isInstanceOf(IllegalStateException.class)
            .hasMessageContaining("email taken");
    }

    @Test
    void updateStudentWithNoUserError() {
        Long studentId = 1L;
        given(studentRepository.findById(studentId))
            .willReturn(Optional.empty());
        
        assertThatThrownBy(() -> underTest.updateStudent(studentId, null, null))
            .isInstanceOf(IllegalStateException.class);
    }

    @Test
    void updateOnlyStudentNameWithoutError() {
        Student student = getMockStudent();
        given(studentRepository.findById(student.getId()))
            .willReturn(Optional.of(student));
        
        underTest.updateStudent(student.getId(), "newName", null);

        assertThat(student.getName()).isEqualTo("newName");
        assertThat(student.getId()).isEqualTo(getMockStudent().getId());
        assertThat(student.getEmail()).isEqualTo(getMockStudent().getEmail());
    }

    @Test
    void updateOnlyStudentEmailWithoutError() {
        Student student = getMockStudent();
        given(studentRepository.findById(student.getId()))
            .willReturn(Optional.of(student));
        
        underTest.updateStudent(student.getId(), null, "test@test");

        assertThat(student.getName()).isEqualTo(getMockStudent().getName());
        assertThat(student.getId()).isEqualTo(getMockStudent().getId());
        assertThat(student.getEmail()).isEqualTo("test@test");
    }


    Student getMockStudent() {
        return new Student(
            1L,
            "abc",
            "abc@bcd",
            LocalDate.now()
        );
    }
}
