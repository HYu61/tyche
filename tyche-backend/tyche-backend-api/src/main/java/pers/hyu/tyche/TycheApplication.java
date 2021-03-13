package pers.hyu.tyche;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@MapperScan("pers.hyu.tyche.dao")
//@EnableSwagger2

public class TycheApplication {
    public static void main(String[] args) {
        SpringApplication.run(TycheApplication.class, args);
    }
}
