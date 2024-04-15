package cc.llcon.youshanxunche;

import org.apache.commons.codec.digest.DigestUtils;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.UUID;

//@SpringBootTest
class YoushanxuncheApplicationTests {

    @Test
    void contextLoads() {

        String password = "123456";
        String salt =UUID.randomUUID().toString();


        //加盐
        password = password.concat(salt);
        System.out.println(password);

        //杂凑
        password = DigestUtils.sha256Hex(password);

        System.out.println(password);



    }

}
