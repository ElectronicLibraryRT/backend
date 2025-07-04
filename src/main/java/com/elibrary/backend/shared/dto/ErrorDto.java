package com.elibrary.backend.shared.dto;

import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ErrorDto {
    private String code;
    private String message;
    private Object details;
}