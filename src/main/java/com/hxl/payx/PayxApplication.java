package com.hxl.payx;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author hxl
 */
@SpringBootApplication
@MapperScan(basePackages = "com.hxl.payx.dao")
public class PayxApplication {

    public static void main(String[] args) {
        SpringApplication.run(PayxApplication.class, args);
    }

}
