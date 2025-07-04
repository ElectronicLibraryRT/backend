package com.elibrary.backend.booklocation;

import com.elibrary.backend.book.Book;
import com.elibrary.backend.booklocation.id.BookLocationId;
import com.elibrary.backend.extensions.Extension;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "books_locations")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BookLocation {
    @EmbeddedId
    private BookLocationId id;

    @Column(nullable = false, length = 256)
    private String location;

    @ManyToOne
    @MapsId("bookId")
    @JoinColumn(name = "book_id", insertable = false, updatable = false)
    private Book book;

    @ManyToOne
    @MapsId("extensionId")
    @JoinColumn(name = "extension_id", insertable = false, updatable = false)
    private Extension extension;
}
