package com.elibrary.backend.booklocation.id;

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
public class BookLocationId implements Serializable {
    @Column(name = "book_id")
    private Integer bookId;

    @Column(name = "extension_id")
    private Integer extensionId;
}
