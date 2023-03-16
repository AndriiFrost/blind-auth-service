package com.blind.auth.service;


import com.blind.auth.dto.request.AuthenticationRequest;
import com.blind.auth.dto.request.RegisterRequest;
import com.blind.auth.dto.response.AuthenticationResponse;

public interface AuthService {
    void register(RegisterRequest request);

    AuthenticationResponse authenticate(AuthenticationRequest request);
}
