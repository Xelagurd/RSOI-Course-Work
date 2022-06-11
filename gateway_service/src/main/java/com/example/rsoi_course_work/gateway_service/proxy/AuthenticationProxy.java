package com.example.rsoi_course_work.gateway_service.proxy;

import com.example.rsoi_course_work.gateway_service.security.SessionRequest;
import com.example.rsoi_course_work.gateway_service.security.SessionResponse;
import com.example.rsoi_course_work.gateway_service.security.TokenResponse;
import feign.Headers;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "okta-service", url = "https://dev-58783735.okta.com")
public interface AuthenticationProxy {
    @PostMapping("/api/v1/authn")
    public ResponseEntity<SessionResponse> verifyUserIdentity
            (@RequestBody SessionRequest sessionRequest);

    @GetMapping("/oauth2/default/v1/authorize")
    public ResponseEntity<String> requestAuthorizationCode
            (@RequestParam(name = "client_id") String clientId,
             @RequestParam(name = "response_type") String responseType,
             @RequestParam String scope,
             @RequestParam(name = "redirect_uri") String redirectUri,
             @RequestParam String state,
             @RequestParam String sessionToken);

    @PostMapping("/oauth2/default/v1/token")
    @Headers({"Content-Type: application/x-www-form-urlencoded", "Accept: application/json"})
    public ResponseEntity<TokenResponse> exchangeCodeForTokens
            (@RequestParam(name = "client_id") String clientId,
             @RequestParam(name = "client_secret") String clientSecret,
             @RequestParam(name = "grant_type") String grantType,
             @RequestParam(name = "redirect_uri") String redirectUri,
             @RequestParam String code);
}
