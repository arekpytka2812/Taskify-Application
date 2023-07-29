package com.pytka.taskifyapplication.auth.service;

import com.pytka.taskifyapplication.auth.model.AuthRequest;
import com.pytka.taskifyapplication.auth.model.AuthResponse;
import com.pytka.taskifyapplication.auth.model.ChangePasswordRequest;
import com.pytka.taskifyapplication.auth.model.RegisterRequest;

public interface AuthService {

    AuthResponse register(RegisterRequest request);

    AuthResponse login(AuthRequest request);

    AuthResponse changePassword(ChangePasswordRequest request);
}


