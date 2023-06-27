package com.management.secretaria.repository;

import com.management.secretaria.model.StudentModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.UUID;

public interface StudentRepository extends JpaRepository<StudentModel, UUID>, JpaSpecificationExecutor<StudentModel> {

    boolean existsByUserName(String userName);

    boolean existsByEmail(String email);


}
