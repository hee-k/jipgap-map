package com.jipgap.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@Setter
@ConfigurationProperties(prefix = "molit.api")
public class MolitApiProperties {
    private String baseUrl;
    private String key;
}
