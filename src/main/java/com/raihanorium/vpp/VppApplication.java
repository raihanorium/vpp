package com.raihanorium.vpp;

import com.raihanorium.vpp.config.AppConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.support.ResourceBundleMessageSource;

@SpringBootApplication
@EnableConfigurationProperties(AppConfig.class)
public class VppApplication {

    public static void main(String[] args) {
        SpringApplication.run(VppApplication.class, args);
    }

    @Bean
    public MessageSource messageSource() {
        ResourceBundleMessageSource source = new ResourceBundleMessageSource();
        source.setBasename("ValidationMessages");
        source.setDefaultEncoding("UTF-8");
        return source;
    }

}
