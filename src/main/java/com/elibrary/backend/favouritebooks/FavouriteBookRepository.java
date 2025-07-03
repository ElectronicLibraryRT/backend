package com.elibrary.backend.favouritebooks;

import com.elibrary.backend.favouritebooks.id.FavouriteBookId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface FavouriteBookRepository extends JpaRepository<FavouriteBook, FavouriteBookId> {
    @Query(
        value =
            "SELECT fb.* " +
            "FROM favourite_books fb " +
            "JOIN books b ON b.id = fb.book_id " +
            "WHERE fb.user_id = :userId " +
            "AND (:startsWith IS NULL OR " +
            "LOWER(b.title) LIKE LOWER(CONCAT(:startsWith, '%'))) " +
            "ORDER BY bl.book_id, bl.extension_id " +
            "OFFSET :offset LIMIT :limit",
        nativeQuery = true
    )
    List<FavouriteBook> findByUserId(
        @Param("userId") Integer userId,
        @Param("startsWith") String startsWith,
        @Param("offset") int offset,
        @Param("limit") int limit);
}
