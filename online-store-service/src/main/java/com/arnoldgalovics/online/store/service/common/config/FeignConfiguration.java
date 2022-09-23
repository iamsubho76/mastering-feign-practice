package com.arnoldgalovics.online.store.service.common.config;

import com.arnoldgalovics.online.store.service.common.helper.OffsetDateTimeToMillisFormatter;
import feign.AsyncFeign;
import feign.Feign;
import feign.Logger;
import feign.Target;
import feign.codec.Decoder;
import feign.codec.ErrorDecoder;
import org.springframework.cloud.openfeign.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.util.ReflectionUtils;

@Configuration
@EnableFeignClients(basePackages = "com.arnoldgalovics.online.store.service")
public class FeignConfiguration implements FeignFormatterRegistrar {
    @Override
    public void registerFormatters(FormatterRegistry registry) {
        registry.addFormatter(new OffsetDateTimeToMillisFormatter());
    }

    @Bean
    public Logger.Level loggerLevel() {
        return Logger.Level.FULL;
    }
}
