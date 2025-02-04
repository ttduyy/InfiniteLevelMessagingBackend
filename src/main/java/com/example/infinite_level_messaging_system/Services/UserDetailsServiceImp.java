package com.example.infinite_level_messaging_system.Services;

import com.example.infinite_level_messaging_system.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImp implements UserDetailsService {
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        if (userRepository.findByUsername(username).isPresent()){
            return userRepository.findByUsername(username).get();
        }
        else if (userRepository.findByEmail(username).isPresent()){
            return userRepository.findByEmail(username).get();
        }
        else {
            throw new UsernameNotFoundException(username);
        }
    }
}
