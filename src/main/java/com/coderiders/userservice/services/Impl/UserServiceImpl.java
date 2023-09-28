package com.coderiders.userservice.services.Impl;

import com.coderiders.userservice.models.db.User;
import com.coderiders.userservice.repositories.UserRepository;
import com.coderiders.userservice.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    public List<User> getAllUsersWithFirstName(String firstName) {
        return userRepository.findByFirstName(firstName);
    }

    public User getUserByClerkId(String clerkId) {
        return userRepository.findByClerkId(clerkId);
    }
}
