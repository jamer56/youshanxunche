package cc.llcon.youshanxunche.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;
import java.util.Map;

public class JwtUtils {
    private static final String SECRET_KEY = System.getenv("JWT_SECRET_KEY");
    private static final Long EXPIRE = 3600000L;


    public static String generateJWT(Map<String, Object> claims) {
        if (SECRET_KEY == null) {
            throw new RuntimeException("SECRET_KEY 為空",new RuntimeException("JWT錯誤"));
        }
        String jwt = Jwts.builder()
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
                .setClaims(claims)
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRE))
                .compact();
        return jwt;
    }

    public static Claims parseJWT(String jwt) {
        if (SECRET_KEY == null) {
            throw new RuntimeException("SECRET_KEY 為空",new RuntimeException("JWT錯誤"));
        }
        Claims claims = Jwts.parser()
                .setSigningKey(SECRET_KEY)
                .parseClaimsJws(jwt)
                .getBody();
        return claims;
    }

}

