package com.studentcardsapi.repository;

import com.studentcardsapi.enums.AppUserRole;
import com.studentcardsapi.model.user.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Optional;

@Repository
public interface AppUserRepository<T extends AppUser> extends JpaRepository<T, Long> {

    Optional<T> findByUsername(String username);

    Collection<AppUser> findAllByUserRole(AppUserRole userRole);

}
