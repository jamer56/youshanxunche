package cc.llcon.youshanxunche.utils;

import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;

public class AuthUtils {

    public static String getUID(HttpServletRequest request) {
        String jwt = request.getHeader("Authorization");
        Claims claims = JwtUtils.parseJWT(jwt);
        return (String) claims.get("id");
    }

    public static boolean auth(HttpServletRequest request, String uID) {
        String jwt = request.getHeader("Authorization");
        Claims claims = JwtUtils.parseJWT(jwt);
        String uid = (String) claims.get("id");

        return uID.equals(uid);
    }
}
