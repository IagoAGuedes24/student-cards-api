package com.studentcardsapi.repository;

import com.studentcardsapi.model.user.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.Optional;

@NoRepositoryBean
public interface AppUserRepository<T extends AppUser> extends JpaRepository<T, Long> {

    Optional<AppUser> findByUsername(String username);

}
