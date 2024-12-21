package com.tms;

import com.google.common.collect.Sets;
import com.tms.domain.model.user.Role;
import com.tms.domain.model.user.User;
import com.tms.domain.repository.user.RoleRepository;
import com.tms.domain.repository.user.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.text.SimpleDateFormat;
import java.util.Date;

@SpringBootApplication
@EnableScheduling
public class Application implements CommandLineRunner {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    public Application(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        if (!userRepository.existsUserByUsername("admin") && !roleRepository.existsByName("ADMIN")) {
            User admin = new User();
            admin.setFullName("Admin");
            admin.setUsername("admin");
            admin.setPassword(passwordEncoder.encode("Sp@123456"));
            Role adminRole = new Role();
            adminRole.setName("ADMIN");
            adminRole.setDescription("Administrator");
            roleRepository.save(adminRole);
            admin.setRole(Sets.newHashSet(adminRole));
            admin.setCreatedAt(new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(new Date()));
            userRepository.save(admin);
        }
    }
}
