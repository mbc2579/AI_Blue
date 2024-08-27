package com.blue.auth.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Table(name="p_users")
@AllArgsConstructor
@NoArgsConstructor
@Builder(access = AccessLevel.PRIVATE)
public class User {
    @Id
    @Column(name="user_name", nullable = false)
    private String userName;

    @Column
    private String password;

    @Column
    private String phoneNumber;

    @Column
    @Enumerated(value=EnumType.STRING)
    private UserRoleEnum role;

}
