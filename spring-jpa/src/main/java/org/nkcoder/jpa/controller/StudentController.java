package org.nkcoder.jpa.controller;

import lombok.extern.slf4j.Slf4j;
import org.nkcoder.jpa.entity.Student;
import org.nkcoder.jpa.repository.StudentRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/student")
@Slf4j
public class StudentController {

  private StudentRepository studentRepository;

  public StudentController(StudentRepository studentRepository) {
    this.studentRepository = studentRepository;
  }

  @GetMapping("/{id}")
  public Student getStudent(@PathVariable("id") Long id) {
    return studentRepository.findById(id)
        .orElseThrow(() -> new StudentNotFoundException("student not found, id: " + id));
  }


  static class StudentNotFoundException extends RuntimeException {

    private static final long serialVersionUID = 3029466508131425212L;

    public StudentNotFoundException(String message) {
      super(message);
    }
  }
}
