package cc.llcon.youshanxunche;

import cc.llcon.youshanxunche.utils.JwtUtils;
import io.jsonwebtoken.Claims;
import org.apache.commons.codec.digest.DigestUtils;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.UUID;

//@SpringBootTest
class YoushanxuncheApplicationTests {





    //@Test
    void getClaims(){
        String JWT = "eyJhbGciOiJIUzI1NiJ9.eyJwZXJtaXNzaW9uIjoxLCJpZCI6IjRhOGNkMDMzLWZhMWItMTFlZS05OWNlLTAwZmZjYWRiM2FmMCIsImV4cCI6MTcxNDIzOTE0MSwidXNlcm5hbWUiOiJqYW1lcjU2In0.7Jl3-TAIWZazpnVbWUvFkBdAGfoUMdEC0vmJOm8NZxA";

        Claims claims = JwtUtils.parseJWT(JWT);
        String id = (String) claims.get("id");
        System.out.println(id);
    }



    @Test
    void contextLoads() {

        String password = "123456";
//        String salt = UUID.randomUUID().toString();

        String salt = "2013b064623947c3940fa27355de2330";


        //加盐
        password = password.concat(salt);
        System.out.println(password);

        //杂凑
        password = DigestUtils.sha256Hex(password);

        System.out.println(password);



    }

}
