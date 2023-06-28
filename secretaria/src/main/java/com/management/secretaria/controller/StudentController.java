package com.management.secretaria.controller;

import com.management.secretaria.dto.StudentDto;
import com.management.secretaria.model.StudentModel;
import com.management.secretaria.services.StudentService;
import com.management.secretaria.specifications.SpecificationTemplate;
import jakarta.validation.Valid;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.UUID;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/students")
@Log4j2
public class StudentController {

    @Autowired
    StudentService studentService;

    @PostMapping
    public ResponseEntity<Object> saveStudent(@RequestBody @Valid StudentDto studentDto) {

        if (studentService.existsByUserName(studentDto.getUserName())) {
            log.warn("Username {} is Already Taken", studentDto.getUserName());
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Error: Username is Already Taken");
        }
        if (studentService.existsByEmail(studentDto.getEmail())) {
            log.warn("Email {} is Already Taken", studentDto.getEmail());
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Error: Email is Already Taken");
        }

        var studentModel = new StudentModel();
        BeanUtils.copyProperties(studentDto, studentModel);
        studentModel.setCreationDate(LocalDateTime.now(ZoneId.of("UTC")));
        studentModel.setLastUpdateDate(LocalDateTime.now(ZoneId.of("UTC")));
        studentService.save(studentModel);
        return ResponseEntity.status(HttpStatus.CREATED).body(studentModel);
    }

    @GetMapping
    public ResponseEntity<Page<StudentModel>> getAllStudents(SpecificationTemplate.StudentSpec spec,
                                                             @PageableDefault(page = 0, size = 10, sort = "studentId", direction = Sort.Direction.ASC) Pageable pageable) {
        Page<StudentModel> studentModelPage = studentService.findAll(spec, pageable);
        if (!studentModelPage.isEmpty()) {
            for (StudentModel student : studentModelPage.toList()) {
                var id = student.getStudentId();
                student.add(linkTo(methodOn(StudentController.class).getOneStudent(id)).withSelfRel());
            }
        }
        return ResponseEntity.status(HttpStatus.OK).body(studentModelPage);
    }

    @GetMapping("/{studentId}")
    public ResponseEntity<Object> getOneStudent(@PathVariable(value = "studentId") UUID studentId) {
        var optionalStudent = studentService.findById(studentId);
        if (optionalStudent.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Student not found");
        } else {
            optionalStudent.get().add(linkTo(methodOn(StudentController.class).getAllStudents(null, null)).withRel("Lista de Estudantes"));
            return ResponseEntity.status(HttpStatus.OK).body(optionalStudent.get());
        }
    }

    @DeleteMapping("/{studentId}")
    public ResponseEntity<Object> deleteStudent(@PathVariable(value = "studentId") UUID studentId) {
        var optionalStudent = studentService.findById(studentId);
        if (optionalStudent.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Student not found");
        } else {
            studentService.delete(optionalStudent.get());
            return ResponseEntity.status(HttpStatus.OK).body("Student deleted success");
        }
    }

    @PutMapping("/{studentId}")
    public ResponseEntity<Object> updateStudent(@PathVariable(value = "studentId") UUID studentId,
                                                @RequestBody @Valid StudentDto studentDto) {
        var optionalStudent = studentService.findById(studentId);
        if (optionalStudent.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Student not found");
        } else {
            var studentModel = optionalStudent.get();
            BeanUtils.copyProperties(studentDto, studentModel);
            studentModel.setLastUpdateDate(LocalDateTime.now(ZoneId.of("UTC")));
            studentService.save(studentModel);
            return ResponseEntity.status(HttpStatus.OK).body(studentModel);
        }
    }

}
