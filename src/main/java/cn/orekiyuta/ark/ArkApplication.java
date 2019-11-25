package cn.orekiyuta.ark;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@MapperScan("cn.orekiyuta,ark.mapper")
@EnableScheduling
public class ArkApplication {

    public static void main(String[] args) {
        SpringApplication.run(ArkApplication.class, args);
    }

}
