package com.elibrary.backend.booklocation;

import com.elibrary.backend.booklocation.id.BookLocationId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BookLocationRepository extends JpaRepository<BookLocation, BookLocationId> {
    @Query(
        value =
            "SELECT bl.* " +
            "FROM books_locations bl " +
            "JOIN extensions e ON e.id = bl.extension_id " +
            "WHERE bl.book_id = :bookId " +
            "AND (:startsWith IS NULL OR " +
            "LOWER(e.name) LIKE LOWER(CONCAT(:startsWith, '%'))) " +
            "ORDER BY bl.book_id, bl.extension_id " +
            "OFFSET :offset LIMIT :limit",
        nativeQuery = true
    )
    List<BookLocation> findByBookId(
        @Param("bookId") Integer bookId,
        @Param("startsWith") String startsWith,
        @Param("offset") int offset,
        @Param("limit") int limit);
}
