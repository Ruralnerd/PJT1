package com.inline.sub2.util;

import java.security.Key;
import java.util.Date;

import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;

import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class JwtUtil{

    public static final Logger logger = LoggerFactory.getLogger(JwtUtil.class);

    @Value("${jwt.secret")
    private String SECRET_KEY;
    @Value("${jwt.expiration}")
    private int EXPIRE_TIME;

    public <T> String createToken(String email, T data, String subject) {
        Date now = new Date();
        //HS256 방식으로 암호화 방식 설정
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
        byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary(SECRET_KEY);
        Key signingKey = new SecretKeySpec(apiKeySecretBytes, signatureAlgorithm.getJcaName());

        JwtBuilder builder = Jwts.builder()
                .setSubject(subject) // user를 구분할 수 있는 값
                .setExpiration(new Date(now.getTime() + EXPIRE_TIME))
                .setAudience(email)
                .signWith(SignatureAlgorithm.HS256, signingKey); //암호화 알고리즘

        return builder.compact();
    }

    //Jwt Token을 복호화해 이름을 return
    public String getUserNameFromJwt(String jwt){
        return getClaims(jwt).getBody().getId();
    }

    public boolean validateToken(String jwt){
        return this.getClaims(jwt) != null;
    }2

    //claims : 속성 정보(?), 권한 집합
    //JWT는 속성 정보 (Claim)를 JSON 데이터 구조로 표현한 토큰
    //Jwt토큰 유효성 검증 메서드
    private Jws<Claims> getClaims(String jwt){
        try{
            return Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(jwt);
        }catch (SignatureException ex) {
            log.error("Invalid JWT signature");
            throw ex;
        } catch (MalformedJwtException ex) {
            log.error("Invalid JWT token");
            throw ex;
        } catch (ExpiredJwtException ex) {
            log.error("Expired JWT token");
            throw ex;
        } catch (UnsupportedJwtException ex) {
            log.error("Unsupported JWT token");
            throw ex;
        } catch (IllegalArgumentException ex) {
            log.error("JWT claims string is empty.");
            throw ex;
        }
    }

//    //	전달 받은 토큰이 제대로 생성된것인지 확인 하고 문제가 있다면 UnauthorizedException을 발생.
//    @Override
//    public boolean isUsable(String jwt) {
//        try {
//            Claims claims = Jwts.parser()
//                    .setSigningKey(DatatypeConverter.parseBase64Binary(SECRET_KEY))
//                    .parseClaimsJws(jwt).getBody();
//            return true;
//        } catch (Exception e) {
//            logger.error(e.getMessage());
//            return false;
//        }
//    }
}