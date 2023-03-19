package com.studentcardsapi.repository;

import com.studentcardsapi.DTO.SubjectDTO;
import com.studentcardsapi.enums.Year;
import com.studentcardsapi.model.Subject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SubjectRepository extends JpaRepository<Subject,Long> {

    Optional<Subject> findByNameAndYear(String name, Year year);

    List<Subject> findAllByYear(Year year);
}
