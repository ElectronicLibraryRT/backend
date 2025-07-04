package com.elibrary.backend.book;

import com.elibrary.backend.author.Author;
import com.elibrary.backend.genre.Genre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BookRepository extends JpaRepository<Book, Integer> {
    @Query(
        value =
            "SELECT * " +
            "FROM books " +
            "WHERE (:startsWith IS NULL OR " +
            "LOWER(title) LIKE LOWER(CONCAT(:startsWith, '%'))) " +
            "ORDER BY id " +
            "OFFSET :offset LIMIT :limit",
        nativeQuery = true
    )
    List<Book> findBooks(
        @Param("startsWith") String startsWith,
        @Param("offset") int offset,
        @Param("limit") int limit);

    @Query(
        value =
            "SELECT g.* " +
            "FROM genres g " +
            "JOIN books_genres bg ON g.id = bg.genre_id " +
            "WHERE bg.book_id = :bookId " +
            "AND (:startsWith IS NULL OR " +
            "LOWER(g.name) LIKE LOWER(CONCAT(:startsWith, '%'))) " +
            "ORDER BY g.id " +
            "OFFSET :offset LIMIT :limit",
        nativeQuery = true
    )
    List<Genre> findGenresByBookId(
        @Param("bookId") Integer bookId,
        @Param("startsWith") String startsWith,
        @Param("offset") int offset,
        @Param("limit") int limit);

    @Query(
        value =
            "SELECT a.* " +
            "FROM authors a " +
            "JOIN books_authors ba ON a.id = ba.author_id " +
            "WHERE ba.book_id = :bookId " +
            "AND (:startsWith IS NULL OR " +
            "LOWER(a.name) LIKE LOWER(CONCAT(:startsWith, '%'))) " +
            "ORDER BY a.id " +
            "OFFSET :offset LIMIT :limit",
        nativeQuery = true
    )
    List<Author> findAuthorsByBookId(
        @Param("bookId") Integer bookId,
        @Param("startsWith") String startsWith,
        @Param("offset") int offset,
        @Param("limit") int limit);
}
