package com.management.secretaria.services.impl;

import com.management.secretaria.model.StudentModel;
import com.management.secretaria.repository.StudentRepository;
import com.management.secretaria.services.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class StudentServiceImpl implements StudentService {

    @Autowired
    StudentRepository studentRepository;


    @Override
    public List<StudentModel> findAll() {
        return studentRepository.findAll();
    }

    @Override
    public Optional<StudentModel> findById(UUID studentId) {
        return studentRepository.findById(studentId);
    }

    @Override
    public void delete(StudentModel studentModel) {
        studentRepository.delete(studentModel);
    }

    @Override
    public StudentModel save(StudentModel studentModel) {
        return studentRepository.save(studentModel);
    }

    @Override
    public boolean existsByUserName(String userName) {
        return studentRepository.existsByUserName(userName);
    }

    @Override
    public boolean existsByEmail(String email) {
        return studentRepository.existsByEmail(email);
    }

    @Override
    public Page<StudentModel> findAll(Specification<StudentModel> spec, Pageable pageable) {
        return studentRepository.findAll(spec, pageable);
    }
}
