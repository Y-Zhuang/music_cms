package com.zhuang.music_cms;

import com.zhuang.music_cms.config.AliyunOSSConfig;
import com.zhuang.music_cms.utils.AliyunOSSUtils;
import com.zhuang.music_cms.utils.JwtUtils;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.orm.jpa.support.OpenEntityManagerInViewFilter;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
public class MusicCmsServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(MusicCmsServiceApplication.class, args);
    }

    @Bean
    public JwtUtils jwtUtils() {
        return new JwtUtils();
    }

    @Bean
    public AliyunOSSConfig aliyunOSSConfig() {
        return new AliyunOSSConfig();
    }

    @Bean
    public AliyunOSSUtils aliyunOSSUtils() {
        return new AliyunOSSUtils();
    }

    @Bean
    public OpenEntityManagerInViewFilter openEntityManagerInViewFilter() {
        return new OpenEntityManagerInViewFilter();
    }
}
