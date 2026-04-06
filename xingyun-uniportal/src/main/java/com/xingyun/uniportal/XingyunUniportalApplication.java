package com.xingyun.uniportal;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

/**
 * 启动入口，继承SpringBootServletInitializer（用于将spring-boot应用程序部署到传统的Servlet容器如Tomcat、Jetty）
 */
@SpringBootApplication
public class XingyunUniportalApplication extends SpringBootServletInitializer {
    public static void main(String[] args) {
        SpringApplication.run(XingyunUniportalApplication.class, args);
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(XingyunUniportalApplication.class);
    }
}
