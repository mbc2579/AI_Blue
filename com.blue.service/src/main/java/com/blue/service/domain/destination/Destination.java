package com.blue.service.domain.destination;

import com.blue.service.domain.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;

import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "p_destinations")
public class Destination extends BaseEntity {

    @Id
    @Column(name="dest_id")
    private UUID destId;

    @Column(name="user_name")
    private String userName;

    @Column(name="address")
    private String address;

    @Column(name="request", length=500)
    private String request;

    public void updateAddress(String address, String request) {
        this.address = address;
        this.request = request;
    }


}
