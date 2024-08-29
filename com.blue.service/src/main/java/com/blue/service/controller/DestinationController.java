package com.blue.service.controller;

import com.blue.service.application.DestinationService;
import com.blue.service.application.dtos.destination.DestinationReqDto;
import com.blue.service.application.dtos.destination.DestinationResDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/destination")
public class DestinationController {

    private final DestinationService destinationService;

    @PostMapping
    public ResponseEntity<DestinationResDto> addDestination(@RequestBody DestinationReqDto requestDto, @RequestHeader(name="X-User_Name", required=false) String userName) {

        return ResponseEntity.ok(destinationService.addDestination(requestDto, userName));
    }



}
