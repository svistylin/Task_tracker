package com.tracker.task.tasktracker.security;


import com.tracker.task.tasktracker.entity.User;
import com.tracker.task.tasktracker.repository.UserRepository;
import com.tracker.task.tasktracker.security.jwt.JwtUser;
import com.tracker.task.tasktracker.security.jwt.JwtUserFactory;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class JwtUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    public JwtUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmailAndDeletedFalse(email)
                .orElseThrow(() -> new UsernameNotFoundException("User with email: " + email + " not found"));
        JwtUser jwtUser = JwtUserFactory.create(user);
        return jwtUser;
    }
}
