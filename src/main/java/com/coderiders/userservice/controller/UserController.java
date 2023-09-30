package com.coderiders.userservice.controller;

import com.coderiders.commonutils.models.UserLibrary;
import com.coderiders.userservice.models.db.User;
import com.coderiders.userservice.models.request.SaveBookRequest;
import com.coderiders.userservice.services.UserBooksService;
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
    private final UserBooksService userBooksService;

    @GetMapping("/")
    public String myRoute() {  return "Successful RecommendationController"; }

    @GetMapping("/{clerkId}")
    public ResponseEntity<User> myRoute(@PathVariable String clerkId) {
        log.info("getUserByClerkId for: " + clerkId);
        return new ResponseEntity<>(userService.getUserByClerkId(clerkId), HttpStatus.OK);
    }

    @PostMapping("/users/library")
    public Long saveBooks(@RequestBody SaveBookRequest payload) {
        log.info("saveBooks for: " + payload.getClerkId());
        return userBooksService.saveBook(payload);
    }

    @GetMapping("/users/library")
    public List<UserLibrary> getBooks(@RequestParam("clerk_id") String clerkId) {
        log.info("Get Library for: " + clerkId);
        return userBooksService.getUserLibrary(clerkId);
    }
}
