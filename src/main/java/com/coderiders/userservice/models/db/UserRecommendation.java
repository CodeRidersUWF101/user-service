package com.coderiders.userservice.models.db;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
@Entity
@Table(name = "userrecommendations")
@AllArgsConstructor
@NoArgsConstructor
public class UserRecommendation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "recommendation_id")
    private Long id;

    @Column(name = "user_clerk_id")
    private String userClerkId;

    @Column(name = "book_id")
    private String bookId;

    @Column(nullable = false, updatable = false)
    private LocalDateTime recommendationDate = LocalDateTime.now();

    @Column
    private String reason;

    @Column
    private Integer rating;

}
