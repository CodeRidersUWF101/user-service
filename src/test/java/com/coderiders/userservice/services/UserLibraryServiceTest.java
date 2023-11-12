package com.coderiders.userservice.services;

import com.coderiders.userservice.models.commonutils.models.UtilsUser;
import com.coderiders.userservice.models.commonutils.models.googleBooks.SaveBookRequest;
import com.coderiders.userservice.models.db.Book;
import com.coderiders.userservice.models.db.User;
import com.coderiders.userservice.models.db.UserLibrary;
import com.coderiders.userservice.repositories.BookRepository;
import com.coderiders.userservice.repositories.UserLibraryRepository;
import com.coderiders.userservice.repositories.UserRepository;
import com.coderiders.userservice.services.Impl.UserLibraryServiceImpl;
import com.coderiders.userservice.services.Impl.UserServiceImpl;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.coderiders.userservice.utilities.Utilities.bookToULWBD;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(SpringExtension.class)
public class UserLibraryServiceTest {

    // used to mock the service layer
    @Mock
    private UserLibraryRepository userBookRepository;
    @Mock
    private BookRepository bookRepository;
    @Mock
    private UserRepository userRepository;
    @Mock
    private NamedParameterJdbcTemplate jdbcTemplate;

    // used to service the controller
    private UserServiceImpl userServiceImpl;
    private UserLibraryService userLibraryService;
    private UserLibraryServiceImpl userBooksServiceImpl;
    private EntityManager entityManager;




    // implement the mocked databases into the service we are testing
    @BeforeEach
    void setUp() {
        userBooksServiceImpl = new UserLibraryServiceImpl(userBookRepository, bookRepository, entityManager);
        userServiceImpl = new UserServiceImpl(userRepository, entityManager, jdbcTemplate);
    }

    // find user test find a user object based off of clerk ID
    // without actually hitting the database
    @Test
    void findUserTest() {
        // arrange
        // A user must be looked up to make sure that they exist
        // Mock object to return
        Mockito.when(userRepository.findByClerkId(Mockito.anyString())).thenReturn(getUser());

        // act
        User foundUser = userServiceImpl.getUserByClerkId("50156");

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
        List<UserLibrary> userBookList = new ArrayList<>(List.of(getUserLibrary()));

        // Mock object to return
        // Mocking the database return
        Mockito.when(bookRepository.findByIsbn10OrIsbn13(Mockito.any(), Mockito.any())).thenReturn(Optional.ofNullable(getBook()));
        Mockito.when(userBookRepository.save(Mockito.any())).thenReturn(getUserLibrary());
        Mockito.when(userBookRepository.findAllByUserClerkId(Mockito.any())).thenReturn(userBookList);

        Optional<Book> book = bookRepository.findByIsbn10OrIsbn13("", "");

        assertNotNull(book.get());
        SaveBookRequest saveBookRequest = SaveBookRequest.builder()
                .user(getModelsUser())
                .book(bookToULWBD(book.get()))
                .build();

        var bookResponse = userBooksServiceImpl.saveBook(saveBookRequest);

        // Finding the book that was saved
        assertEquals(userBookList, userBookRepository.findAllByUserClerkId("50156"));
    }

    private static UserLibrary getUserLibrary() {
        return UserLibrary.builder()
                .userClerkId("50156")
                .bookId("b2gV0CQDvW8C").
                addedDate(LocalDateTime.now()).
                readingStatus("NOT_STARTED").
                build();
    }

    private static User getUser() {
        return User.builder().
                username("Dill").
                firstName("Dillon").
                lastName("Vaughan").
                signupDate(LocalDateTime.now()).
                clerkId("50156")
                .userId(1L)
                .imageUrl("https://example.com")
                .build();
    }

    private static UtilsUser getModelsUser() {
        return UtilsUser.builder().
                username("Dill").
                firstName("Dillon").
                lastName("Vaughan").
                clerkId("50156")
                .imageUrl("https://example.com")
                .build();
    }

    private static Book getBook() {
        String[] author = {"J.R.R. Tolkien"};
        String[] categories = {"Fantasy"};

        return Book.builder().
                bookId("b2gV0CQDvW8C").
                title("The Hobbit").
                author(author).
                publisher("Houghton Mifflin").
                publishedDate("1999-10-01").
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
    }
}
