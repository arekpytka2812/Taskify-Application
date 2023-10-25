package com.pytka.taskifyapplication.auth.service;

import com.pytka.taskifyapplication.auth.model.*;

public interface AuthService {

    void generateVerificationCode(VerificationCodeRequest request);

    AuthResponse register(RegisterRequest request);

    void regenerateVerificationCode(VerificationCodeRequest request);

    AuthResponse login(AuthRequest request);

    AuthResponse changePassword(ChangePasswordRequest request);

    void remindPassword(RemindPasswordRequest request);

    AuthResponse setNewPasswordAfterPasswordRemind(NewPasswordRequest request);
}


