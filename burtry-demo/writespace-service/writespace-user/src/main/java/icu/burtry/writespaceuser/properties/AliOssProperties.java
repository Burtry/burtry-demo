package icu.burtry.writespaceuser.properties;


import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "writespace.alioss")
@Data
public class AliOssProperties {

    private String endpoint;
    private String bucketName;

}