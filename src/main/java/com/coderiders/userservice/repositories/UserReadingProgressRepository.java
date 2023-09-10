package com.coderiders.userservice.repositories;

import com.coderiders.userservice.models.db.UserReadingProgress;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserReadingProgressRepository extends JpaRepository<UserReadingProgress, Long> {
}
