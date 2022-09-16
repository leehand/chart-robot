package com.mongcent.bootstrap;

import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceAutoConfigure;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * 排除 原生Druid的快速配置类。
 * DruidDataSourceAutoConfigure会注入一个DataSourceWrapper，其会在原生的spring.datasource下找url,username,password等。而我们动态数据源的配置路径是变化的
 */
@SpringBootApplication(scanBasePackages = "com.mongcent", exclude = DruidDataSourceAutoConfigure.class)
@EnableScheduling
public class ChartManagerApplication {

    public static void main(String[] args) {
        SpringApplication.run(ChartManagerApplication.class, args);
    }
}
