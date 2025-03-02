package com.server.ggini.global.config.properties;

import com.server.ggini.global.properties.jwt.JwtProperties;
import com.server.ggini.global.properties.swagger.SwaggerProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@EnableConfigurationProperties({
    JwtProperties.class,
    SwaggerProperties.class
})
@Configuration
public class PropertiesConfig {

}
