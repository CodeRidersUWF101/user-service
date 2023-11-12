package com.coderiders.userservice.services.Impl;


import com.coderiders.userservice.exceptions.UserServiceException;
import com.coderiders.userservice.models.commonutils.models.SmallUser;
import com.coderiders.userservice.models.commonutils.models.UtilsUser;
import com.coderiders.userservice.models.commonutils.models.requests.AddFriend;
import com.coderiders.userservice.models.commonutils.models.requests.GetFriendsBooks;
import com.coderiders.userservice.models.commonutils.models.requests.UpdateFriendRequest;
import com.coderiders.userservice.models.commonutils.models.requests.UpdateProgress;
import com.coderiders.userservice.models.db.User;
import com.coderiders.userservice.repositories.UserRepository;
import com.coderiders.userservice.services.UserService;
import com.coderiders.userservice.utilities.UserServiceQueries;
import com.coderiders.userservice.utilities.Queries;
import com.coderiders.userservice.utilities.Utilities;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final EntityManager entityManager;
    private final NamedParameterJdbcTemplate jdbcTemplate;

    public List<User> getAllUsersWithFirstName(String firstName) {
        return userRepository.findByFirstName(firstName);
    }

    public User getUserByClerkId(String clerkId) {
        return userRepository.findByClerkId(clerkId);
    }

    @Override
    public UtilsUser addNewUser(UtilsUser user) {

        User newUser = User.builder()
                .clerkId(user.getClerkId())
                .username(user.getUsername())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .signupDate(LocalDateTime.now())
                .imageUrl(user.getImageUrl())
                .build();

        User dbUser = userRepository.findByClerkId(user.getClerkId());

        if (dbUser == null) {
            User newUsr = userRepository.save(newUser);
            return userToOtherUserDTO(newUsr);
        }
        return user;
    }

    @Override
    public UpdateProgress updateReadingProgress(UpdateProgress updateProgress) {
        String queryStr = "SELECT update_reading_progress(:clerkId, :bookId , :currentPage)";

        Query query = entityManager.createNativeQuery(queryStr)
                .setParameter("clerkId", updateProgress.getClerkId())
                .setParameter("bookId", updateProgress.getBookId())
                .setParameter("currentPage", updateProgress.getCurrentPage());


        String compositeResult = query.getSingleResult().toString();

        if (compositeResult == null || compositeResult.equalsIgnoreCase("null")) {
            throw new UserServiceException("Update User Reading Progress Failed.");
        }

        String cleanedResult = compositeResult.substring(1, compositeResult.length() - 1);
        String[] parts = cleanedResult.split(",");

        return UpdateProgress
                .builder()
                .clerkId(parts[0])
                .bookId(parts[1])
                .currentPage(Integer.parseInt(parts[2]))
                .build();
    }

    private UtilsUser userToOtherUserDTO(User user) {
        UtilsUser retUser = new UtilsUser();

        retUser.setClerkId(user.getClerkId());
        retUser.setFirstName(user.getFirstName());
        retUser.setLastName(user.getLastName());
        retUser.setUsername(user.getUsername());
        return retUser;
    }

    @Override
    public List<UtilsUser> getAllUsersByClerkId(List<String> clerkIds) {
        String sql = "SELECT * FROM users WHERE clerk_id IN (:clerkIds)";
        MapSqlParameterSource parameters = new MapSqlParameterSource();
        parameters.addValue("clerkIds", clerkIds);
        return jdbcTemplate.query(sql, parameters, (rs, rowNum) -> {
            UtilsUser user = new UtilsUser();
            user.setClerkId(rs.getString("clerk_id"));
            user.setFirstName(rs.getString("first_name"));
            user.setLastName(rs.getString("last_name"));
            user.setUsername(rs.getString("username"));
            user.setImageUrl(rs.getString("image_url"));
            return user;
        });
    }

    @Override
    @Transactional
    public AddFriend addFriend(AddFriend friendRequest) {
        String queryStr = "INSERT INTO Friends (user_clerk_id1, user_clerk_id2, status) VALUES (?, ?, ?)";
        String firstUser;
        String secondUser;

        if (friendRequest.getRequestingClerkId().compareTo(friendRequest.getFriendToAddClerkId()) > 0) {
            firstUser = friendRequest.getFriendToAddClerkId();
            secondUser = friendRequest.getRequestingClerkId();
        } else {
            firstUser = friendRequest.getRequestingClerkId();
            secondUser = friendRequest.getFriendToAddClerkId();
        }

        Query query = entityManager.createNativeQuery(queryStr)
                .setParameter(1, firstUser)
                .setParameter(2, secondUser)
                .setParameter(3, "PENDING");

            query.executeUpdate();

        return AddFriend
                .builder()
                .successString("SUCCESS")
                .build();

    }

    @Override
    public List<GetFriendsBooks> getFriendsBooks(String clerkId) {
        String sql = "SELECT * FROM get_friends_last_updated_books(:clerk_id) LIMIT 5;";
        MapSqlParameterSource parameters = new MapSqlParameterSource();
        parameters.addValue("clerk_id", clerkId);
        return jdbcTemplate.query(sql, parameters, (rs, rowNum) -> GetFriendsBooks.builder()
                .username(rs.getString("username"))
                .firstName(rs.getString("first_name"))
                .lastName(rs.getString("last_name"))
                .clerkId(rs.getString("clerk_id"))
                .imageUrl(rs.getString("image_url"))
                .lastBookTitle(rs.getString("last_book_title"))
                .lastBookId(rs.getString("last_book_api_id"))
                .lastBookUpdated(rs.getString("last_book_updated"))
                .build());
    }

    public List<UtilsUser> getAllUsersNotBlocked(String clerk_Id) {
        MapSqlParameterSource parameters = new MapSqlParameterSource();
        parameters.addValue("first", clerk_Id);
        String sql = UserServiceQueries.sqlQueryFindFriendsNotBlocked;
        return jdbcTemplate.query(sql, parameters, (rs, rowNum) ->
                new UtilsUser(rs.getString("username"),
                        rs.getString("first_name"),
                        rs.getString("last_name"),
                        rs.getString("clerk_id"),
                        rs.getString("image_url")
                ));
    }


    @Override
    public List<SmallUser> getPendingFriends(String clerkId) {
        MapSqlParameterSource parameters = new MapSqlParameterSource();
        parameters.addValue("clerkId", clerkId);

        return jdbcTemplate.query(Queries.GET_PENDING_FRIENDS, parameters, (rs, rowNum) -> {
            SmallUser user = new SmallUser();
            String clerk_id = rs.getString("clerk_id");
            String displayName = Utilities.determineDisplayName(
                    rs.getString("username"),
                    rs.getString("first_name"),
                    rs.getString("last_name"),
                    clerk_id);

            user.setClerkId(clerk_id);
            user.setDisplayName(displayName);
            user.setAvatarUrl(rs.getString("image_url"));
            return user;
        });
    }

    @Override
    @Transactional
    public UpdateFriendRequest updateFriendRequest(UpdateFriendRequest updateRequest) {
        String sql = "UPDATE friends SET status = ? WHERE user_clerk_id1 = ? AND user_clerk_id2 = ?;";

        Query query = entityManager.createNativeQuery(sql)
                .setParameter(1, updateRequest.getStatus())
                .setParameter(2, updateRequest.getClerkId())
                .setParameter(3, updateRequest.getFriendId());

        int rowsAffected = query.executeUpdate();

        return updateRequest;
    }

}
