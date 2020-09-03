package com.zhuang.music_cms.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @Package     : com.zhuang.music_cms.config
 * @ClassName   : AliyunOSSConfig 
 * @Description : 阿里云oss配置类
 * @Author      : Zhuang
 * @Date        : 2020-04-24 21:02
 */

@ConfigurationProperties(prefix = "aliyun-oss")
@Data
public class AliyunOSSConfig {

    /**
     * 服务器地点
     */
    private String region;
    /**
     * 服务器地址
     */
    private String endpoint;
    /**
     * OSS身份id
     */
    private String accessKeyId;
    /**
     * 身份密钥
     */
    private String accessKeySecret;

    /**
     * App文件bucketName
     */
    private String bucketApp;
    /**
     * App包文件地址前缀
     */
    private String domainApp;

}
