package com.elibrary.backend.favouritebooks.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AddedDto {
    @JsonProperty(value = "added_ts")
    private LocalDateTime addedTs;
}
