package com.example.rsoi_course_work.session_service;

import com.example.rsoi_course_work.session_service.model.User;
import com.example.rsoi_course_work.session_service.security.RegistrationRequest;
import com.example.rsoi_course_work.session_service.security.SessionJwtResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("api/v1")
public class SessionController {
    private final SessionService sessionService;

    public SessionController(SessionService sessionService) {
        this.sessionService = sessionService;
    }

    @GetMapping("/authenticate")
    public ResponseEntity<SessionJwtResponse> getUserAuthentication(@RequestParam("login") String login, @RequestParam("password") String password) {
        return sessionService.getUserAuthentication(login, password);
    }

    @PostMapping("/registration")
    public ResponseEntity<SessionJwtResponse> userRegistration(@RequestBody RegistrationRequest registrationRequest) {
        return sessionService.userRegistration(registrationRequest);
    }

    @GetMapping("/validate-token")
    public ResponseEntity<Boolean> validateJwtToken(@RequestHeader("Authorization") String jwt) {
        return sessionService.validateJwtToken(jwt);
    }

    @GetMapping("/user/{userUid}")
    public ResponseEntity<User> getUser(@PathVariable("userUid") UUID userUid) {
        return sessionService.getUser(userUid);
    }
}
