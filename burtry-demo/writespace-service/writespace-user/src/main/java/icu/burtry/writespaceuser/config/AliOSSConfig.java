package icu.burtry.writespaceuser.config;


import icu.burtry.writespaceuser.properties.AliOssProperties;
import icu.burtry.writespaceutils.utils.AliOSSUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 配置类，用于创建AliOssUntil对象
 */
@Configuration
@Slf4j
public class AliOSSConfig {
    @Bean
    @ConditionalOnMissingBean  //保证容器中只有这一个bean对象
    public AliOSSUtil aliOssUtil(AliOssProperties aliOssProperties) {
        log.info("create aliyun file upload utils object:{}",aliOssProperties);
        return new AliOSSUtil(aliOssProperties.getEndpoint(),
                aliOssProperties.getBucketName());
    }
}
