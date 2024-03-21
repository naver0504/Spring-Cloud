package com.example.apigatewayservice.filter;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class AuthorizationFilterTest {


    private final String key = "mySecretKeyaffeaff924fk934f3k94f23fr0owrelg0erfl92249fk9k9fewqf9f2m34f9234mf2934f234mdi392fm4i9f2i943f924f92i4gm924ik3kf923i4rk2i934dmi";

    @Test
    void isTokenValid() {

        SecretKey secretKey = Keys.hmacShaKeyFor(key.getBytes(StandardCharsets.UTF_8));
        String jwtToken = Jwts.builder()
                .subject("dfd5435b-15c5-4a84-82f1-521a03557f5b")
                .setExpiration(new Date(System.currentTimeMillis() + 86400))
                .signWith(secretKey)
                .compact();

        System.out.println(jwtToken);

        String result = Jwts.parser()
                .verifyWith(secretKey)
                .build()
                .parseClaimsJws("eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJkZmQ1NDM1Yi0xNWM1LTRhODQtODJmMS01MjFhMDM1NTdmNWIiLCJleHAiOjE3MTExMjg1NDd9.z6u7srX2E2z11Obi8msduuF-zF2TrDXh3nCPOZzFOkm4pkbE2hj-dqeFzTmQeEhUSWJnkSuyCEDVpSNZky0feA")
                .getBody()
                .getSubject();

        Assertions.assertThat(result).isEqualTo("dfd5435b-15c5-4a84-82f1-521a03557f5b");
//        Jwts.parser()
//                .verifyWith(secretKey)
//                .build()
//                .parse(jwtToken);
//
//        Assertions.assertThat(result).isEqualTo("100");

    }

}