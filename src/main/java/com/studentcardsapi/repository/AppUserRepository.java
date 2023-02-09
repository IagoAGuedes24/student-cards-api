package com.studentcardsapi.repository;

import com.studentcardsapi.enums.AppUserRole;
import com.studentcardsapi.model.user.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Optional;

@Repository
public interface AppUserRepository<T extends AppUser> extends JpaRepository<T, Long> {

    Optional<T> findByUsername(String username);
    
    Optional<T> findByUsernameAndEnabled(String username, boolean enabled);

    Optional<T> findByUsernameConfirmationToken(String activationToken);

    Optional<T> findByCpf(String cpf);

    Optional<T> findById(Long id);

    Optional<T> findByIdAndEnabled(Long id, boolean enabled);

    Collection<T> findAllByUserRole(AppUserRole userRole);

}
