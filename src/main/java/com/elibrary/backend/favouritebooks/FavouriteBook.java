package com.elibrary.backend.favouritebooks;

import com.elibrary.backend.book.Book;
import com.elibrary.backend.favouritebooks.id.FavouriteBookId;
import com.elibrary.backend.user.User;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "favourite_books")
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FavouriteBook {
    @EmbeddedId
    private FavouriteBookId id;

    @Column(name = "added_ts", nullable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT TIMESTAMP")
    private LocalDateTime addedTs;

    @ManyToOne
    @MapsId("userId")
    @JoinColumn(name = "user_id", insertable = false, updatable = false)
    private User user;

    @ManyToOne
    @MapsId("bookId")
    @JoinColumn(name = "book_id", insertable = false, updatable = false)
    private Book book;
}
