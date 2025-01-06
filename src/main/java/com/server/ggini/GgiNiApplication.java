package com.server.ggini;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class GgiNiApplication {

    public static void main(String[] args) {
        SpringApplication.run(GgiNiApplication.class, args);
    }

}
