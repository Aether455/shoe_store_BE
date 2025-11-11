package com.nguyenkhang.mobile_store.configuration;

import com.nguyenkhang.mobile_store.entity.User;
import com.nguyenkhang.mobile_store.enums.Role;
import com.nguyenkhang.mobile_store.repository.RoleRepository;
import com.nguyenkhang.mobile_store.repository.UserRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.HashSet;

@RequiredArgsConstructor
@Configuration
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class ApplicationInitConfig {
    PasswordEncoder passwordEncoder;

    @Bean
    ApplicationRunner applicationRunner(UserRepository userRepository, RoleRepository roleRepository){
        return args ->{

            if (userRepository.findByUsername("admin").isEmpty()){
                var roles = new HashSet<String>();
                roles.add(Role.ADMIN.name());

                User user = User.builder()
                        .username("admin")
                        .email("admin@gmail.com")
                        .password(passwordEncoder.encode("admin"))
                        .roles(new HashSet<>(roleRepository.findAllById(roles)))
                        .build();

                userRepository.save(user);


                log.warn("Admin user has been created with default password: admin and email: admin@gmail.com, please change it!");
            }
        };
    }
}
