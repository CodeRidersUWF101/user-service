package com.coderiders.userservice.repositories;

import com.coderiders.userservice.models.db.UserRecommendation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRecommendationRepository extends JpaRepository<UserRecommendation, Long> {
}
