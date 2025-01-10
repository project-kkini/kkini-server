package com.server.ggini.global.config.properties;

import com.server.ggini.global.properties.jwt.JwtProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@EnableConfigurationProperties({
    JwtProperties.class
})
@Configuration
public class PropertiesConfig {

}
