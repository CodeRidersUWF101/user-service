package com.coderiders.userservice.models.db;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Data
@Entity
@Builder
@Table(name = "users")
@AllArgsConstructor
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    @Column(name = "username")
    private String username;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(nullable = false, updatable = false)
    private LocalDateTime signupDate = LocalDateTime.now();

    @Column(name = "clerk_id", unique = true, nullable = false)
    private String clerkId;

    @Column(name = "image_url")
    private String imageUrl;
}
