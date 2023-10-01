package com.coderiders.userservice.services;


import com.coderiders.commonutils.models.UserLibraryWithBookDetails;
import com.coderiders.commonutils.models.googleBooks.SaveBookRequest;

import java.util.List;

public interface UserLibraryService {
    Long saveBook(SaveBookRequest bookToSave);
    List<UserLibraryWithBookDetails> getUserLibraryForClerkId(String userClerkId);
}
