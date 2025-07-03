package com.elibrary.backend.user.dto;

import lombok.*;


@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDto {
    private Integer id;
    private String name;
}
