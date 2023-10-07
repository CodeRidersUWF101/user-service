package com.coderiders.userservice.utilities;

import com.coderiders.commonutils.models.UserLibraryWithBookDetails;
import com.coderiders.commonutils.models.googleBooks.GoogleBook;
import com.coderiders.userservice.models.db.Book;

public class Utilities {

    public static final String ISBN_10 = "ISBN_10";
    public static final String ISBN_13 = "ISBN_13";

    public static Book convertToBook(final GoogleBook googleBook) {
        String isbn10 = getIndustryIdentifier(googleBook, ISBN_10);
        String isbn13 = getIndustryIdentifier(googleBook, ISBN_13);

        return Book.builder()
                .bookId(googleBook.id)
                .apiId(googleBook.id)
                .title(googleBook.volumeInfo.title)
                .author(googleBook.volumeInfo.authors != null ? googleBook.volumeInfo.authors.toArray(new String[0]) : null)
                .publisher(googleBook.volumeInfo.publisher)
                .publishedDate(googleBook.volumeInfo.publishedDate)
                .description(googleBook.volumeInfo.description)
                .isbn10(isbn10)
                .isbn13(isbn13)
                .pageCount(googleBook.volumeInfo.pageCount)
                .printType(googleBook.volumeInfo.printType)
                .categories(googleBook.volumeInfo.categories != null ? googleBook.volumeInfo.categories.toArray(new String[0]) : null)
                .averageRating((float) googleBook.volumeInfo.averageRating)
                .ratingsCount(googleBook.volumeInfo.ratingsCount)
                .maturityRating(googleBook.volumeInfo.maturityRating)
                .smallThumbnail(googleBook.volumeInfo.imageLinks != null ? googleBook.volumeInfo.imageLinks.smallThumbnail : null)
                .thumbnail(googleBook.volumeInfo.imageLinks != null ? googleBook.volumeInfo.imageLinks.thumbnail : null)
                .build();
    }

    private static String getIndustryIdentifier(final GoogleBook googleBook, final String type) {
        if (googleBook.volumeInfo.industryIdentifiers == null) { return null; }

        return googleBook.volumeInfo.industryIdentifiers.stream()
                .filter(b -> b.type.equalsIgnoreCase(type))
                .map(b -> b.identifier)
                .findFirst()
                .orElse(null);
    }

    public static UserLibraryWithBookDetails bookToULWBD(Book book) {
        return UserLibraryWithBookDetails.builder()
                .book_id(book.getBookId())
                .api_id(book.getApiId())
                .title(book.getTitle())
                .author(book.getAuthor())
                .publisher(book.getPublisher())
                .published_date(book.getPublishedDate())
                .description(book.getDescription())
                .isbn_10(book.getIsbn10())
                .isbn_13(book.getIsbn13())
                .page_count(book.getPageCount())
                .print_type(book.getPrintType())
                .categories(book.getCategories())
                .average_rating(Double.valueOf(book.getAverageRating()))
                .ratings_count(book.getRatingsCount())
                .maturity_rating(book.getMaturityRating())
                .small_thumbnail(book.getSmallThumbnail())
                .thumbnail(book.getThumbnail())
                .reading_status("NOT_STARTED")
                .last_page_read(null)
                .last_reading_update(null)
                .isInLibrary(false)
                .build();
    }

    public static Book ulwbdToBook(UserLibraryWithBookDetails bookDetails) {
        return Book.builder()
                .bookId(bookDetails.getBook_id())
                .apiId(bookDetails.getApi_id())
                .title(bookDetails.getTitle())
                .author(bookDetails.getAuthor())
                .publisher(bookDetails.getPublisher())
                .publishedDate(bookDetails.getPublished_date())
                .description(bookDetails.getDescription())
                .isbn10(bookDetails.getIsbn_10())
                .isbn13(bookDetails.getIsbn_13())
                .pageCount(bookDetails.getPage_count())
                .printType(bookDetails.getPrint_type())
                .categories(bookDetails.getCategories())
                .averageRating(Float.valueOf(String.valueOf(bookDetails.getAverage_rating())))
                .ratingsCount(bookDetails.getRatings_count())
                .maturityRating(bookDetails.getMaturity_rating())
                .smallThumbnail(bookDetails.getSmall_thumbnail())
                .thumbnail(bookDetails.getThumbnail())
                .build();
    }
}
