package com.coderiders.userservice.services;

import com.coderiders.userservice.models.commonutils.models.SmallUser;
import com.coderiders.userservice.models.commonutils.models.UtilsUser;
import com.coderiders.userservice.models.commonutils.models.requests.AddFriend;
import com.coderiders.userservice.models.commonutils.models.requests.GetFriendsBooks;
import com.coderiders.userservice.models.commonutils.models.requests.UpdateFriendRequest;
import com.coderiders.userservice.models.commonutils.models.requests.UpdateProgress;
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
    UpdateFriendRequest updateFriendRequest(UpdateFriendRequest updateRequest);
    List<UtilsUser> getAllUsersNotBlocked(String clerk_id);
    List<String> getAllFriends(String user_clerk_Id1);
}
