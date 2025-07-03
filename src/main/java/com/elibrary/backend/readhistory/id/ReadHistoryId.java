package com.elibrary.backend.readhistory.id;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Embeddable
public class ReadHistoryId implements Serializable {
    @Column(name = "user_id")
    private Integer userId;

    @Column(name = "book_id")
    private Integer bookId;
}
