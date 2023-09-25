package com.coderiders.userservice.models.db;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;

import java.time.LocalDate;

@Data
@Entity
@Table(name = "books")
@NoArgsConstructor
@AllArgsConstructor
public class Book {

    @Id
    @Column(name = "book_id")
    private String bookId;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String author;

    @Column
    private String publisher;

    @Column(name = "published_date")
    private LocalDate publishedDate;

    @Column(length = 1000)  // Assuming we want a longer description column.
    private String description;

    @Column(name = "isbn_10")
    private String isbn10;

    @Column(name = "isbn_13")
    private String isbn13;

    @Column(name = "page_count")
    private Integer pageCount;

    @Column(name = "print_type")
    private String printType;


    @Column(name = "categories")
    @Type(value = com.coderiders.userservice.config.CustomStringArrayType.class)
    private String[] categories;

    @Column(name = "average_rating")
    private Float averageRating;

    @Column(name = "ratings_count")
    private Integer ratingsCount;

    @Column(name = "maturity_rating")
    private String maturityRating;

    @Column(name = "small_thumbnail")
    private String smallThumbnail;

    @Column
    private String thumbnail;

// getters, setters, and other methods
}