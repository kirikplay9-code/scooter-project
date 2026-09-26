package com.scooter.scooterrental.user.enums.repository;

import com.scooter.scooterrental.user.enums.entity.Role;
import com.scooter.scooterrental.user.enums.UserRole;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {
    Optional<Role> findByName(UserRole name); //optional
}
@RequiredArgsConstructor
class A{

}