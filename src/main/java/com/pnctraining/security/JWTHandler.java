package com.pnctraining.security;

import io.fusionauth.jwt.Signer;
import io.fusionauth.jwt.Verifier;
import io.fusionauth.jwt.domain.JWT;
import io.fusionauth.jwt.hmac.HMACSigner;
import io.fusionauth.jwt.hmac.HMACVerifier;
import org.springframework.stereotype.Component;

import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Set;

@Component
public class JWTHandler {
    private final String ISSUER = "PNC";
    private final String AUDIENCE = "customers";

    private Set<String> hashSet;

    private String userId;
    String secretKey = "RandomSecretkey";

    public JWTHandler() { hashSet = new HashSet<>(); }

    public JWTHandler(String userId) {
        this.userId = userId;
    }

    public String createToken(String userId) {
        Signer signer = HMACSigner.newSHA256Signer(secretKey);
        JWT jwt = new JWT().setIssuer(ISSUER)
                .setIssuedAt(ZonedDateTime.now(ZoneOffset.UTC))
                .setSubject("subjecttest")
                .setAudience(AUDIENCE)
                .setExpiration(ZonedDateTime.now(ZoneOffset.UTC).plusMinutes(15))
                .addClaim("userId", userId);
        String encodedJWT = "abc" + JWT.getEncoder().encode(jwt, signer);
        hashSet.add(encodedJWT);
        return encodedJWT;
    }

    public boolean validate(String token){
        if(!hashSet.contains(token)){
            return false;
        }
        Verifier verifier = HMACVerifier.newVerifier(secretKey);
        JWT jwt = JWT.getDecoder().decode(token.substring(3), verifier);
        if(jwt.isExpired()){
            return false;
        }
        if(jwt.subject.equals("subjecttest")){
            return true;
        }
        return false;
    }

    public boolean validate(String token, String userId){
        if(!hashSet.contains(token)){
            return false;
        }
        Verifier verifier = HMACVerifier.newVerifier(secretKey);
        JWT jwt = JWT.getDecoder().decode(token.substring(3), verifier);
        if(jwt.isExpired()){
            return false;
        }
        if(jwt.getString("userId").equals(userId)){
            return true;
        }
        return false;
    }
    public void revokeToken(String token){
        hashSet.remove(token);
    }
    public String getUsernameFromToken(String token){
        if(validate(token)){
            Verifier verifier = HMACVerifier.newVerifier(secretKey);
            JWT jwt = JWT.getDecoder().decode(token.substring(3), verifier);
            return jwt.getString("userId");
        }
        return "";
    }

    public String getIssuerFromToken(String token) {
        if(validate(token)){
            Verifier verifier = HMACVerifier.newVerifier(secretKey);
            JWT jwt = JWT.getDecoder().decode(token.substring(3),verifier);
            return jwt.issuer;
        }
        return "";
    }
}
