package com.elibrary.backend.extensions.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;


@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ExtensionDto {
    private Integer id;
    private String name;
}
