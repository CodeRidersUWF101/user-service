package com.coderiders.userservice.services;

import com.coderiders.commonutils.models.requests.UpdateProgress;
import com.coderiders.userservice.models.db.User;

import java.util.List;

public interface UserService {
    List<User> getAllUsersWithFirstName(String firstName);
    User getUserByClerkId(String clerkId);
    com.coderiders.commonutils.models.User addNewUser(com.coderiders.commonutils.models.User user);
    UpdateProgress updateReadingProgress(UpdateProgress updateProgress);
}
