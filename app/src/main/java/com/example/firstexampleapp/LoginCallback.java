package com.example.firstexampleapp;

public interface LoginCallback {
    void onLoginSuccess(ClienteContpaq cliente);
    void onLoginFailure(String errorMessage);
}
