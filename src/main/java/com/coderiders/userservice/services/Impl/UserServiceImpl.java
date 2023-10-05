package com.coderiders.userservice.services.Impl;

import com.coderiders.userservice.models.db.User;
import com.coderiders.userservice.repositories.UserRepository;
import com.coderiders.userservice.services.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
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

    @Override
    public com.coderiders.commonutils.models.User addNewUser(com.coderiders.commonutils.models.User user) {

        User newUser = User.builder()
                .clerkId(user.getClerkId())
                .username(user.getUsername())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .signupDate(LocalDateTime.now())
                .build();

        User dbUser = userRepository.findByClerkId(user.getClerkId());

        if (dbUser == null) {
            User newUsr = userRepository.save(newUser);
            return userToOtherUserDTO(newUsr);
        }
        return user;
    }

    private com.coderiders.commonutils.models.User userToOtherUserDTO(User user) {
        com.coderiders.commonutils.models.User retUser = new com.coderiders.commonutils.models.User();

        retUser.setClerkId(user.getClerkId());
        retUser.setFirstName(user.getFirstName());
        retUser.setLastName(user.getLastName());
        retUser.setUsername(user.getUsername());
        return retUser;
    }
}
