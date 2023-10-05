package com.coderiders.userservice.controller;

import com.coderiders.commonutils.models.UserLibraryWithBookDetails;
import com.coderiders.commonutils.models.googleBooks.GoogleBook;
import com.coderiders.commonutils.models.googleBooks.SaveBookRequest;
import com.coderiders.userservice.models.db.Book;
import com.coderiders.userservice.models.db.User;
import com.coderiders.userservice.services.BooksService;
import com.coderiders.userservice.services.UserLibraryService;
import com.coderiders.userservice.services.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RefreshScope
@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final UserLibraryService userLibraryService;
    private final BooksService booksService;

    @GetMapping("/")
    public String myRoute() {  return "Successful RecommendationController"; }

    @GetMapping("/{clerkId}")
    public ResponseEntity<User> myRoute(@PathVariable String clerkId) {
        log.info("getUserByClerkId for: " + clerkId);
        return new ResponseEntity<>(userService.getUserByClerkId(clerkId), HttpStatus.OK);
    }

    @PostMapping("/users/library")
    public Long saveBooks(@RequestBody SaveBookRequest payload) {
        log.info("saveBooks for: " + payload.toString());
        return userLibraryService.saveBook(payload);
    }

    @GetMapping("/users/library")
    public List<UserLibraryWithBookDetails> getBooks(@RequestParam("clerk_id") String clerkId) {
        log.info("Get Library for: " + clerkId);
        return userLibraryService.getUserLibraryForClerkId(clerkId);
    }

    @PostMapping("/users/books")
    public String saveBooks(@RequestBody List<GoogleBook> books) {
        log.info("Post Books");
        List<Book> booksToReturn = booksService.saveBooks(books);
        return "SAVED";
    }

    @PostMapping("/users/signup")
    public com.coderiders.commonutils.models.User addNewUser(@RequestBody com.coderiders.commonutils.models.User user) {
        log.info("/users/signup POST ENDPOINT HIT: " + user.getClerkId());
        return userService.addNewUser(user);
    }
}
