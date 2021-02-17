package com.xingbg.spring.annotation;

import com.xingbg.spring.annotation.pojo.User;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan("com.xingbg.spring.annotation")
public class MyConfig {

    @Bean
    public User getUser() {
        return new User();
    }
}
