package com.yupi.yuaicodemother;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@SpringBootApplication
@MapperScan("com.yupi.yuaicodemother.mapper")
@EnableAspectJAutoProxy(exposeProxy = true)
public class YupiAiCodeMotherApplication {

    public static void main(String[] args) {
        SpringApplication.run(YupiAiCodeMotherApplication.class, args);
    }

}
