package com.coderiders.userservice.services;



import com.coderiders.userservice.models.commonutils.models.UserLibraryWithBookDetails;
import com.coderiders.userservice.models.commonutils.models.googleBooks.SaveBookRequest;

import java.util.List;

public interface UserLibraryService {
    Long saveBook(SaveBookRequest bookToSave);
    String saveBookCustom(SaveBookRequest bookToSave);
    List<UserLibraryWithBookDetails> getUserLibraryForClerkId(String userClerkId);
    String removeBook(String bookId, String clerkId);
}
