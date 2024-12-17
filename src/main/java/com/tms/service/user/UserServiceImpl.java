package com.tms.service.user;

import com.google.common.base.Strings;
import com.google.common.collect.Sets;
import com.tms.domain.model.user.Role;
import com.tms.domain.model.user.User;
import com.tms.domain.repository.user.RoleRepository;
import com.tms.domain.repository.user.UserRepository;
import com.tms.dto.user.LoginRequest;
import com.tms.dto.user.LoginResponse;
import com.tms.dto.user.RegistrationRequest;
import com.tms.dto.user.RegistrationResponse;
import com.tms.exception.RoleNotFoundException;
import com.tms.util.JwtTokenUtil;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Map;

@Service
public class UserServiceImpl implements UserService {

    private final JwtTokenUtil jwtTokenUtil;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final UserDetailsService userDetailsService;

    public UserServiceImpl(JwtTokenUtil jwtTokenUtil, AuthenticationManager authenticationManager, PasswordEncoder passwordEncoder, UserRepository userRepository, RoleRepository roleRepository, UserDetailsService userDetailsService) {
        this.jwtTokenUtil = jwtTokenUtil;
        this.authenticationManager = authenticationManager;
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.userDetailsService = userDetailsService;
    }

    public User loadUserByUsername(String username) {
        return userRepository.findByUsername(username).orElseThrow(
                () -> new UsernameNotFoundException(username)
        );
    }

    @Override
    public RegistrationResponse register(RegistrationRequest registrationRequest) throws RoleNotFoundException {
        if (userRepository.findByUsername(registrationRequest.getUsername()).isPresent()) {
            return new RegistrationResponse("Failed", "Username is already taken");
        }
        User user = new User();
        user.setFullName(registrationRequest.getFullName());
        Role role = getRoleByName("USER");
        user.setRole(Sets.newHashSet(role));
        user.setUsername(registrationRequest.getUsername());
        user.setPassword(passwordEncoder.encode(registrationRequest.getPassword()));
        user.setCreatedAt(LocalDate.now().toString());
        userRepository.save(user);
        return new RegistrationResponse("Success", "User successfully registered");
    }

    @Override
    public LoginResponse login(LoginRequest loginRequest) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    loginRequest.getUsername(), loginRequest.getPassword()
            ));
        } catch (Exception e) {
            return new LoginResponse("", "login failed");
        }
        UserDetails userDetails = userDetailsService.loadUserByUsername(loginRequest.getUsername());
        String token = jwtTokenUtil.generateToken(
                loginRequest.getUsername(),
                Map.of("role", userDetails.getAuthorities().toString())
                );
        return new LoginResponse(token, "You are logged in successfully");
    }

    @Override
    public String setRoleToAdmin(String username) throws RoleNotFoundException {
        try {
            if (Strings.isNullOrEmpty(username)) {
                return "username is required";
            }
            User user = loadUserByUsername(username);
            Role admin = getRoleByName("ADMIN");
            user.setRole(Sets.newHashSet(admin));
            userRepository.save(user);
            return "User with " + username + " now are administrator";
        } catch (UsernameNotFoundException e) {
            return "Username not found";
        }
    }

    private Role getRoleByName(String roleName) throws RoleNotFoundException {
        return roleRepository.findByName(roleName).orElseThrow(
                () -> new RoleNotFoundException("")
        );
    }
}
