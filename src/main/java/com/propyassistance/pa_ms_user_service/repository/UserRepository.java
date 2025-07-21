package com.propyassistance.pa_ms_user_service.repository;


import com.propyassistance.pa_ms_user_service.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {
    Optional<User> findByOktaId(String oktaId);
    Optional<User> findByEmail(String email);
}
