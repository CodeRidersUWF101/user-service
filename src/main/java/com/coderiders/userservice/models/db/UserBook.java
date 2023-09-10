package com.coderiders.userservice.models.db;

import com.coderiders.userservice.models.ReadingStatus;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "userbooks")
public class UserBook {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_book_id")
    private Long id;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_clerk_id", referencedColumnName = "clerk_id")
    @JsonBackReference
    private User user;

    @ManyToOne
    @JoinColumn(name = "book_id")
    private Book book;

    @Column(nullable = false, updatable = false)
    private LocalDateTime addedDate = LocalDateTime.now();

    @Enumerated(EnumType.STRING)
    @Column(name = "reading_status", nullable = false)
    private ReadingStatus readingStatus = ReadingStatus.NOT_STARTED;
}
