package com.example.rsoi_course_work.gateway_service.security;

public class TokenResponse {
    private String access_token;
    private String token_type;
    private Integer expires_in;
    private String scope;
    private String refresh_token;
    private String id_token;

    public TokenResponse() {
    }

    public TokenResponse(String access_token, String token_type, Integer expires_in, String scope, String refresh_token, String id_token) {
        this.access_token = access_token;
        this.token_type = token_type;
        this.expires_in = expires_in;
        this.scope = scope;
        this.refresh_token = refresh_token;
        this.id_token = id_token;
    }

    public String getAccess_token() {
        return access_token;
    }

    public String getToken_type() {
        return token_type;
    }

    public Integer getExpires_in() {
        return expires_in;
    }

    public String getScope() {
        return scope;
    }

    public String getRefresh_token() {
        return refresh_token;
    }

    public String getId_token() {
        return id_token;
    }
}
