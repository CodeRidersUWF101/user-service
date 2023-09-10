package com.coderiders.userservice.repositories;

import com.coderiders.userservice.models.db.Book;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookRepository extends JpaRepository<Book, Long> {
    List<Book> findByAuthor(String author);

    List<Book> findByTitleContainingIgnoreCase(String title);
}
