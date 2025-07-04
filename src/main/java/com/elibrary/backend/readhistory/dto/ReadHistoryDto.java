package com.elibrary.backend.readhistory.dto;

import com.elibrary.backend.book.dto.BookDto;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReadHistoryDto {
    private BookDto book;
    @JsonProperty(value = "last_read_ts")
    private LocalDateTime lastReadTs;
}
