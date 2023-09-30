package com.coderiders.userservice.services.Impl;

import com.coderiders.commonutils.models.UserLibrary;
import com.coderiders.userservice.exceptions.BookNotFoundException;
import com.coderiders.userservice.models.ReadingStatus;
import com.coderiders.userservice.models.db.Book;
import com.coderiders.userservice.models.db.User;
import com.coderiders.userservice.models.db.UserBook;
import com.coderiders.userservice.models.request.SaveBookRequest;
import com.coderiders.userservice.repositories.BookRepository;
import com.coderiders.userservice.repositories.UserBookRepository;
import com.coderiders.userservice.repositories.UserRepository;
import com.coderiders.userservice.services.UserBooksService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserBooksServiceImpl implements UserBooksService {
    private final UserBookRepository userBookRepository;
    private final BookRepository bookRepository;
    private final UserRepository userRepository;
    @PersistenceContext
    private EntityManager entityManager;

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
        newBook.setReadingStatus(ReadingStatus.NOT_STARTED.value);

        return userBookRepository.save(newBook).getId();

    }

    @Override
    public List<UserBook> getUserBook(String clerkId) {
        return userBookRepository.findByUserClerkId(clerkId);
    }

    @Override
    public List<UserLibrary> getUserLibrary(String clerkId) {
        var resultList = entityManager.createNativeQuery("SELECT * FROM get_user_library(:p_clerk_id)")
                .setParameter("p_clerk_id", clerkId)
                .getResultList();

        return mapResultToUserLibrary(resultList);
    }

    private List<UserLibrary> mapResultToUserLibrary(List<Object[]> resultList) {
        // Create a list to store UserLibrary objects
        List<UserLibrary> userLibraries = new ArrayList<>();

        // Iterate through the result rows and create UserLibrary objects
        for (Object[] row : resultList) {
            UserLibrary userLibrary = new UserLibrary(
                    (String) row[0],          // book_id
                    (String) row[1],          // api_id
                    (String) row[2],          // title
                    (String[]) row[3],       // author
                    (String) row[4],          // publisher
                    (String) row[5],          // published_date
                    (String) row[6],          // description
                    (String) row[7],          // isbn_10
                    (String) row[8],          // isbn_13
                    (Integer) row[9],         // page_count
                    (String) row[10],         // print_type
                    (String[]) row[11],       // categories
                    (Double) row[12],          // average_rating
                    (Integer) row[13],        // ratings_count
                    (String) row[14],         // maturity_rating
                    (String) row[15],         // small_thumbnail
                    (String) row[16],         // thumbnail
                    (String) row[17],         // reading_status
                    (Integer) row[18],        // last_page_read
                    (Timestamp) row[19]       // last_reading_update
            );
            userLibraries.add(userLibrary);
        }

        return userLibraries;
    }
}