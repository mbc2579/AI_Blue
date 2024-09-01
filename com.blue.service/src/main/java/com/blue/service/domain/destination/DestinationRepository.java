package com.blue.service.domain.destination;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface DestinationRepository extends JpaRepository<Destination, Integer> {
    Optional <Destination> findAllByUserNameAndDeletedAtIsNull(String userName);
    Optional<Destination> findByDestIdAndDeletedAtIsNull(UUID destId);

}
