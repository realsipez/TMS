package com.tms.dto.user;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

@Data
@AllArgsConstructor
public class RegistrationResponse implements Serializable {
    private String status;
    private String message;
}
