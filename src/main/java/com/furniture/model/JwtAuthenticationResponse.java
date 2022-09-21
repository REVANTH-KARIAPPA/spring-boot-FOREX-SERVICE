package com.furniture.model;
import lombok.Value;

@Value
public class JwtAuthenticationResponse {
    private String accessToken;
    private UserInfo user;
}