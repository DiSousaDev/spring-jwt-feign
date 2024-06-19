package br.dev.diego.springjwtfeign.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class JwtTokenService {

    @Value("${security.login}")
    private String login;

    @Value("${security.password}")
    private String password;

    @Value("${security.access-token-uri}")
    private String accessTokenUri;

    private String token;
    private Date expiration;

    public String getToken() {
        if (token == null || isTokenExpired()) {
            obtainNewToken();
        }
        return token;
    }

    private void obtainNewToken() {
        RestTemplate restTemplate = new RestTemplate();

        Map<String, String> body = new HashMap<>();
        body.put("login", login);
        body.put("password", password);

        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");

        HttpEntity<Map<String, String>> request = new HttpEntity<>(body, headers);

        ResponseEntity<Map> response = restTemplate.exchange(accessTokenUri, HttpMethod.POST, request, Map.class);
        Map<String, Object> responseBody = response.getBody();

        if (responseBody != null) {
            token = (String) responseBody.get("token");
            expiration = extractExpiration(token);
        }
    }

    private Date extractExpiration(String token) {
        String[] parts = token.split("\\.");
        if (parts.length < 2) {
            throw new IllegalArgumentException("Invalid JWT token");
        }
        String payload = new String(Base64.getDecoder().decode(parts[1]));
        return getExpirationFromPayload(payload);
    }

    private Date getExpirationFromPayload(String payload) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            Map<String, Object> claims = objectMapper.readValue(payload, HashMap.class);
            Integer exp = (Integer) claims.get("exp");
            if (exp == null) {
                throw new IllegalArgumentException("Token does not contain expiration claim");
            }
            return new Date(exp * 1000L); // Convert to milliseconds
        } catch (IOException e) {
            throw new RuntimeException("Failed to parse JWT payload", e);
        }
    }

    private boolean isTokenExpired() {
        return expiration == null || expiration.before(new Date());
    }
}