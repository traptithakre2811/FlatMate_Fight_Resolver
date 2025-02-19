package com.flatmate.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import java.util.Date;
import java.util.Map;
@Component
public class JwtTokenUtil {
    @Value("${secret.key}")
    private String secretKey;

    public String generateToken(Map<String, Object> claim, String userName) {
        return Jwts.builder().setClaims(claim).setSubject(userName).setIssuedAt(new Date())
                .setExpiration(new Date((new Date()).getTime() + 864000 * 60))
                .signWith(SignatureAlgorithm.HS512, "trapti").compact();

    }


    public Claims getAllClaims(String token) {
        Claims bodyClaims = Jwts.parser().setSigningKey("trapti").parseClaimsJws(token).getBody();
        return bodyClaims;
    }

    public String getUserNameFromToken(String token) {
        Claims allClaims = getAllClaims(token);
        return allClaims.getSubject();
    }

    public Date getExpirationTimeFromToken(String token) {
        Claims allClaims = getAllClaims(token);
        Date expiration = allClaims.getExpiration();
        return expiration;
    }

    public boolean isTokenExpireOrNot(String token) {
        Date expirationTimeFromToken = getExpirationTimeFromToken(token);
        return expirationTimeFromToken.before(new Date());
    }

    public boolean validateToken(String userDetails, String token) {
        String userNameFromToken = getUserNameFromToken(token);
        boolean tokenExpireOrNot = isTokenExpireOrNot(token);
        if (userNameFromToken.equals(userDetails) && !tokenExpireOrNot) {
            return true;
        }
        return false;

    }
}
