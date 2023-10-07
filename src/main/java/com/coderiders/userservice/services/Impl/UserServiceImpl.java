package com.coderiders.userservice.services.Impl;

import com.coderiders.commonutils.models.requests.UpdateProgress;
import com.coderiders.userservice.exceptions.UserServiceException;
import com.coderiders.userservice.models.db.User;
import com.coderiders.userservice.repositories.UserRepository;
import com.coderiders.userservice.services.UserService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
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
    private final EntityManager entityManager;

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

    @Override
    public UpdateProgress updateReadingProgress(UpdateProgress updateProgress) {
        String queryStr = "SELECT update_reading_progress(:clerkId, :bookId , :currentPage)";

        Query query = entityManager.createNativeQuery(queryStr)
                .setParameter("clerkId", updateProgress.getClerkId())
                .setParameter("bookId", updateProgress.getBookId())
                .setParameter("currentPage", updateProgress.getCurrentPage());


        String compositeResult = query.getSingleResult().toString();

        if (compositeResult == null || compositeResult.equalsIgnoreCase("null")) {
            throw new UserServiceException("Update User Reading Progress Failed.");
        }

        String cleanedResult = compositeResult.substring(1, compositeResult.length() - 1);
        String[] parts = cleanedResult.split(",");

        return UpdateProgress
                .builder()
                .clerkId(parts[0])
                .bookId(parts[1])
                .currentPage(Integer.parseInt(parts[2]))
                .build();
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
