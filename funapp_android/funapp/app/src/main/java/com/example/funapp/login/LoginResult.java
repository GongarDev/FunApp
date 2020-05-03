package com.example.funapp.login;

import androidx.annotation.Nullable;

import java.net.Socket;

/**
 * Authentication result : success (user details) or error message.
 */
class LoginResult {
    @Nullable
    private LoggedInUserView success;
    @Nullable
    private Integer error;
    @Nullable
    private Socket socket;

    LoginResult(@Nullable Integer error) {
        this.error = error;
    }

    LoginResult(@Nullable LoggedInUserView success) {
        this.success = success;

    }

    LoginResult(@Nullable LoggedInUserView success, Socket socket) {
        this.success = success;
        this.socket = socket;
    }

    @Nullable
    LoggedInUserView getSuccess() { return success; }

    @Nullable
    Integer getError() {
        return error;
    }

    @Nullable
    Socket getSocket() { return socket; }
}
