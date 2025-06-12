package com.edu.common.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "mps")
public class MtsConfig {

    public String pipelineId;

    public String videoTemplateId;

    public String audioTemplateId;

    public String mtsRegion;

    public String ossRegion;

}
