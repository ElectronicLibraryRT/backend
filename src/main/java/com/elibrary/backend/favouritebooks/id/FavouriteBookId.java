package com.elibrary.backend.favouritebooks.id;

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
public class FavouriteBookId implements Serializable {
    @Column(name = "user_id")
    private Integer userId;

    @Column(name = "book_id")
    private Integer bookId;
}
