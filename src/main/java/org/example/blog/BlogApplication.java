package org.example.blog;

import lombok.extern.slf4j.Slf4j;
import org.example.blog.utils.IdWorker;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Slf4j
@EnableSwagger2
@SpringBootApplication
public class BlogApplication {

    public static void main(String[] args) {
        log.info("BlogApplication..");
        SpringApplication.run(BlogApplication.class, args);
    }

    @Bean
    public IdWorker createIdWorker() {
        return new IdWorker(1,1,1);
    }

    @Bean
    public BCryptPasswordEncoder createPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
