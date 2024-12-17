package com.tms.service.user;

import com.tms.domain.model.user.User;
import com.tms.dto.user.*;
import com.tms.exception.RoleNotFoundException;

public interface UserService {

    User loadUserByUsername(String username);

    RegistrationResponse register(RegistrationRequest registrationRequest) throws RoleNotFoundException;

    LoginResponse login(LoginRequest loginRequest);

    String setRoleToAdmin(String username) throws RoleNotFoundException;
}
