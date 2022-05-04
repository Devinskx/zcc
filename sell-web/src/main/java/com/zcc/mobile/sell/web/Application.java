package com.zcc.mobile.sell.web;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;

import java.awt.*;
import java.net.URI;

/**
 * @author Devin sun
 * @date 2022/2/27
 */
@SpringBootApplication(scanBasePackages = {"com.zcc.mobile"})
@MapperScan("com.zcc.mobile.sell.domain.dao")
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
//        ConfigurableApplicationContext context = new SpringApplicationBuilder(Application.class).run(args);
//        int port = context.getEnvironment().getProperty("server.port", int.class);
        System.setProperty("java.awt.headless", "false");
        try {
            Desktop.getDesktop().browse(new URI("http://127.0.0.1"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
