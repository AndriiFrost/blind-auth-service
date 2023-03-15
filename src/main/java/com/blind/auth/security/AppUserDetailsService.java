package com.blind.auth.security;

import com.blind.auth.repository.BlindUserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class AppUserDetailsService implements UserDetailsService {

    private static final String USER_NOT_FOUND_MESSAGE = "User with email '%s' not found.";

    private final BlindUserRepository blindUserRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return blindUserRepository.findByEmail(email).orElseThrow(() -> {
            log.error(String.format(USER_NOT_FOUND_MESSAGE, email));
            throw new UsernameNotFoundException(String.format(USER_NOT_FOUND_MESSAGE, email));
        });
    }
}
