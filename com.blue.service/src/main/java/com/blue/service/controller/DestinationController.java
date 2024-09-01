package com.blue.service.controller;

import com.blue.service.application.DestinationService;
import com.blue.service.application.dtos.destination.DestinationReqDto;
import com.blue.service.application.dtos.destination.DestinationResDto;
import com.blue.service.domain.destination.Destination;
import com.blue.service.domain.destination.DestinationRepository;
import feign.Response;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/destination")
public class DestinationController {

    private final DestinationService destinationService;

    @PostMapping
    public ResponseEntity<DestinationResDto> addDestination(@RequestBody DestinationReqDto requestDto, @RequestHeader(name="X-User-Name", required=false) String userName) {

        return ResponseEntity.ok(destinationService.addDestination(requestDto, userName));
    }

    @GetMapping
    public ResponseEntity<List<DestinationResDto>> searchDestinations(@RequestHeader(name="X-User-Name", required=false) String userName) {
        return ResponseEntity.ok(destinationService.searchDestination(userName));
    }

    @GetMapping("/{destId}")
    public ResponseEntity<DestinationResDto> getDestination(@PathVariable("destId") UUID destId, @RequestHeader(name="X-User-Name", required = false) String userName) {
        return ResponseEntity.ok(destinationService.getDestination(destId, userName));
    }

    @PutMapping("/{destId}")
    public ResponseEntity<DestinationResDto> editDestination(
            @RequestBody DestinationReqDto requestDto,
            @PathVariable(name="destId") UUID destId) {

        return ResponseEntity.ok(destinationService.editDestination(requestDto, destId));
    }

    @DeleteMapping("/{destId}")
    public ResponseEntity<DestinationResDto> deleteDestination(@PathVariable(name="destId") UUID destId, @RequestHeader(name="X-User-Name", required = false) String userName){
        return ResponseEntity.ok(destinationService.deleteDestination(destId, userName));
    }



}
