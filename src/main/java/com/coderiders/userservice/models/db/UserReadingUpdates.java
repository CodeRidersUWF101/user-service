package com.coderiders.userservice.models.db;

import jakarta.persistence.*;
import lombok.*;
import java.sql.Timestamp;

@Entity
@Table(name = "userreadingprogressupdates")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserReadingUpdates {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long update_id;
    private String user_clerk_id;
    private String book_id;
    private int previous_page;
    private int current_page;
    private Timestamp update_timestamp;
}