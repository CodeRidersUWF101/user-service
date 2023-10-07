package com.coderiders.userservice.models.db;

import jakarta.persistence.*;
import lombok.*;


import java.time.LocalDateTime;

@Entity
@Table(name = "userlibrary")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserLibrary {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_clerk_id")
    private String userClerkId;

    @Column(name = "book_id")
    private String bookId;

    @Column(nullable = false, updatable = false)
    private LocalDateTime addedDate;

    @Column(name = "reading_status")
    private String readingStatus;

    @Column(name = "last_page_read")
    private Integer lastPageRead;

    @Column(name = "last_updated")
    private LocalDateTime lastReadingUpdate;

}
