package com.example.rsoi_course_work.session_service;

import com.example.rsoi_course_work.session_service.model.User;
import com.example.rsoi_course_work.session_service.security.RegistrationRequest;
import com.example.rsoi_course_work.session_service.security.SessionJwtResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1")
public class SessionController {
    private final SessionService sessionService;

    public SessionController(SessionService sessionService) {
        this.sessionService = sessionService;
    }

    @GetMapping("/auth")
    public ResponseEntity<SessionJwtResponse> userAuthorization(@RequestParam("login") String login, @RequestParam("password") String password) {
        return sessionService.userAuthorization(login, password);
    }

    @PostMapping("/registration")
    public ResponseEntity<SessionJwtResponse> userRegistration(@RequestBody RegistrationRequest registrationRequest) {
        return sessionService.userRegistration(registrationRequest);
    }

    @GetMapping("/user")
    public ResponseEntity<User> getCurrentUser(@RequestHeader("Authorization") String jwt) {
        return sessionService.getCurrentUser(jwt);
    }

    @GetMapping("/verify")
    public ResponseEntity<Boolean> verifyJwtToken(@RequestHeader("Authorization") String jwt) {
        return sessionService.verifyJwtToken(jwt);
    }
}
