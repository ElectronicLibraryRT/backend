package com.elibrary.backend.favouritebooks.dto;

import com.elibrary.backend.book.dto.BookDto;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.time.OffsetDateTime;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FavouriteBookDto {
    private BookDto book;
    @JsonProperty(value = "added_ts")
    private OffsetDateTime addedTs;
}
