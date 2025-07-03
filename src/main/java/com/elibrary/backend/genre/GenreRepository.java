package com.elibrary.backend.genre;

import com.elibrary.backend.book.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface GenreRepository extends JpaRepository<Genre, Integer> {
    @Query(
        value =
            "SELECT * " +
            "FROM genres " +
            "WHERE (:startsWith IS NULL OR " +
            "LOWER(name) LIKE LOWER(CONCAT(:startsWith, '%'))) " +
            "ORDER BY id " +
            "OFFSET :offset LIMIT :limit",
        nativeQuery = true
    )
    List<Genre> findGenres(
        @Param("startsWith") String startsWith,
        @Param("offset") int offset,
        @Param("limit") int limit);

    @Query(
        value =
            "SELECT b.* " +
            "FROM books b " +
            "JOIN books_genres ba ON b.id = ba.book_id " +
            "WHERE ba.genre_id = :genreId " +
            "AND (:startsWith IS NULL OR " +
            "LOWER(b.title) LIKE LOWER(CONCAT(:startsWith, '%'))) " +
            "ORDER BY b.id " +
            "OFFSET :offset LIMIT :limit",
        nativeQuery = true
    )
    List<Book> findBooksByGenreId(
        @Param("genreId") Integer genreId,
        @Param("startsWith") String startsWith,
        @Param("offset") int offset,
        @Param("limit") int limit);
}
