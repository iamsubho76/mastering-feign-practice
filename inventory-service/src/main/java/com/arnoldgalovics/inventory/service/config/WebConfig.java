package com.arnoldgalovics.inventory.service.config;

import com.arnoldgalovics.inventory.service.processor.MillisToOffsetDateTimeFormatter;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addFormatter(new MillisToOffsetDateTimeFormatter());
    }
}
