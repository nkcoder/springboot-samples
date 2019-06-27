package org.nkcoder;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.nkcoder.jpa.entity.Student;
import org.nkcoder.jpa.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.PageRequest;

@DataJpaTest
public class StudentRepositoryTest {

  @Autowired
  private StudentRepository studentRepository;

  private Long id;
  private String name;
  private DateTimeFormatter formatter;

  @BeforeEach
  public void setup() {
    id = 1L;
    name = "Kobe";
    formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
  }


  @Test
  public void shouldReturnStudent_when_findById() {
    Optional<Student> studentOpt = studentRepository.findById(id);

    assertThat(studentOpt).isNotNull();
    assertThat(studentOpt.isPresent()).isTrue();
    assertThat(studentOpt.get().getId()).isEqualTo(id);
  }

  @Test
  public void shouldReturnStudent_when_findStudentById() {
    Student student = studentRepository.findStudentById(id);

    assertThat(student).isNotNull();
    assertThat(student.getId()).isEqualTo(id);
  }

  @Test
  public void shouldReturnStudent_when_findByIdAndName() {
    Student student = studentRepository.findByIdAndName(id, name);

    assertThat(student).isNotNull();
    assertThat(student.getId()).isEqualTo(id);
    assertThat(student.getName()).isEqualTo(name);
  }

  @Test
  public void shouldReturnStudent_when_readByIdAndCreateAtBetween() {
    Student student = studentRepository.readByIdAndCreateAtBetween(
        id,
        LocalDateTime.parse("2019-06-24 10:01:01", formatter),
        LocalDateTime.parse("2019-06-25 10:01:01", formatter)
    );

    assertThat(student).isNotNull();
    assertThat(student.getId()).isEqualTo(id);
    assertThat(student.getName()).isEqualTo(name);
  }

  @Test
  public void shouldReturnStudent_when_getByCreateAtAfter() {
    List<Student> students = studentRepository.getByCreateAtAfter(
        LocalDateTime.parse("2019-06-24 10:01:01", formatter)
    );

    assertThat(students).isNotEmpty();
    assertThat(students.size()).isEqualTo(3);
  }

  @Test
  public void shouldReturnStudent_when_findByNameParam() {
    Student student = studentRepository.findByNameParam("Durant");

    assertThat(student).isNotNull();
    assertThat(student.getName()).isEqualTo("Durant");
  }

  @Test
  public void shouldReturnStudent_when_findByName() {
    Student student = studentRepository.findByName("Durant");

    assertThat(student).isNotNull();
    assertThat(student.getName()).isEqualTo("Durant");
  }

  @Test
  public void shouldReturnStudent_when_findByNameWithPage() {
    List<Student> studentPage = studentRepository.findByName("Durant", PageRequest.of(0, 5));

    assertThat(studentPage).isNotEmpty();
    assertThat(studentPage.size()).isEqualTo(2);
  }
}

