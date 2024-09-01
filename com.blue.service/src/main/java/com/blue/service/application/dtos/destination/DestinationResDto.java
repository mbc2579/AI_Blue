package com.blue.service.application.dtos.destination;

import com.blue.service.domain.destination.Destination;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DestinationResDto {
    private UUID destId;
    private String userName;
    private String address;
    private String request;

    public static DestinationResDto from(Destination dest){
        return DestinationResDto.builder()
                .destId(dest.getDestId())
                .userName(dest.getUserName())
                .address(dest.getAddress())
                .request(dest.getRequest())
                .build();
    }
}
