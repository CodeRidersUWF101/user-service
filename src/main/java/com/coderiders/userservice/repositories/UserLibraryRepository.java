package com.coderiders.userservice.repositories;

import com.coderiders.userservice.models.db.UserLibrary;
import org.springframework.data.jpa.repository.JpaRepository;


import java.util.List;

public interface UserLibraryRepository extends JpaRepository<UserLibrary, Long> {

    List<UserLibrary> findAllByUserClerkId(String clerkId);

}
