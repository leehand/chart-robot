package com.mongcent.bootstrap.config;

import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * mybatis-plus配置
 * 扫描指定的文件夹
 *
 * @author zl
 */
@ConditionalOnClass(PaginationInterceptor.class)
@Configuration
@MapperScan("com.mongcent.**.mapper")
public class MybatisPlusConfig {

    /**
     * 分页插件
     */
    @Bean
    public PaginationInterceptor paginationInterceptor() {
        return new PaginationInterceptor();
    }
}
