package com.imine.farouk.dto;

import lombok.*;

import java.math.BigDecimal;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class UserDto {

    private String firstName;
    private String lastName;
    private String otherName;
    private String gender;
    private String address;
    private String stateOfOrigin;
    private String email;
    private String phoneNumber;
    private String alternativePhoneNumber;
}
