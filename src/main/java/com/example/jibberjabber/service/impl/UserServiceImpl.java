package com.example.jibberjabber.service.impl;

import com.example.jibberjabber.entity.User;
import com.example.jibberjabber.repository.UserRepository;
import com.example.jibberjabber.service.UserService;
import org.keycloak.KeycloakPrincipal;
import org.keycloak.KeycloakSecurityContext;
import org.keycloak.representations.AccessToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service

public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User getCurrentUser() {
        KeycloakPrincipal principal = (KeycloakPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        KeycloakSecurityContext session = principal.getKeycloakSecurityContext();
        AccessToken accessToken = session.getToken();

        User user = userRepository.findByUsername(accessToken.getPreferredUsername());

        if (user == null) {
            user = User.builder()
                    .username(accessToken.getPreferredUsername())
                    .name(accessToken.getName())
                    .build();
            userRepository.save(user);
        }

        return user;
    }

    @Override
    public User getUserById(UUID userId) {
        return userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
    }
}
