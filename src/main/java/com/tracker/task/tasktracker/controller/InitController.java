package com.tracker.task.tasktracker.controller;

import com.tracker.task.tasktracker.dto.AutentificationReqquestDto;
import com.tracker.task.tasktracker.entity.User;
import com.tracker.task.tasktracker.repository.UserRepository;
import com.tracker.task.tasktracker.security.jwt.JwtTokenProvider;
import com.tracker.task.tasktracker.service.UserService;
import com.tracker.task.tasktracker.util.RandomGeneratorDbService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/api")
public class InitController {

    private final UserService userService;
    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider tokenProvider;
    private final RandomGeneratorDbService randomGenerator;
    private final BCryptPasswordEncoder encoder;

    public InitController(UserService userService,
                          UserRepository userRepository,
                          AuthenticationManager authenticationManager,
                          JwtTokenProvider tokenProvider,
                          RandomGeneratorDbService randomGenerator,
                          BCryptPasswordEncoder encoder) {
        this.userService = userService;
        this.userRepository = userRepository;
        this.authenticationManager = authenticationManager;
        this.tokenProvider = tokenProvider;
        this.randomGenerator = randomGenerator;
        this.encoder = encoder;
    }

    @PostMapping(value = "/login")
    public ResponseEntity<String> login(@RequestBody AutentificationReqquestDto requestDto) {
        try {
            String email = requestDto.getLogin();
            User user = userService.findByEmail(email).orElseThrow(NoSuchElementException::new);
            user.setRoles(new ArrayList<>());
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email,
                    requestDto.getPassword()));
            String token = tokenProvider.createToken(email, user.getRoles());
            return ResponseEntity.ok("Bearer " + token);
        } catch (AuthenticationException e) {
            throw new BadCredentialsException("Invalid username or password");
        }
    }

    @PostConstruct()
    @Transactional
    public void generateDb() {
        User defaultUser = new User();
        defaultUser.setUsername("misha");
        defaultUser.setPassword(encoder.encode("misha"));
        defaultUser.setLastName("toje misha");
        defaultUser.setEmail("misha");
        userRepository.save(defaultUser);
        randomGenerator.generateDb();
    }
}
