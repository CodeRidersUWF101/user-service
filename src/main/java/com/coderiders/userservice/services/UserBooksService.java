package com.coderiders.userservice.services;

import com.coderiders.userservice.models.db.Book;
import com.coderiders.userservice.models.request.SaveBookRequest;

public interface UserBooksService {

    Long saveBook(SaveBookRequest bookToSave);


}
