package cc.llcon.youshanxunche.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;
import java.util.Map;

public class JwtUtils {
	private static final String SIGNKEY = "8711d18c0fb74e0f84f3e443722c7a5d";
	private static final Long EXPIRE = 3600000L;


	public static String generateJWT(Map<String,Object> claims){
	String jwt = Jwts.builder()
			.signWith(SignatureAlgorithm.HS256, SIGNKEY)
			.setClaims(claims)
			.setExpiration(new Date(System.currentTimeMillis() + EXPIRE))
			.compact();
	return jwt;
	}

	public static Claims parseJWT(String jwt){
		Claims claims = Jwts.parser()
				.setSigningKey(SIGNKEY)
				.parseClaimsJws(jwt)
				.getBody();
		return claims;
	}

}

