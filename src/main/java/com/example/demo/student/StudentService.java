package com.example.demo.student;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class StudentService {

    private final StudentRepository studentRepository;

    @Autowired
    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public List<Student> getStudents() {
		return studentRepository.findAll();
	}

    public void addNewStudent(Student student) {
        Optional<Student> studentOptional 
            = studentRepository.findStudentByEmail(student.getEmail());
        
        if (studentOptional.isPresent()) {
            throw new IllegalStateException("email taken");
        }

        studentRepository.save(student);
    }

    @Transactional
    public void updateStudent(Long studentId, String name, String email) {
        Student student = studentRepository
            .findById(studentId)
            .orElseThrow(
                () -> new IllegalStateException("no user " + studentId)
            );

        if (name != null && name.length() > 0) {
            student.setName(name);
        }

        if (email != null && email.length() > 0) {
            Optional<Student> studentOptional 
                = studentRepository.findStudentByEmail(email);

            if (studentOptional.isPresent()) {
                throw new IllegalStateException("email taken");
            }

            student.setEmail(email);
        }
    }
}
