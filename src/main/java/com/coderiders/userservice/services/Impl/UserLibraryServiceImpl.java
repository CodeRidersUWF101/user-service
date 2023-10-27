package com.coderiders.userservice.services.Impl;

import com.coderiders.commonutils.models.UserLibraryWithBookDetails;
import com.coderiders.commonutils.models.UtilsUser;
import com.coderiders.commonutils.models.googleBooks.SaveBookRequest;
import com.coderiders.userservice.exceptions.UserServiceException;
import com.coderiders.userservice.models.ReadingStatus;
import com.coderiders.userservice.models.db.Book;
import com.coderiders.userservice.models.db.UserLibrary;
import com.coderiders.userservice.repositories.BookRepository;
import com.coderiders.userservice.repositories.UserLibraryRepository;
import com.coderiders.userservice.services.UserLibraryService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

import static com.coderiders.userservice.utilities.Utilities.ulwbdToBook;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class UserLibraryServiceImpl implements UserLibraryService {

    private final UserLibraryRepository userLibraryRepository;
    private final BookRepository bookRepository;
    private final EntityManager entityManager;

    @Override
    public Long saveBook(SaveBookRequest bookToSave) {
        UserLibraryWithBookDetails ulwbd = bookToSave.getBook();
        Book bookSaving = bookRepository.findByIsbn10OrIsbn13(ulwbd.getIsbn_10(), ulwbd.getIsbn_13()).orElseGet(Book::new);

        if (bookSaving.getBookId() == null) {
            bookSaving = bookRepository.save(ulwbdToBook(ulwbd));
        }

        UserLibrary userLibrary = new UserLibrary();
        userLibrary.setUserClerkId(bookToSave.getUser().getClerkId());
        userLibrary.setBookId(bookSaving.getBookId());
        userLibrary.setAddedDate(LocalDateTime.now());
        userLibrary.setReadingStatus(ReadingStatus.NOT_STARTED.value);

        return userLibraryRepository.save(userLibrary).getId();
    }

    @Override
    @Transactional
    public String saveBookCustom(SaveBookRequest bookRequest) {
        UtilsUser user = bookRequest.getUser();
        UserLibraryWithBookDetails book = bookRequest.getBook();
        try {

            String sql = "SELECT add_user_book_to_library(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

            Query query = entityManager.createNativeQuery(sql);

            // User object
            query.setParameter(1, user.getClerkId());
            query.setParameter(2, user.getUsername());
            query.setParameter(3, user.getFirstName());
            query.setParameter(4, user.getLastName());
            query.setParameter(5, user.getImageUrl());

            // Book object
            query.setParameter(6, book.getBook_id());
            query.setParameter(7, book.getApi_id());
            query.setParameter(8, book.getTitle());
            query.setParameter(9, book.getAuthor());
            query.setParameter(10, book.getPublisher());
            query.setParameter(11, book.getPublished_date());
            query.setParameter(12, book.getDescription());
            query.setParameter(13, book.getIsbn_10());
            query.setParameter(14, book.getIsbn_13());
            query.setParameter(15, book.getPage_count());
            query.setParameter(16, book.getPrint_type());
            query.setParameter(17, book.getCategories());
            query.setParameter(18, book.getAverage_rating());
            query.setParameter(19, book.getRatings_count());
            query.setParameter(20, book.getMaturity_rating());
            query.setParameter(21, book.getSmall_thumbnail());
            query.setParameter(22, book.getThumbnail());

            return query.getSingleResult().toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<UserLibraryWithBookDetails> getUserLibraryForClerkId(String clerkId) {
        String attempt2 = """
                SELECT
                b.*,
                ul.reading_status,
                ul.last_page_read,
                ul.last_updated
                FROM UserLibrary ul
                JOIN Books b ON ul.book_id = b.book_id
                WHERE ul.user_clerk_id = :clerkId
                """;

        Query query = entityManager.createNativeQuery(attempt2).setParameter("clerkId", clerkId);

        List<Object[]> resultList = query.getResultList();
        return bookToUserLibraryWithBookDetails(resultList);
    }

    @Override
    @Transactional
    public String removeBook(String bookId, String clerkId) {
        String queryStr = "SELECT remove_book_from_userlibrary(:clerkId, :bookId)";

        Query query = entityManager.createNativeQuery(queryStr)
                .setParameter("clerkId", clerkId)
                .setParameter("bookId", bookId);

        String results = query.getSingleResult().toString();
        log.debug("Delete Results: {}", results);

        if (results == null || results.equalsIgnoreCase("null")) {
            throw new UserServiceException("Failed to remove book from user's library. Might not exist");
        }
        return results;
    }

    private List<UserLibraryWithBookDetails> bookToUserLibraryWithBookDetails(List<Object[]> resultList) {
        return resultList.stream()
                .map(row -> UserLibraryWithBookDetails.builder()
                .book_id((String) row[0])
                .api_id((String) row[1])
                .title((String) row[2])
                .author((String[]) row[3])
                .publisher((String) row[4])
                .published_date((String) row[5])
                .description((String) row[6])
                .isbn_10((String) row[7])
                .isbn_13((String) row[8])
                .page_count(row[9] != null ? (Integer) row[9] : null)
                .categories((String[]) row[11])
                .average_rating(row[12] != null ? (Double) row[12] : null)
                .ratings_count((Integer) row[13])
                .maturity_rating((String) row[14])
                .small_thumbnail((String) row[15])
                .thumbnail((String) row[16])
                .reading_status((String) row[17])
                .last_page_read(row[18] != null ? (Integer) row[18] : null)
                .last_reading_update(row[19] != null ? (Timestamp) row[19] : null)
                .isInLibrary(true)
                .build()).toList();
    }
}