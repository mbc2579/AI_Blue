package com.blue.service.application;

import com.blue.service.application.dtos.destination.DestinationReqDto;
import com.blue.service.application.dtos.destination.DestinationResDto;
import com.blue.service.domain.destination.Destination;
import com.blue.service.domain.destination.DestinationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DestinationService {

    private final DestinationRepository destinationRepository;

    public DestinationResDto addDestination(DestinationReqDto requestDto, String userName ) {
        Destination destination = requestDto.toDestination(userName);

        return DestinationResDto.from(destinationRepository.save(destination));
    }

    public List<DestinationResDto>  searchDestination(String userName) {
        return destinationRepository.findAllByUserNameAndDeletedAtIsNull(userName)
                .map(destination -> DestinationResDto.builder().destId(destination.getDestId()).userName(userName).address(destination.getAddress()).request(destination.getRequest()).build())
                .map(Collections::singletonList)
                .orElseThrow( ()-> new IllegalArgumentException("배송지가 존재하지 않습니다."));
    }

    public DestinationResDto getDestination(UUID destId, String userName) {
        Destination destination = destinationRepository.findByDestIdAndDeletedAtIsNull(destId).orElseThrow(
                ()-> new IllegalArgumentException("조회하려는 배송지가 존재하지 않습니다.")
        );

        return DestinationResDto.from(destination);
    }

    @Transactional
    public DestinationResDto editDestination(DestinationReqDto requestDto, UUID destId) {

        Destination destination = destinationRepository.findByDestIdAndDeletedAtIsNull(destId).orElseThrow(
                ()-> new IllegalArgumentException("수정하려는 배송지 주소가 없습니다.")
        );

        destination.updateAddress(requestDto.getAddress(), requestDto.getRequest());
        return DestinationResDto.from(destinationRepository.save(destination));
    }

    @Transactional
    public DestinationResDto deleteDestination(UUID destId , String userName ) {

        Destination destination = destinationRepository.findByDestIdAndDeletedAtIsNull(destId).orElseThrow(
                ()-> new IllegalArgumentException("삭제하려는 배송지가 존재하지 않습니다.")
        );

        destination.setDeleted(LocalDateTime.now(), userName);
        return DestinationResDto.from(destinationRepository.save(destination));
    }
}
