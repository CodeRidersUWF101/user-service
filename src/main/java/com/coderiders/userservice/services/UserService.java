package com.coderiders.userservice.services;

import com.coderiders.userservice.models.db.User;

import java.util.List;

public interface UserService {
    List<User> getAllUsersWithFirstName(String firstName);
    User getUserByClerkId(String clerkId);
}
