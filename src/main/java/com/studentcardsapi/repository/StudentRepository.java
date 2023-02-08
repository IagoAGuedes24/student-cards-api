package com.studentcardsapi.repository;

import com.studentcardsapi.model.user.Student;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentRepository extends AppUserRepository<Student> {
}
