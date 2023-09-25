package com.coderiders.userservice.controller;

import com.coderiders.userservice.exceptions.UserServiceException;
import com.coderiders.userservice.models.db.Book;
import com.coderiders.userservice.models.db.User;
import com.coderiders.userservice.models.request.SaveBookRequest;
import com.coderiders.userservice.services.UserBooksService;
import com.coderiders.userservice.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RefreshScope
@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final UserBooksService userBooksService;

    @GetMapping("/")
    public String myRoute() {
        return "Successful RecommendationController";
    }

    @GetMapping("/{clerkId}")
    public ResponseEntity<User> myRoute(@PathVariable String clerkId) {
        User myUser = userService.getUserByClerkId(clerkId);
        return new ResponseEntity<>(myUser, HttpStatus.OK);
    }

    @GetMapping("/exceptionTest")
    public String myRouteException() {
        throw new UserServiceException("My Exception Test");
//        return "Successful RecommendationController";
    }

    @PostMapping("/users/library")
    public Long saveBooks(@RequestBody SaveBookRequest payload) {
        return userBooksService.saveBook(payload);


//        System.out.println(payload);

    }
}
