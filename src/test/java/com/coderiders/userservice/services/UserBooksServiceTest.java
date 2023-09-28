package com.coderiders.userservice.services;

import com.coderiders.userservice.models.db.Book;
import com.coderiders.userservice.models.db.User;
import com.coderiders.userservice.models.db.UserBook;
import com.coderiders.userservice.models.request.SaveBookRequest;
import com.coderiders.userservice.repositories.BookRepository;
import com.coderiders.userservice.repositories.UserBookRepository;
import com.coderiders.userservice.repositories.UserRepository;
import com.coderiders.userservice.services.Impl.UserBooksServiceImpl;
import com.coderiders.userservice.services.Impl.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.times;

@ExtendWith(SpringExtension.class)
public class UserBooksServiceTest {

    // used to mock the service layer
    @Mock
    private UserBookRepository userBookRepository;
    @Mock
    private BookRepository bookRepository;
    @Mock
    private UserRepository userRepository;

    // used to service the controller
    private UserServiceImpl userServiceImpl;
    private UserBooksService userBooksService;
    private UserBooksServiceImpl userBooksServiceImpl;

    // implement the mocked databases into the service we are testing
    @BeforeEach
    void setUp() {
        userBooksServiceImpl = new UserBooksServiceImpl(userBookRepository, bookRepository, userRepository);
        userServiceImpl = new UserServiceImpl(userRepository);
    }

    // find user test find a user object based off of clerk ID
    // without actually hitting the database
    @Test
    void findUserTest() {
        // arrange
        // A user must be looked up to make sure that they exist
        // Mock object to return
        LocalDateTime signUpDate = LocalDateTime.now();
        User user = User.builder().
                username("Dill").
                firstName("Dillon").
                lastName("Vaughan").
                signupDate(signUpDate).
                clerkId("50156").
                userId(1L).
                build();

        Mockito.when(userRepository.findByClerkId(Mockito.anyString()))
                        .thenReturn(user);

        // act
        var foundUser = userServiceImpl.getUserByClerkId(user.getClerkId());

        // assertions
        // assert user is not null
        assertNotNull(foundUser);

        // assert that the user exist at the correct clerk ID
        assertEquals("50156", foundUser.getClerkId());

        // assert that the user exist at the correct user ID
        assertEquals(1L, foundUser.getUserId());
    }


    // This test will test the saveBook() method inside
    // the User Book Service
    @Test
    void savingBooksTest() {
        // arrange
        // data to return for a mock

        // A user must be looked up to make sure that they exist
        // Mock object to return
        LocalDateTime signUpDate = LocalDateTime.now();
        User user = User.builder().username("Dill").firstName("Dillon").lastName("Vaughan").signupDate(signUpDate).clerkId("50156").userId(1L).build();

        // Mocking the database return
        Mockito.when(userRepository.findByClerkId(Mockito.any()))
                .thenReturn(user);

        // Values needed to create a book.
        // This book was pulled as an exact match from the database
        String[] author = {"J.R.R. Tolkien"};
        LocalDate publishDate = LocalDate.of(1937, 9, 21);
        String[] categories = {"Fantasy"};

        // saving a book first requires a check into our
        // book data
        // build a book database to ensure that the book is there
        // this will build a book and then mock a return object once
        // the call has been made to our database
        Book book = Book.builder().
                bookId("b2gV0CQDvW8C").
                title("The Hobbit").
                author(author).
                publisher("Houghton Mifflin").
                publishedDate(publishDate).
                description("Bilbo Baggins, a hobbit, embarks on a quest.").
                isbn10("1234567890").
                isbn13("0987654321").
                pageCount(310).
                printType("BOOK").
                categories(categories).
                averageRating(4.8F).
                ratingsCount(5000).
                maturityRating("NOT_MATURE").
                smallThumbnail("http://example.com/small_thumbnail.jpg").
                thumbnail("http://example.com/thumbnail.jpg").
                build();

        // when searching for a book, return the above created book
        Mockito.when(bookRepository.findByIsbn10OrIsbn13(Mockito.any(), Mockito.any()))
                .thenReturn(Optional.ofNullable(book));

        // creating a user book to mock the database call
        LocalDateTime todayDate = LocalDateTime.now();
        UserBook userBook = UserBook.builder().
                id(1l).
                user(user).
                book(book).
                addedDate(todayDate).
                readingStatus("NOT_STARTED").
                build();

        // when searching for a user book, return the above user book
        Mockito.when(userBookRepository.save(Mockito.any()))
                .thenReturn(userBook);

        List<UserBook> userBookList = new ArrayList<UserBook>();
        userBookList.add(userBook);

        // when searching for a list of books from a user, return the above list of books
        Mockito.when(userBookRepository.findByUserClerkId(Mockito.any()))
                .thenReturn(userBookList);

        SaveBookRequest saveBookRequest = SaveBookRequest.builder().clerkId("50156").isbn_10("2312546785").build();

        // act
        var bookResponse = userBooksServiceImpl.saveBook(saveBookRequest);

        // assert
        // Not null response
        assertNotNull(bookResponse);

        // Expected Behavior, this is how many times we expected the database to have a save implemented
        Mockito.verify(userBookRepository, times(1)).save((UserBook) Mockito.any());

        // Finding the book that was saved
        assertEquals(userBookList, userBookRepository.findByUserClerkId("50156"));
    }
}
