package com.coderiders.userservice.models.db;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "userbooks")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
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
    private LocalDateTime addedDate;

    @Column(name = "reading_status")
    private String readingStatus;

}
