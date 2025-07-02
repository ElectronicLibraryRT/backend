package com.elibrary.backend.book.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;


@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BookDto {
    private Integer id;
    private String title;
    @JsonProperty("year_written")
    private Short yearWritten;
}
