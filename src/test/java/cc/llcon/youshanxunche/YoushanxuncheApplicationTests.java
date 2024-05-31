package cc.llcon.youshanxunche;

import cc.llcon.youshanxunche.pojo.ListUserParam;
import cc.llcon.youshanxunche.utils.JwtUtils;
import com.alibaba.fastjson2.JSON;
import com.sanctionco.jmail.EmailValidationResult;
import com.sanctionco.jmail.JMail;
import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.junit.jupiter.api.Test;
import org.passay.*;

import java.util.*;

//@SpringBootTest
@Slf4j
class YoushanxuncheApplicationTests {


    //@Test
    void getClaims() {
        String JWT = "eyJhbGciOiJIUzI1NiJ9.eyJwZXJtaXNzaW9uIjoxLCJpZCI6IjRhOGNkMDMzLWZhMWItMTFlZS05OWNlLTAwZmZjYWRiM2FmMCIsImV4cCI6MTcxNDIzOTE0MSwidXNlcm5hbWUiOiJqYW1lcjU2In0.7Jl3-TAIWZazpnVbWUvFkBdAGfoUMdEC0vmJOm8NZxA";

        Claims claims = JwtUtils.parseJWT(JWT);
        String id = (String) claims.get("id");
        System.out.println(id);
    }


//@Test
    void password(){
        PasswordData passwordData = new PasswordData("jamer56@JC5441");
        passwordData.setUsername("jamer56");

        List<Rule> rules = new ArrayList<Rule>();
        rules.add(new LengthRule(8,32));
        CharacterCharacteristicsRule characterCharacteristicsRule =new CharacterCharacteristicsRule(4,
                new CharacterRule(EnglishCharacterData.UpperCase,1),
                new CharacterRule(EnglishCharacterData.LowerCase,1),
                new CharacterRule(EnglishCharacterData.Digit,1),
                new CharacterRule(EnglishCharacterData.Special,1)
        );
        rules.add(characterCharacteristicsRule);
        rules.add(new UsernameRule());
        rules.add(new  WhitespaceRule());

        PasswordValidator passwordValidator = new PasswordValidator(rules);
        RuleResult validate = passwordValidator.validate(passwordData);

        String string = validate.getDetails().toString();

        if (!validate.isValid()){
            log.error(validate.getDetails().toString());
        }else {
            log.warn("成功");
        }

    }



//    @Test
    void email() {
        String email = "";

        EmailValidationResult validate = JMail.validate(email);

        if (validate.isSuccess()){
            log.info("email验证通过");
        }
        log.warn("失败原因:{}",validate.getFailureReason());
    }

    //    @Test
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

//    @Test
    void UUID(){
        log.info(UUID.randomUUID().toString().replace("-",""));
        log.info(UUID.randomUUID().toString().replace("-",""));
    }
//    @Test
    void getenv(){
        String secret = System.getenv("PATH");

        System.out.println(secret);
    }
//    @Test
    void Jwt(){
        String secret = System.getenv("JWT_SIGNKEY");

        Map<String, Object> claims = new HashMap<>();
        claims.put("id", "123");
        claims.put("username", "456");
        claims.put("permission", "789");
        String s = JwtUtils.generateJWT(claims);

        log.info(secret);
        log.info(s);
    }
//    @Test
    void tostringtest(){
        ListUserParam userParam = new ListUserParam();

        userParam.setEmail("dsf");

        log.info(JSON.toJSONString(userParam));
        log.info(userParam.toString());
    }
}
