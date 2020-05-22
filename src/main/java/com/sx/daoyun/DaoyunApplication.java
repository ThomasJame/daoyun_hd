package com.sx.daoyun;

import com.sx.daoyun.Filter.CrosFilter;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
//@MapperScan("com.sx.daoyun.mapper")
public class DaoyunApplication {

    public static void main(String[] args) {
        SpringApplication.run(DaoyunApplication.class, args);
    }

    @Bean
    public FilterRegistrationBean registerFilter(){
        FilterRegistrationBean bean = new FilterRegistrationBean();
        bean.addUrlPatterns("/*");
        bean.setFilter(new CrosFilter());
        return bean;
    }
}
