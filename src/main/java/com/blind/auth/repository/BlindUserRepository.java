package com.blind.auth.repository;

import com.blind.auth.entity.BlindUser;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BlindUserRepository extends JpaRepository<BlindUser, Integer> {

    Optional<BlindUser> findByEmail(String email);

}
