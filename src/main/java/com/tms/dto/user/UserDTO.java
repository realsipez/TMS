package com.tms.dto.user;

import lombok.Data;
import lombok.NonNull;

import java.io.Serializable;

@Data
public class UserDTO implements Serializable {

    @NonNull
    private String username;

    @NonNull
    private String password;

    @NonNull
    private String roleName;

    @NonNull
    private String fullName;
}
