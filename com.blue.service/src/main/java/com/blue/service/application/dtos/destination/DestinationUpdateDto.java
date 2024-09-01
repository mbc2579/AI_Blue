package com.blue.service.application.dtos.destination;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class DestinationUpdateDto {
    private String address;
    private String request;
}
