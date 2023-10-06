package com.coderiders.userservice.services;

import com.coderiders.userservice.models.db.User;
import com.coderiders.userservice.repositories.UserRepository;
import com.coderiders.userservice.services.Impl.UserServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;
import static java.util.Collections.singletonList;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTests {

    @Mock
    private UserRepository userRepository;
    @InjectMocks
    private UserServiceImpl userService;

    private static User getUser() {
        return User.builder()
                .userId(1L)
                .firstName("John")
                .lastName("Doe")
                .username("john_doe")
                .clerkId("JD001")
                .signupDate(LocalDateTime.now())
                .build();
    }

    @Test
    void shouldReturnUserWhenClerkIdIsProvided() {
        when(userRepository.findByClerkId(anyString())).thenReturn(getUser());

        User user = userService.getUserByClerkId("JD001");
        assertNotNull(user);
        assertTrue("JD001".equalsIgnoreCase(user.getClerkId()));
    }

    @Test
    void shouldReturnUsersWithMatchingFirstName() {
        String firstName = "John";
        when(userRepository.findByFirstName(eq(firstName))).thenReturn(singletonList(getUser()));

        List<User> users = userService.getAllUsersWithFirstName(firstName);

        assertNotNull(users);
        assertFalse(users.isEmpty(), "The list of users should not be empty");
        assertEquals(1, users.size(), "The list of users should have one user");
        User user = users.get(0);
        assertEquals(firstName, user.getFirstName(), "The first name should be John");
    }

    @Test
    void shouldAddNewUserWhenUserDoesNotExist() {
        com.coderiders.commonutils.models.User commonUser = new com.coderiders.commonutils.models.User();
        commonUser.setClerkId("JD001");
        commonUser.setUsername("john_doe");
        commonUser.setFirstName("John");
        commonUser.setLastName("Doe");

        when(userRepository.findByClerkId(anyString())).thenReturn(null);
        when(userRepository.save(any())).thenAnswer(invocation -> invocation.getArgument(0));

        com.coderiders.commonutils.models.User returnedUser = userService.addNewUser(commonUser);

        assertNotNull(returnedUser);
        assertEquals(commonUser.getClerkId(), returnedUser.getClerkId(), "ClerkId mismatch");
        assertEquals(commonUser.getUsername(), returnedUser.getUsername(), "Username mismatch");
        assertEquals(commonUser.getFirstName(), returnedUser.getFirstName(), "First name mismatch");
        assertEquals(commonUser.getLastName(), returnedUser.getLastName(), "Last name mismatch");

        verify(userRepository).findByClerkId(anyString());
        verify(userRepository).save(any());
        verifyNoMoreInteractions(userRepository);
    }

    @Test
    void shouldReturnExistingUserWhenUserAlreadyExists() {
        User dbUser = getUser();
        com.coderiders.commonutils.models.User commonUser = new com.coderiders.commonutils.models.User();
        commonUser.setClerkId("JD001");
        commonUser.setUsername("john_doe");
        commonUser.setFirstName("John");
        commonUser.setLastName("Doe");

        when(userRepository.findByClerkId(anyString())).thenReturn(dbUser);

        com.coderiders.commonutils.models.User returnedUser = userService.addNewUser(commonUser);

        assertNotNull(returnedUser);
        assertEquals(commonUser.getClerkId(), returnedUser.getClerkId(), "ClerkId mismatch");
        assertEquals(commonUser.getUsername(), returnedUser.getUsername(), "Username mismatch");
        assertEquals(commonUser.getFirstName(), returnedUser.getFirstName(), "First name mismatch");
        assertEquals(commonUser.getLastName(), returnedUser.getLastName(), "Last name mismatch");

        verify(userRepository).findByClerkId(anyString());
        verifyNoMoreInteractions(userRepository);
    }


}
