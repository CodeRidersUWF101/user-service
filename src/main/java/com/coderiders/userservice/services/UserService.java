package com.coderiders.userservice.services;

import com.coderiders.commonutils.models.SmallUser;
import com.coderiders.commonutils.models.UtilsUser;
import com.coderiders.commonutils.models.requests.AddFriend;
import com.coderiders.commonutils.models.requests.GetFriendsBooks;
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
    List<SmallUser> getPendingFriends(String clerkId);
    List<GetFriendsBooks> getFriendsBooks(String clerkId);
    List<UtilsUser> getAllUsersNotBlocked(String clerk_id);
}
