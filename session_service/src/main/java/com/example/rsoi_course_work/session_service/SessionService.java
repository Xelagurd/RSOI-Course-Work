package com.example.rsoi_course_work.session_service;

import com.example.rsoi_course_work.session_service.exception.ErrorResponse;
import com.example.rsoi_course_work.session_service.model.User;
import com.example.rsoi_course_work.session_service.model.UserRole;
import com.example.rsoi_course_work.session_service.security.JwtUtils;
import com.example.rsoi_course_work.session_service.security.RegistrationRequest;
import com.example.rsoi_course_work.session_service.security.SessionJwtResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class SessionService {
    private final UserRepository userRepository;
    private final JwtUtils jwtUtils;

    public SessionService(UserRepository userRepository, JwtUtils jwtUtils) {
        this.userRepository = userRepository;
        this.jwtUtils = jwtUtils;
    }

    public ResponseEntity<SessionJwtResponse> getUserAuthentication(String login, String password) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

        Optional<User> userOptional = userRepository.findByLogin(login);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            if (passwordEncoder.matches(password, user.getPassword())) {
                SessionJwtResponse sessionJwtResponse = new SessionJwtResponse();
                sessionJwtResponse.setJwt(jwtUtils.generateJwtToken(login));
                sessionJwtResponse.setRole(user.getRole());
                sessionJwtResponse.setUser_uid(user.getUser_uid());

                return new ResponseEntity<>(sessionJwtResponse, HttpStatus.OK);
            } else {
                throw new ErrorResponse("Not valid password for user");
            }
        } else {
            throw new ErrorResponse("Not found user for login");
        }
    }

    public ResponseEntity<SessionJwtResponse> userRegistration(RegistrationRequest registrationRequest) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

        User user = new User();
        user.setLogin(registrationRequest.getLogin());
        user.setPassword(passwordEncoder.encode(registrationRequest.getPassword()));
        user.setName(registrationRequest.getName());
        user.setSurname(registrationRequest.getSurname());
        user.setRole(UserRole.USER);
        UUID userUid = UUID.randomUUID();
        user.setUser_uid(userUid);
        userRepository.save(user);

        SessionJwtResponse sessionJwtResponse = new SessionJwtResponse();
        sessionJwtResponse.setJwt(jwtUtils.generateJwtToken(registrationRequest.getLogin()));
        sessionJwtResponse.setRole(UserRole.USER);
        sessionJwtResponse.setUser_uid(userUid);

        return new ResponseEntity<>(sessionJwtResponse, HttpStatus.CREATED);
    }

    public ResponseEntity<Boolean> validateJwtToken(String jwt) {
        jwt = jwt.substring(7);
        if (jwtUtils.validateJwtToken(jwt)) {
            return new ResponseEntity<>(Boolean.TRUE, HttpStatus.OK);
        }

        return new ResponseEntity<>(Boolean.FALSE, HttpStatus.OK);
    }

    public ResponseEntity<User> getUser(UUID userUid) {
        User user = userRepository.findByUser_uid(userUid).orElseThrow(() ->
                new ErrorResponse("Not found user for UID"));

        return new ResponseEntity<>(user, HttpStatus.OK);
    }
}