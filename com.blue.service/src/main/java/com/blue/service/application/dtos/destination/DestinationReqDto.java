package com.blue.service.application.dtos.destination;

import com.blue.service.domain.destination.Destination;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class DestinationReqDto {
    private UUID destId;
    private String address;
    private String request;

    public Destination toDestination(String userName){
        return Destination.builder()
                .destId(UUID.randomUUID())
                .userName(userName)
                .address(this.address)
                .request(this.request)
                .build();
    }
}
