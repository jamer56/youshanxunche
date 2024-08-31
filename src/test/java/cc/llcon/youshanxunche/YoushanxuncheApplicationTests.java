package cc.llcon.youshanxunche;

import cc.llcon.youshanxunche.constant.VerificationCodeType;
import cc.llcon.youshanxunche.controller.VerifcationCodeController;
import cc.llcon.youshanxunche.controller.request.VerificationCodeRequest;
import cc.llcon.youshanxunche.service.MailService;
import cc.llcon.youshanxunche.utils.JwtUtils;
import com.sanctionco.jmail.EmailValidationResult;
import com.sanctionco.jmail.JMail;
import com.sanctionco.jmail.ValidationRules;
import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.junit.jupiter.api.Test;
import org.passay.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.math.BigInteger;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.*;

@SpringBootTest
@Slf4j
class YoushanxuncheApplicationTests {

    @Autowired
    MailService mailService;

    @Autowired
    VerifcationCodeController verifcationCodeController;

    //@Test
    void getClaims() {
        String JWT = "eyJhbGciOiJIUzI1NiJ9.eyJwZXJtaXNzaW9uIjoxLCJpZCI6IjRhOGNkMDMzLWZhMWItMTFlZS05OWNlLTAwZmZjYWRiM2FmMCIsImV4cCI6MTcxNDIzOTE0MSwidXNlcm5hbWUiOiJqYW1lcjU2In0.7Jl3-TAIWZazpnVbWUvFkBdAGfoUMdEC0vmJOm8NZxA";

        Claims claims = JwtUtils.parseJWT(JWT);
        String id = (String) claims.get("id");
        System.out.println(id);
    }


    //@Test
    void password() {
        PasswordData passwordData = new PasswordData("jamer56@JC5441");
        passwordData.setUsername("jamer56");

        List<Rule> rules = new ArrayList<Rule>();
        rules.add(new LengthRule(8, 32));
        CharacterCharacteristicsRule characterCharacteristicsRule = new CharacterCharacteristicsRule(4,
                new CharacterRule(EnglishCharacterData.UpperCase, 1),
                new CharacterRule(EnglishCharacterData.LowerCase, 1),
                new CharacterRule(EnglishCharacterData.Digit, 1),
                new CharacterRule(EnglishCharacterData.Special, 1)
        );
        rules.add(characterCharacteristicsRule);
        rules.add(new UsernameRule());
        rules.add(new WhitespaceRule());

        PasswordValidator passwordValidator = new PasswordValidator(rules);
        RuleResult validate = passwordValidator.validate(passwordData);

        String string = validate.getDetails().toString();

        if (!validate.isValid()) {
            log.error(validate.getDetails().toString());
        } else {
            log.warn("成功");
        }

    }


    //    @Test
    void email() {
        String email = "";

        EmailValidationResult validate = JMail.validate(email);

        if (validate.isSuccess()) {
            log.info("email验证通过");
        }
        log.warn("失败原因:{}", validate.getFailureReason());
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
    void UUID() {
        log.info(UUID.randomUUID().toString().replace("-", ""));
        log.info(UUID.randomUUID().toString().replace("-", ""));
    }

    //    @Test
    void getenv() {
        String secret = System.getenv("PATH");

        System.out.println(secret);
    }

    //    @Test
    void Jwt() {
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
    void tostringtest() {
        byte[] a = new byte[8];

        for (int i = 0; i < 8; i++) {
            a[i] = (byte) ((i + 6) * 17);
        }
        BigInteger bigInteger = new BigInteger(1, a);
        BigInteger bigInteger1 = BigInteger.valueOf(16516511665156L);
        System.out.println(bigInteger1);
        System.out.printf(bigInteger1.toString(16));

    }

    //    @Test
    void datetime() {
        LocalDateTime localDateTime = LocalDateTime.now();
        LocalDateTime locakDate = LocalDateTime.of(2024, Month.JUNE, 15, 6, 15, 15);

        log.info("{}", localDateTime.isBefore(locakDate));

    }

    //    @Test
    void parseTest() {

        String uuid = "027b4f4abfec48d687fe84c9c7d4bf9c";
        if (uuid.contains("-")) {

        }

        String substring1 = uuid.substring(0, 16);
        String substring2 = uuid.substring(16, 32);

        UUID uuid1 = UUID.fromString(uuid);

        log.info("{}", uuid1);


        long l1 = Long.parseLong("027b4f4abfec48d6", 16);
        long l2 = Long.parseLong(substring1, 16);
        long l3 = Long.parseLong(substring2, 16);
        log.info("{}", l1);
        log.info("{}", l2);
        log.info("{}", l3);
    }

    //    @Test
    void test() {
//        mailService.sendPlainText();
        File mailTemplate = null;
        StringBuilder mailContent = new StringBuilder();

        try {
            mailTemplate = ResourceUtils.getFile("classpath:mailtemplate.html");
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        try {
            Scanner scanner = new Scanner(mailTemplate);

            while (scanner.hasNext()) {
                mailContent.append(scanner.nextLine());
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        String mail = mailContent.toString().replace("{{nickname}}", "jamer56").replace("{{code}}", "123456");


        Collection<String> receivers = Arrays.asList("l8898b@hotmail.com");
//        mailService.sendRegisterVerificationCode(receivers, "友善尋車系統 註冊驗證碼", mail);
    }


    @Test
    void testemailsend() {
        VerificationCodeRequest request = new VerificationCodeRequest("l8898b@hotmail.com", "jamer56", VerificationCodeType.REGISTER);
        verifcationCodeController.generateVerificationCode(request);
    }
}
