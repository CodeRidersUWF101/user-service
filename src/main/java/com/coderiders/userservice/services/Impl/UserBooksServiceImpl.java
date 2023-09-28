package com.coderiders.userservice.services.Impl;

import com.coderiders.userservice.exceptions.BookNotFoundException;
import com.coderiders.userservice.models.db.Book;
import com.coderiders.userservice.models.db.User;
import com.coderiders.userservice.models.db.UserBook;
import com.coderiders.userservice.models.request.SaveBookRequest;
import com.coderiders.userservice.repositories.BookRepository;
import com.coderiders.userservice.repositories.UserBookRepository;
import com.coderiders.userservice.repositories.UserRepository;
import com.coderiders.userservice.services.UserBooksService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class UserBooksServiceImpl implements UserBooksService {
    private final UserBookRepository userBookRepository;
    private final BookRepository bookRepository;
    private final UserRepository userRepository;
    @Override
    public Long saveBook(SaveBookRequest bookToSave) {
        User newUser = userRepository.findByClerkId(bookToSave.getClerkId());
        Book bookSaving = bookRepository.findByIsbn10OrIsbn13(bookToSave.getIsbn_10(), bookToSave.getIsbn_13()).orElseGet(null);
        if (bookSaving == null) {
            throw new BookNotFoundException("Book with ISBN10: " + bookToSave.getIsbn_10() + " and/or ISBN13: " + bookToSave.getIsbn_13() + " could not be found.");
        }

        UserBook newBook = new UserBook();
        newBook.setUser(newUser);
        newBook.setBook(bookSaving);
        newBook.setAddedDate(LocalDateTime.now());
        newBook.setReadingStatus("NOT_STARTED");

        return userBookRepository.save(newBook).getId();

    }
}
