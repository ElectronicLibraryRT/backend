package com.elibrary.backend.book;

import com.elibrary.backend.author.Author;
import com.elibrary.backend.booklocation.BookLocation;
import com.elibrary.backend.genre.Genre;
import com.elibrary.backend.favouritebooks.FavouriteBook;
import com.elibrary.backend.readhistory.ReadHistory;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(
    name = "books",
    uniqueConstraints = @UniqueConstraint(
        name = "books_unique",
        columnNames = {"title", "year_written"}
    )
)
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, length = 128)
    private String title;

    @Column(name = "year_written")
    private Short yearWritten;

    @ManyToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    @JoinTable(
        name = "books_authors",
        joinColumns = @JoinColumn(name = "book_id"),
        inverseJoinColumns = @JoinColumn(name = "author_id")
    )
    private Set<Author> authors = new HashSet<>();

    @ManyToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    @JoinTable(
        name = "books_genres",
        joinColumns = @JoinColumn(name = "book_id"),
        inverseJoinColumns = @JoinColumn(name = "genre_id")
    )
    private Set<Genre> genres = new HashSet<>();

    @OneToMany(mappedBy = "book", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<BookLocation> booksLocations = new HashSet<>();
}
