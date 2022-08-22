package com.example.tanmeyah.employee;

import lombok.AllArgsConstructor;
import lombok.Getter;
import net.bytebuddy.asm.Advice;

@AllArgsConstructor
@Getter
public class LoginDTO {
    private String email;
    private String password;
}
