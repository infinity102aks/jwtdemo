package com.ankit.scheduler.repositories;

import com.ankit.scheduler.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    @Query("SELECT u FROM User u WHERE u.name LIKE %:name%")
    List<User> findAllByNameContaining(@Param("name") String name);

    Optional<User> findByEmail(String email);
}
