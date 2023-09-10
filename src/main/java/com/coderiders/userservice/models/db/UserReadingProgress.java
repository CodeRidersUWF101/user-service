package com.coderiders.userservice.models.db;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "userreadingprogress")
public class UserReadingProgress {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "progress_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_clerk_id", referencedColumnName = "clerk_id")
    @JsonBackReference
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "book_id")
    private Book book;

    @Column(name = "last_page_read", nullable = false)
    private int lastPageRead = 0;

    @Column(nullable = false, updatable = false)
    private LocalDateTime lastUpdated = LocalDateTime.now();
}
