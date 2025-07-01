package com.elibrary.backend.author;

import com.elibrary.backend.book.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AuthorRepository extends JpaRepository<Author, Integer> {
    @Query(
        value =
            "SELECT * " +
            "FROM authors " +
            "WHERE (:startsWith IS NULL OR " +
            "LOWER(name) LIKE LOWER(CONCAT(:startsWith, '%'))) " +
            "OFFSET :offset LIMIT :limit",
        nativeQuery = true
    )
    List<Author> findAuthors(
        @Param("startsWith") String startsWith,
        @Param("offset") int offset,
        @Param("limit") int limit);

    @Query(
        value =
            "SELECT b.* " +
            "FROM books b " +
            "JOIN books_authors ba ON b.id = ba.book_id " +
            "WHERE ba.author_id = :authorId " +
            "AND (:startsWith IS NULL OR " +
            "LOWER(b.title) LIKE LOWER(CONCAT(:startsWith, '%'))) " +
            "OFFSET :offset LIMIT :limit",
        nativeQuery = true
    )
    List<Book> findBooksByAuthorId(
        @Param("authorId") Integer authorId,
        @Param("startsWith") String startsWith,
        @Param("offset") int offset,
        @Param("limit") int limit);
}
