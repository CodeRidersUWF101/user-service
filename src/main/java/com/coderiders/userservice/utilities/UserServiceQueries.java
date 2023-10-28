package com.coderiders.userservice.utilities;

public class UserServiceQueries {

    public static String getAllUsersThatAreNotBlocked(String requestingClerkId) {
        StringBuffer sqlQuery = new StringBuffer();
        sqlQuery.append("SELECT u.username,\n" +
                "       u.first_name,\n" +
                "       u.last_name,\n" +
                "       u.image_url,\n" +
                "       u.clerk_id\n" +
                "FROM Users u\n" +
                "WHERE NOT EXISTS (\n" +
                "    SELECT 1 FROM Friends\n" +
                "    WHERE (user_clerk_id1 = u.clerk_id OR user_clerk_id2 = u.clerk_id)\n" +
                "    AND (user_clerk_id1 = '" + requestingClerkId + "' OR user_clerk_id2 = '" + requestingClerkId + "')\n" +
                "    AND (status = 'CONFIRMED' OR status = 'BLOCKED')\n" +
                ")\n" +
                "AND clerk_id != '" + requestingClerkId + "';");
        return String.format(sqlQuery.toString());
    }
}
