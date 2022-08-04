package com.woowacourse.kkogkkog;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class KkogKkogApplication {

    public static void main(String[] args) {
        SpringApplication.run(KkogKkogApplication.class, args);
    }
}
