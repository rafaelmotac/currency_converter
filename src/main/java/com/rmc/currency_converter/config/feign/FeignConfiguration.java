package com.rmc.currency_converter.config.feign;

import feign.Logger.Level;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FeignConfiguration {

    @Bean
    Level feignLoggerLevel() {
        return Level.FULL;
    }

}
