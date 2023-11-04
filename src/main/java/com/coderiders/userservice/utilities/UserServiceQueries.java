package com.coderiders.userservice.utilities;

public class UserServiceQueries {

    public final static String sqlQueryFindFriendsNotBlocked = """
                SELECT u.username,
                u.first_name,
                u.last_name,
                u.image_url,
                u.clerk_id
                FROM Users u
                WHERE NOT EXISTS (
                SELECT 1 FROM Friends
                WHERE (user_clerk_id1 = u.clerk_id OR user_clerk_id2 = u.clerk_id)
                AND (user_clerk_id1 = :first OR user_clerk_id2 = :first)
                AND (status = 'CONFIRMED' OR status = 'BLOCKED')
                )
                AND clerk_id != :first
                """;

}
