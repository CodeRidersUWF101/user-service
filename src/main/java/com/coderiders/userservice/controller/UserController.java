package com.coderiders.userservice.controller;

import com.coderiders.commonutils.models.UserLibraryWithBookDetails;
import com.coderiders.commonutils.models.UtilsUser;
import com.coderiders.commonutils.models.googleBooks.SaveBookRequest;
import com.coderiders.commonutils.models.requests.GetFriendsBooks;
import com.coderiders.commonutils.models.requests.UpdateFriendRequest;
import com.coderiders.commonutils.models.requests.UpdateProgress;
import com.coderiders.commonutils.models.requests.AddFriend;
import com.coderiders.userservice.models.db.User;
import com.coderiders.userservice.services.UserLibraryService;
import com.coderiders.userservice.services.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.hibernate.exception.ConstraintViolationException;

import java.util.List;

@Slf4j
@RefreshScope
@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final UserLibraryService userLibraryService;

    @GetMapping("/")
    public String myRoute() {  return "Successful RecommendationController"; }

    @GetMapping("/{clerkId}")
    public ResponseEntity<User> myRoute(@PathVariable String clerkId) {
        log.info("getUserByClerkId for: " + clerkId);
        return new ResponseEntity<>(userService.getUserByClerkId(clerkId), HttpStatus.OK);
    }

    @GetMapping("/library")
    public List<UserLibraryWithBookDetails> getBooks(@RequestParam("clerk_id") String clerkId) {
        log.info("/users/library GET ENDPOINT HIT: " + clerkId);
        return userLibraryService.getUserLibraryForClerkId(clerkId);
    }

    @PostMapping("/library")
    public String saveBooks(@RequestBody SaveBookRequest payload) {
        log.info("/users/library POST ENDPOINT HIT: " + payload.getUser().getClerkId());
        return userLibraryService.saveBookCustom(payload);
    }

    @PatchMapping("/library")
    public UpdateProgress updateReadingProgress(@RequestBody UpdateProgress updateProgress) {
        log.info("/users/signup PATCH ENDPOINT HIT: " + updateProgress.getClerkId());
        return userService.updateReadingProgress(updateProgress);
    }

    @DeleteMapping("/library")
    public String deleteUserBook(@RequestParam("book_id") String bookId, @RequestParam("clerk_id") String clerkId) {
        log.info("/users/signup DELETE ENDPOINT HIT: {} for book: {}", clerkId, bookId);
        return userLibraryService.removeBook(bookId, clerkId);
    }

    @PostMapping("/signup")
    public UtilsUser addNewUser(@RequestBody UtilsUser user) {
        log.info("/users/signup POST ENDPOINT HIT: " + user.getClerkId());
        return userService.addNewUser(user);
    }

    @PostMapping("/getByClerkIds")
    public List<UtilsUser> getUsersByClerkId(@RequestBody List<String> clerkIds) {
        log.info("/users/getByClerkId GET ENDPOINT HIT: " + clerkIds);
        return userService.getAllUsersByClerkId(clerkIds);
    }

    @PostMapping("/addFriends")
    public AddFriend addFriend(@RequestBody AddFriend friendRequest) {
        log.info("/users/signup POST ENDPOINT HIT: " + friendRequest.getRequestingClerkId() + "   " + friendRequest.getFriendToAddClerkId());
        try {
            return userService.addFriend(friendRequest);
        } catch (ConstraintViolationException e) {
            return AddFriend
                    .builder()
                    .successString("DUPLICATE_RECORD")
                    .build();
        }
    }

    @GetMapping("/friends/pending")
    public List<SmallUser> getPendingFriends(@RequestParam("clerk_id") String clerkId) {
        log.info("/users/friends GET ENDPOINT HIT: " + clerkId);
        return userService.getPendingFriends(clerkId);
    }

    @GetMapping("/retrieveFriends")
    public List<GetFriendsBooks> getFriendsBooks(@RequestParam("clerkId") String clerkId) {
        log.info("/users/retrieveFriends GET ENDPOINT HIT: " + clerkId);
        return userService.getFriendsBooks(clerkId);
    }

    @GetMapping("/getUsers/")
    public List<UtilsUser> getUsersForAddFriendsPage(@RequestParam("clerk_id") String clerkId) {
        log.info("/users/getUsers GET ENDPOINT HIT with clerkId: " + clerkId);

        return userService.getAllUsersNotBlocked(clerkId);
    }

    @PutMapping("/updateFriends")
    public UpdateFriendRequest updateFriendRequest(@RequestBody UpdateFriendRequest updateRequest) {
        log.info("/users/updateFriends PUT ENDPOINT HIT: " + updateRequest.getClerkId());
        return userService.updateFriendRequest(updateRequest);
    }

}
