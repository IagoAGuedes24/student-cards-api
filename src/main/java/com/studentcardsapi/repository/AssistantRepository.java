package com.studentcardsapi.repository;

import com.studentcardsapi.model.user.Assistant;
import org.springframework.stereotype.Repository;

@Repository
public interface AssistantRepository extends AppUserRepository<Assistant> {
}
