package com.app.User_service.Service;

import com.app.User_service.Repository.UserRepository;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailService implements UserDetailsService {

    private final UserRepository userRepository;

    // ✅ Correct constructor (Ensure it matches class name exactly)

    public CustomUserDetailService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username)
                .map(user -> User.builder()
                        .username(user.getUsername())
                        .password(user.getPassword()) // Ensure password is hashed
                        .roles(String.valueOf(user.getRole())) // Assuming `role` exists in your entity
                        .build())
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));
    }}
