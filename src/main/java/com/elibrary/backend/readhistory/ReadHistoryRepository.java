package com.elibrary.backend.readhistory;

import com.elibrary.backend.readhistory.id.ReadHistoryId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ReadHistoryRepository extends JpaRepository<ReadHistory, ReadHistoryId> {
    @Query(
        value =
            "SELECT rh.* " +
            "FROM read_history rh " +
            "JOIN books b ON b.id = rh.book_id " +
            "WHERE rh.user_id = :userId " +
            "AND (:startsWith IS NULL OR " +
            "LOWER(b.title) LIKE LOWER(CONCAT(:startsWith, '%'))) " +
            "ORDER BY fb.book_id, fb.extension_id " +
            "OFFSET :offset LIMIT :limit",
        nativeQuery = true
    )
    List<ReadHistory> findByUserId(
        @Param("userId") Integer userId,
        @Param("startsWith") String startsWith,
        @Param("offset") int offset,
        @Param("limit") int limit);
}
