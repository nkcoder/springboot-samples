package org.nkcoder.repository;

import java.time.LocalDateTime;
import java.util.List;
import org.nkcoder.entity.Student;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentRepository extends CrudRepository<Student, Long> {

  Student findStudentById(Long id);

  /**
   * equivalent to the following forms:
   * - findStudentByIdAndName()
   * - readByIdAndName()
   * - getByIdAndName()
   */
  Student findByIdAndName(Long id, String name);

  Student readByIdAndCreateAtBetween(Long id, LocalDateTime from, LocalDateTime to);

  /**
   * equivalent to:
   * - Iterable<Student> getByCreateAtAfter()
   * - Iterable<Student> getByCreateAtIsAfter()
   */
  List<Student> getByCreateAtAfter(LocalDateTime from);

  @Query(value = "SELECT s FROM Student s where s.name = :name")
  Student findByNameParam(@Param("name") String name);

  @Query(value = "SELECT s FROM Student s where s.name = ?1")
  Student findByName(String name);

  List<Student> findByName(String name, Pageable pageable);

}
