package com.qfleaf.yunbi;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.qfleaf.yunbi.dao.mapper")
public class YunbiBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(YunbiBackendApplication.class, args);
    }

}
