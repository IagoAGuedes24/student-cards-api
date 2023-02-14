package com.studentcardsapi.repository;

import com.studentcardsapi.model.user.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface AppUserRepository<T extends AppUser> extends JpaRepository<T, Long> {

    Optional<T> findByUsername(String username);

    @Query(
            value = "SELECT * FROM app_user WHERE enabled = true AND username = ?1",
            nativeQuery = true
    )
    Optional<T> findEnabledByUsername(String username);

    Optional<T> findByCpf(String cpf);

    Optional<T> findById(Long id);

    Optional<T> findByUsernameConfirmationToken(String usernameConfirmationToken);

}
