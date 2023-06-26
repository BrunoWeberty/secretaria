package com.management.secretaria.services;

import com.management.secretaria.model.StudentModel;

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


}
