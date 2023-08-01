package com.sky.config;

import com.sky.properties.AliOssProperties;
import com.sky.utils.AliOssUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AliOssConfiguration {

    @Autowired
    private AliOssProperties aliOssProperties;

    @Bean
    public AliOssUtil aliOssUtil(){
        AliOssUtil aliOssUtil = new AliOssUtil();
        aliOssUtil.setAccessKeyId(aliOssProperties.getAccessKeyId());
        aliOssUtil.setAccessKeySecret(aliOssProperties.getAccessKeySecret());
        aliOssUtil.setEndpoint(aliOssProperties.getEndpoint());
        aliOssUtil.setBucketName(aliOssProperties.getBucketName());

        return aliOssUtil;

    }
}
