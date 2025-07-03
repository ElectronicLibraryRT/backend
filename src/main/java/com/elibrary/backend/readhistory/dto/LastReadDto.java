package com.elibrary.backend.readhistory.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.time.OffsetDateTime;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LastReadDto {
    @JsonProperty(value = "last_read_ts")
    private OffsetDateTime lastReadTs;
}