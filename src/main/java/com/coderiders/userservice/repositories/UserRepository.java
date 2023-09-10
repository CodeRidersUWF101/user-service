package com.coderiders.userservice.repositories;

import com.coderiders.userservice.models.db.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    List<User> findByFirstName(String firstName);
    User findByClerkId(String clerkId);
}
