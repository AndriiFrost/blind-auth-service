package com.blind.auth.service.impl;

import com.blind.auth.dto.request.AuthenticationRequest;
import com.blind.auth.dto.request.RegisterRequest;
import com.blind.auth.dto.response.AuthenticationResponse;
import com.blind.auth.entity.BlindUser;
import com.blind.auth.entity.enumeration.Role;
import com.blind.auth.repository.BlindUserRepository;
import com.blind.auth.security.JwtService;
import com.blind.auth.service.AuthService;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private static final String USER_WITH_EMAIL_EXIST_MESSAGE = "User with email '%s' already exist.";

    private final BlindUserRepository blindUserRepository;

    private final BCryptPasswordEncoder passwordEncoder;

    private final JwtService jwtService;

    private final AuthenticationManager authenticationManager;

    @Override
    @Transactional
    public AuthenticationResponse register(RegisterRequest request) {
        checkAndThrowExceptionIfUserByEmailExist(request.getEmail());
        BlindUser blindUser = BlindUser.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.USER)
                .build();
        blindUserRepository.save(blindUser);
        String jwtToken = jwtService.generateToken(blindUser);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }

    @Override
    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(),
                request.getPassword()));
        BlindUser blindUser = blindUserRepository.findByEmail(request.getEmail())
                .orElseThrow();
        String jwtToken = jwtService.generateToken(blindUser);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }

    private void checkAndThrowExceptionIfUserByEmailExist(String email) {
        Optional<BlindUser> blindUserRepositoryByEmail = blindUserRepository.findByEmail(email);
        if (blindUserRepositoryByEmail.isPresent()) {
            log.error(String.format(USER_WITH_EMAIL_EXIST_MESSAGE, email));
            throw new IllegalArgumentException(String.format(USER_WITH_EMAIL_EXIST_MESSAGE, email));
        }
    }
}
