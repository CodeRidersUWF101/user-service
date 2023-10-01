package com.coderiders.userservice.utilities;

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

}
