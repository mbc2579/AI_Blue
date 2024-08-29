package com.blue.service.application;

import com.blue.service.application.dtos.destination.DestinationReqDto;
import com.blue.service.application.dtos.destination.DestinationResDto;
import com.blue.service.domain.destination.Destination;
import com.blue.service.domain.destination.DestinationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DestinationService {

    private final DestinationRepository destinationRepository;

    public DestinationResDto addDestination(DestinationReqDto requestDto, String userName ) {
        Destination destination = requestDto.toDestination(userName);

        return DestinationResDto.from(destinationRepository.save(destination));
    }
}
