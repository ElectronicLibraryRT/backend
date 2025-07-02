package com.elibrary.backend.booklocation.dto;

import com.elibrary.backend.extensions.dto.ExtensionDto;

import lombok.*;


@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BookLocationDto {
    private ExtensionDto extension;
    private String location;
}
