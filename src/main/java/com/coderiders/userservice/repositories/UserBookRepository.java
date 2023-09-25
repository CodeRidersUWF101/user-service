package com.coderiders.userservice.repositories;

import com.coderiders.userservice.models.db.UserBook;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UserBookRepository extends JpaRepository<UserBook, Long> {
    @Query("SELECT ub FROM UserBook ub WHERE ub.user.clerkId = :clerkId")
    List<UserBook> findByUserClerkId(@Param("clerkId") String clerkId);
}
