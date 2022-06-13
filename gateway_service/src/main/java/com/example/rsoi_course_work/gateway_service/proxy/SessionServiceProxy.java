package com.example.rsoi_course_work.gateway_service.proxy;

import com.example.rsoi_course_work.gateway_service.model.user.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "session-service", url = "http://localhost:8010/api/v1")
public interface SessionServiceProxy {
    @GetMapping("/user")
    public ResponseEntity<User> getCurrentUser(@RequestHeader("Authorization") String jwt);

    @GetMapping("/verify")
    public ResponseEntity<Boolean> verifyJwtToken(@RequestHeader("Authorization") String jwt);
}
