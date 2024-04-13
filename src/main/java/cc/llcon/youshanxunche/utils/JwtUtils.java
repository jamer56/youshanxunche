package cc.llcon.youshanxunche.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;
import java.util.Map;

public class JwtUtils {
	private static final String signKey = "8711d18c0fb74e0f84f3e443722c7a5d";
	private static final Long expire = 3600000L;


	public static String generateJWT(Map<String,Object> claims){
	String jwt = Jwts.builder()
			.signWith(SignatureAlgorithm.HS256, signKey)
			.setClaims(claims)
			.setExpiration(new Date(System.currentTimeMillis() + expire))
			.compact();
	return jwt;
	}

	public static Claims parseJWT(String jwt){
		Claims claims = Jwts.parser()
				.setSigningKey(signKey)
				.parseClaimsJws(jwt)
				.getBody();
		return claims;
	}

}

