package com.edu.common.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "oss")
public class OssConfig {

    public String accessKeyId;

    public String accessKeySecret;

    public String bucket;

    public String endpoint;

    public String cdnUrl;
}
