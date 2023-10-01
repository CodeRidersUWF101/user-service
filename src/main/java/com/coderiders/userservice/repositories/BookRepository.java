package com.coderiders.userservice.repositories;

import com.coderiders.userservice.models.db.Book;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BookRepository extends JpaRepository<Book, Long> {

    Optional<Book> findByIsbn10OrIsbn13(String isbn10, String isbn13);

    List<Book> findAllByApiIdIn(List<String> apiIds);

}
