package com.tms.dto.user;

import lombok.Data;
import lombok.NonNull;

import java.io.Serializable;

@Data
public class RegistrationRequest implements Serializable {

    @NonNull
    private String fullName;

    @NonNull
    private String username;

    @NonNull
    private String password;

    @NonNull
    private String reEnteredPassword;
}
