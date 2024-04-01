package com.CrudOperations.controller;


import com.CrudOperations.Entity.Student;
import com.CrudOperations.repository.StudentRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class StudentController {
    @Autowired
    private StudentRepo studentRepo;

    @PostMapping("/students")
    public ResponseEntity<Student> SaveStudent(@RequestBody Student student) {
        return new ResponseEntity<>(studentRepo.save(student), HttpStatus.CREATED);
    }

    @GetMapping("/GetAll")
    public ResponseEntity<List<Student>> getStudent() {
        return new ResponseEntity<>(studentRepo.findAll(), HttpStatus.OK);
    }

    @GetMapping("/getStudentById/{id}")
    public ResponseEntity<Student> getStudentById(@PathVariable Long id) {
        Optional<Student> student = studentRepo.findById(id);
        if (student.isPresent()) {
            return new ResponseEntity<>(student.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/updateStudentById/{id}")
    public ResponseEntity<Student> updateStudentById(@PathVariable Long id, @RequestBody Student student) {
        Optional<Student> studentIsThereOrNot = studentRepo.findById(id);
        if (studentIsThereOrNot.isPresent()) {
            Student existingStudent = studentIsThereOrNot.get();
            existingStudent.setStudentEmail(student.getStudentEmail());
            existingStudent.setStudentName(student.getStudentName());
            existingStudent.setStudentAddress(student.getStudentAddress());
            Student updatedStudent = studentRepo.save(existingStudent);
            return new ResponseEntity<>(updatedStudent, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
