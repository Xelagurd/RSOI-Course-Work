package com.example.rsoi_course_work.front.proxy;

import com.example.rsoi_course_work.front.model.user.security.RegistrationRequest;
import com.example.rsoi_course_work.front.model.user.security.SessionJwtResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "session-service", url = "http://localhost:8010/api/v1")
public interface SessionServiceProxy {
    @GetMapping("/auth")
    public ResponseEntity<SessionJwtResponse> userAuthorization(@RequestParam("login") String login, @RequestParam("password") String password);

    @PostMapping("/registration")
    public ResponseEntity<SessionJwtResponse> userRegistration(@RequestBody RegistrationRequest registrationRequest);
}
