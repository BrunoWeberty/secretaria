package com.management.secretaria.services;

import com.management.secretaria.model.StudentModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface StudentService {

    List<StudentModel> findAll();

    Optional<StudentModel> findById(UUID studentId);

    void delete(StudentModel studentModel);

    StudentModel save(StudentModel studentModel);

    boolean existsByUserName(String userName);

    boolean existsByEmail(String email);

    Page<StudentModel> findAll(Specification<StudentModel> spec, Pageable pageable);


}
