package com.elibrary.backend.author.dto;

import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AuthorDto {
    private Integer id;
    private String name;
}
