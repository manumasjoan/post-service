package com.example.jibberjabber.service;

import com.example.jibberjabber.entity.User;

import java.util.UUID;

public interface UserService {

    User getCurrentUser();

    User getUserById(UUID userId);
}
