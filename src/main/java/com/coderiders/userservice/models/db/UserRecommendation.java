package com.coderiders.userservice.models.db;

import jakarta.persistence.*;

import java.time.LocalDateTime;
@Entity
@Table(name = "userrecommendations")
public class UserRecommendation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "recommendation_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_clerk_id", referencedColumnName = "clerk_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "book_id", referencedColumnName = "book_id")
    private Book book;

    @Column(nullable = false, updatable = false)
    private LocalDateTime recommendationDate = LocalDateTime.now();

    @Column
    private String reason;

    @Column
    private Integer rating;

    // getters, setters, and other methods
}
