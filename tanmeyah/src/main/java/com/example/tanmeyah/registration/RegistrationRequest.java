package com.example.tanmeyah.registration;

import com.example.tanmeyah.security.Role;
import lombok.*;


@Getter
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class RegistrationRequest {
    private final String firstName;
    private final String lastName;
    private final String password;
    private final String email;
    private final Role role;
}
