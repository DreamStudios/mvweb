package com.blueshit.neweast;

import com.blueshit.neweast.repository.MvWebBaseRepositoryFactoryBean;
import org.apache.catalina.connector.Connector;
import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.embedded.EmbeddedServletContainerFactory;
import org.springframework.boot.context.embedded.tomcat.TomcatConnectorCustomizer;
import org.springframework.boot.context.embedded.tomcat.TomcatEmbeddedServletContainerFactory;
import org.springframework.boot.context.web.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.filter.CharacterEncodingFilter;

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
        System.out.println("数据库密码:admin!@#456 加密后是：ENC(" + encryptor.encrypt("admin!@#456") + ")");
        return "就不告诉你！";
    }

    // 字符集设置过滤
    @Bean
    public CharacterEncodingFilter characterEncodingFilter() {
        final CharacterEncodingFilter characterEncodingFilter = new CharacterEncodingFilter();
        characterEncodingFilter.setEncoding("UTF-8");
        characterEncodingFilter.setForceEncoding(true);
        System.out.println("Request字符集设置为UTF-8");
        return characterEncodingFilter;
    }

    // 修改tomcat UseBodyEncodingForURI参数为true
    @Bean
    public EmbeddedServletContainerFactory servletContainer() {
        TomcatEmbeddedServletContainerFactory factory = new TomcatEmbeddedServletContainerFactory();
        factory.getTomcatConnectorCustomizers().add(
                new TomcatConnectorCustomizer() {

                    @Override
                    public void customize(Connector connector) {
                        connector.setURIEncoding("utf-8");
                        connector.setUseBodyEncodingForURI(true);
                        System.out
                                .println("Tomcat connector UseBodyEncodingForURI=true");
                    }

                });
        return factory;
    }
}
