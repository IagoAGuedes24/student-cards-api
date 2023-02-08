package com.studentcardsapi.repository;

import com.studentcardsapi.model.user.Coordinator;
import org.springframework.stereotype.Repository;

@Repository
public interface CoordinatorRepository extends AppUserRepository<Coordinator> {
}
