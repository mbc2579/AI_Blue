package com.blue.service.application.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ReviewReqDto {
    private String comment;
    private Integer star;
}
