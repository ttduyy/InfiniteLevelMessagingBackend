package com.example.infinite_level_messaging_system.Services;


import com.example.infinite_level_messaging_system.Entity.User;
import com.example.infinite_level_messaging_system.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServicesImp implements UserServices{

    private final UserRepository userRepository;

    @Override
    public User getUser(String username){
        return userRepository.findByEmail(username)
                .or(() -> userRepository.findByUsername(username))
                .orElseThrow(() -> new UsernameNotFoundException("Username is not found!"));
    }
}
