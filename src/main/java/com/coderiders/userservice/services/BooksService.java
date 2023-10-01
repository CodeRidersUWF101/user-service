package com.coderiders.userservice.services;

import com.coderiders.commonutils.models.googleBooks.GoogleBook;
import com.coderiders.userservice.models.db.Book;

import java.util.List;

public interface BooksService {
    List<Book> saveBooks(List<GoogleBook> books);
}
