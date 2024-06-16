package com.ankit.scheduler.repositories;

import com.ankit.scheduler.Entity.Goal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface GoalRepository extends JpaRepository<Goal, Long> {
    List<Goal> findAllByUserId(long id);

    Optional<Goal> findByIdAndUserId(long id, long userId);
}
