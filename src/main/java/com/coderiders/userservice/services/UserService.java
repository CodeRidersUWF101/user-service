package com.coderiders.userservice.services;

import com.coderiders.commonutils.models.UtilsUser;
import com.coderiders.commonutils.models.requests.AddFriend;
import com.coderiders.commonutils.models.requests.UpdateProgress;
import com.coderiders.userservice.models.db.User;

import java.util.List;

public interface UserService {
    List<User> getAllUsersWithFirstName(String firstName);
    User getUserByClerkId(String clerkId);
    UtilsUser addNewUser(UtilsUser user);
    UpdateProgress updateReadingProgress(UpdateProgress updateProgress);
    List<UtilsUser> getAllUsersByClerkId(List<String> clerkIds);
    AddFriend addFriend(AddFriend friendRequest);
}
