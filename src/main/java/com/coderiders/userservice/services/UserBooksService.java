package com.coderiders.userservice.services;

import com.coderiders.commonutils.models.UserLibrary;
import com.coderiders.userservice.models.db.UserBook;
import com.coderiders.userservice.models.request.SaveBookRequest;

import java.util.List;

public interface UserBooksService {
    Long saveBook(SaveBookRequest bookToSave);
    List<UserBook> getUserBook(String clerkId);
    List<UserLibrary> getUserLibrary(String clerkId);
}
