package com.raihanorium.vpp.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "app")
@Data
public class AppConfig {

    private int maxPostcodesInQuery;
    private String batteryQueryStrategy;
}
