package com.coderiders.userservice.utilities;

public class Queries {
    public static final String GET_PENDING_FRIENDS = """
                SELECT
                    u.username,
                    u.first_name,
                    u.last_name,
                    u.clerk_id,
                    u.image_url
                FROM Friends F
                JOIN Users U ON (U.clerk_id = F.user_clerk_id1 OR U.clerk_id = F.user_clerk_id2)
                WHERE (F.user_clerk_id1 = :clerkId OR F.user_clerk_id2 = :clerkId)
                  AND F.status = 'PENDING'
                  AND U.clerk_id != :clerkId;
                """;
}
