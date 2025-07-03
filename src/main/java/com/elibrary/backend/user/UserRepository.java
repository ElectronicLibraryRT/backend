package com.elibrary.backend.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Integer> {
    @Query(
        value =
            "SELECT * " +
            "FROM users " +
            "WHERE (:startsWith IS NULL OR " +
            "LOWER(name) LIKE LOWER(CONCAT(:startsWith, '%'))) " +
            "ORDER BY id" +
            "OFFSET :offset LIMIT :limit",
        nativeQuery = true
    )
    List<User> findUsers(
        @Param("startsWith") String startsWith,
        @Param("offset") int offset,
        @Param("limit") int limit);
}
