package com.tms.dto.user;

import lombok.Data;
import lombok.NonNull;

import java.io.Serializable;

@Data
public class LoginRequest implements Serializable {

    @NonNull
    private String username;
    @NonNull
    private String password;
}
