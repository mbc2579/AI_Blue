package com.blue.auth.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@Table(name="p_users")
@AllArgsConstructor
@NoArgsConstructor
@Builder(access = AccessLevel.PRIVATE)
public class User extends BaseEntity {
    @Id
    @Column(name="user_name", nullable = false)
    private String userName;

    @Column(name="phone_number")
    private String phoneNumber;

    @Column(name="password")
    private String password;

    @Column(name="role")
    @Enumerated(value=EnumType.STRING)
    private UserRoleEnum role;

    public void userUpdate(String phoneNumber, String password) {
        this.phoneNumber = phoneNumber;
        this.password = password;
    }

}
