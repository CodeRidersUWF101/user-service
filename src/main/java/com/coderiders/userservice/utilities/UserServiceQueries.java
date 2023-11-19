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

//    public final static String getSqlQueryFindFriends = """
//            SELECT
//                CASE
//                    WHEN u.user_clerk_Id2 = :first THEN u.user_clerk_Id1
//                    WHEN u.user_clerk_Id1 = :first THEN u.user_clerk_Id2
//                END AS result_column
//            FROM Friends u
//            WHERE status = 'CONFIRMED';
//            """;

    public final static String getSqlQueryFindFriends = """
            SELECT
              u.user_clerk_Id1,
              u.user_clerk_Id2
            FROM Friends u
            WHERE user_clerk_Id1 = :first OR user_clerk_Id2 = :first
            AND status = 'CONFIRMED';
            """;

//    public final static String getSqlQueryFindFriends2 = """
//            SELECT u.user_clerk_Id1
//            FROM Friends u
//            WHERE user_clerk_Id2 = :first
//            AND status = 'CONFIRMED';
//
//            """;

}
