package com.elibrary.backend.readhistory;


import com.elibrary.backend.book.Book;
import com.elibrary.backend.readhistory.id.ReadHistoryId;
import com.elibrary.backend.user.User;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "read_history")
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReadHistory {
    @EmbeddedId
    private ReadHistoryId id;

    @Column(name = "last_read_ts", nullable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT TIMESTAMP")
    private LocalDateTime lastReadTs;

    @ManyToOne
    @MapsId("userId")
    @JoinColumn(name = "user_id", insertable = false, updatable = false)
    private User user;

    @ManyToOne
    @MapsId("bookId")
    @JoinColumn(name = "book_id", insertable = false, updatable = false)
    private Book book;
}
