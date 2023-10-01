package com.coderiders.userservice.models.db;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;

@Data
@Entity
@Builder
@Table(name = "books")
@AllArgsConstructor
@NoArgsConstructor
public class Book {

    @Id
    @Column(name = "book_id")
    private String bookId;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false, name = "api_id")
    private String apiId;

    @Column(nullable = false)
    @Type(value = com.coderiders.userservice.config.CustomStringArrayType.class)
    private String[] author;

    @Column
    private String publisher;

    @Column(name = "published_date")
    private String publishedDate;

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
}