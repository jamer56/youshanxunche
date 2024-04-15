package cc.llcon.youshanxunche;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;


@MapperScan("cc.llcon.youshanxunche.mapper")
@ServletComponentScan
@SpringBootApplication
public class YoushanxuncheApplication {

    public static void main(String[] args) {
        SpringApplication.run(YoushanxuncheApplication.class, args);
    }

}
