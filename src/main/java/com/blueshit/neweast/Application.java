package com.blueshit.neweast;

import com.blueshit.neweast.repository.MvWebBaseRepositoryFactoryBean;
import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.web.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;

@Configuration
@ComponentScan
@EnableAutoConfiguration
@EnableScheduling
@EnableJpaRepositories(
        basePackages = "com.blueshit.neweast.repository",
        repositoryFactoryBeanClass = MvWebBaseRepositoryFactoryBean.class
)
public class Application extends SpringBootServletInitializer {

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        //数据库密码加密密钥：
        System.setProperty("tadirect.jasypt.key", "password");
        return application.sources(Application.class);
    }

    public static void main(String[] args) {
        //数据库密码加密密钥：
        System.setProperty("tadirect.jasypt.key", "password");
        SpringApplication.run(Application.class, args);
    }

    @Bean
    public String passwordShow() {
        StandardPBEStringEncryptor encryptor = new StandardPBEStringEncryptor();
        encryptor.setPassword("password");
        System.out.println("数据库密码:root 加密后是：ENC(" + encryptor.encrypt("root") + ")");
        System.out.println("数据库密码:123456 加密后是：ENC(" + encryptor.encrypt("123456") + ")");
        return "就不告诉你！";
    }
}
