package com.hg.dashboard.dto;


import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LoginData {
    @NotBlank
    @Email(flags = { Pattern.Flag.CASE_INSENSITIVE })
    private String email;

    @NotBlank
    @Size(min = 8)
    private String password;

    public String getEmail() {
        return email.toLowerCase();
    }
}

