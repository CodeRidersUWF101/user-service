package com.coderiders.userservice.services.Impl;


import com.coderiders.commonutils.models.UserLibraryWithBookDetails;
import com.coderiders.commonutils.models.googleBooks.SaveBookRequest;
import com.coderiders.userservice.exceptions.BookNotFoundException;
import com.coderiders.userservice.models.ReadingStatus;
import com.coderiders.userservice.models.db.Book;
import com.coderiders.userservice.models.db.User;
import com.coderiders.userservice.repositories.BookRepository;
import com.coderiders.userservice.repositories.UserLibraryRepository;
import com.coderiders.userservice.repositories.UserRepository;
import com.coderiders.userservice.services.UserLibraryService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class UserLibraryServiceImpl implements UserLibraryService {

    private final UserLibraryRepository userLibraryRepository;
    private final BookRepository bookRepository;
    private final UserRepository userRepository;
    private final EntityManager entityManager;

    @Override
    public Long saveBook(SaveBookRequest bookToSave) {
        User newUser = userRepository.findByClerkId(bookToSave.getClerkId());
        Book bookSaving = bookRepository.findByIsbn10OrIsbn13(bookToSave.getIsbn10(), bookToSave.getIsbn13()).orElseGet(null);
        if (bookSaving == null) {
            throw new BookNotFoundException("Book with ISBN10: " + bookToSave.getIsbn10() + " and/or ISBN13: " + bookToSave.getIsbn13() + " could not be found.");
        }

        com.coderiders.userservice.models.db.UserLibrary newBook = new com.coderiders.userservice.models.db.UserLibrary();
        newBook.setUser(newUser);
        newBook.setBook(bookSaving);
        newBook.setAddedDate(LocalDateTime.now());
        newBook.setReadingStatus(ReadingStatus.NOT_STARTED.value);

        return userLibraryRepository.save(newBook).getId();

    }

    @Override
    public List<UserLibraryWithBookDetails> getUserLibraryForClerkId(String clerkId) {
        String sqlQuery = "SELECT ul.book_id, b.api_id, b.title, b.author, b.publisher, b.published_date, " +
                "b.description, b.isbn_10, b.isbn_13, b.page_count, b.print_type, b.categories, b.average_rating, " +
                "b.ratings_count, b.maturity_rating, b.small_thumbnail, b.thumbnail, ul.reading_status, " +
                "ul.last_page_read, ul.last_updated " +
                "FROM UserLibrary ul " +
                "JOIN Books b ON ul.book_id = b.book_id " +
                "WHERE ul.user_clerk_id = :clerkId";

        Query query = entityManager.createNativeQuery(sqlQuery)
                .setParameter("clerkId", clerkId);

        List<UserLibraryWithBookDetails> userLibraryList = new ArrayList<>();
        List<Object[]> resultList = query.getResultList();

        for (Object[] row : resultList) {
            UserLibraryWithBookDetails userLibrary = new UserLibraryWithBookDetails();
            userLibrary.setBook_id((String) row[0]);
            userLibrary.setApi_id((String) row[1]);
            userLibrary.setTitle((String) row[2]);
            userLibrary.setAuthor((String[]) row[3]);
            userLibrary.setPublisher((String) row[4]);
            userLibrary.setPublished_date((String) row[5]);
            userLibrary.setDescription((String) row[6]);
            userLibrary.setIsbn_10((String) row[7]);
            userLibrary.setIsbn_13((String) row[8]);

            userLibrary.setPage_count(row[9] != null ? (Integer) row[9] : null);
            userLibrary.setAverage_rating(row[12] != null ? (Double) row[12] : null);

            userLibrary.setRatings_count((Integer) row[13]);
            userLibrary.setMaturity_rating((String) row[14]);
            userLibrary.setSmall_thumbnail((String) row[15]);
            userLibrary.setThumbnail((String) row[16]);
            userLibrary.setReading_status((String) row[17]);

            userLibrary.setLast_page_read(row[18] != null ? (Integer) row[18] : null);
            userLibrary.setLast_reading_update(row[19] != null ? (Timestamp) row[19] : null);

            userLibraryList.add(userLibrary);
        }

        return userLibraryList;
    }
}