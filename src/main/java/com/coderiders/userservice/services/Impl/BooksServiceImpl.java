package com.coderiders.userservice.services.Impl;

import com.coderiders.userservice.models.commonutils.models.googleBooks.GoogleBook;
import com.coderiders.userservice.models.db.Book;
import com.coderiders.userservice.repositories.BookRepository;
import com.coderiders.userservice.services.BooksService;
import com.coderiders.userservice.utilities.Utilities;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BooksServiceImpl implements BooksService {

    private final BookRepository bookRepository;
    private final JdbcTemplate jdbcTemplate;

    /**
     * @deprecated
     */
    @Override
    public List<Book> saveBooks(List<GoogleBook> books) {
        List<String> ids = books.stream().map(GoogleBook::getId).toList();
        List<String> existingBookIds = bookRepository.findAllByApiIdIn(ids).stream().map(Book::getApiId).toList();
        List<Book> booksToSave = books.stream().filter(book -> !existingBookIds.contains(book.id)).map(Utilities::convertToBook).toList();
        bulkSaveBooks(booksToSave);

        return List.of(new Book());
    }

    /**
     * @deprecated
     */
    public void bulkSaveBooks(List<Book> books) {
        String sql = "INSERT INTO books (book_id, api_id, title, author, publisher, published_date, description, isbn_10, isbn_13, page_count, print_type, categories, average_rating, ratings_count, maturity_rating, small_thumbnail, thumbnail) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {
            @Override
            public void setValues(@NonNull PreparedStatement ps, int i) throws SQLException {
                Book book = books.get(i);
                setBookValues(ps, book);
            }
            @Override
            public int getBatchSize() {
                // Specify the number of statements in the batch
                return books.size();
            }
        });
    }

    /**
     * @deprecated
     */
    private void setBookValues(PreparedStatement ps, Book book) throws SQLException {
        ps.setString(1, book.getBookId());
        ps.setString(2, book.getApiId());
        ps.setString(3, book.getTitle());
        ps.setObject(4, book.getAuthor());
        ps.setString(5, book.getPublisher());
        ps.setString(6, book.getPublishedDate());
        ps.setString(7, book.getDescription());
        ps.setString(8, book.getIsbn10());
        ps.setString(9, book.getIsbn13());
        ps.setInt(10, book.getPageCount());
        ps.setString(11, book.getPrintType());
        ps.setObject(12, book.getCategories());
        ps.setFloat(13, book.getAverageRating());
        ps.setInt(14, book.getRatingsCount());
        ps.setString(15, book.getMaturityRating());
        ps.setString(16, book.getSmallThumbnail());
        ps.setString(17, book.getThumbnail());
    }
}
