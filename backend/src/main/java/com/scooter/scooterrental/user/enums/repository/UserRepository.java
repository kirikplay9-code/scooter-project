package com.scooter.scooterrental.user.enums.repository;

import com.scooter.scooterrental.user.enums.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {
    Optional<User> findByLogin(String login);
    boolean existsByLogin(String login);
    boolean existsByEmail(String email);
    Optional<User> findByLoginAndActiveTrue(String login);
    Page<User> findAllByActiveTrue(Pageable pageable);
}