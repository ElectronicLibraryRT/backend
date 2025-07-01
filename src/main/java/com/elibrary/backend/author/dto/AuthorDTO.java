package com.elibrary.backend.author.dto;

import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AuthorDTO {
    private Integer id;
    private String name;
}
