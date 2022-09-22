package com.arnoldgalovics.online.store.service.common.config;

import com.arnoldgalovics.online.store.service.inventory.helper.InventoryServiceErrorDecoder;
import com.arnoldgalovics.online.store.service.common.interceptor.SourceRequestInterceptor;
import com.arnoldgalovics.online.store.service.inventory.InventoryServiceClient;
import com.arnoldgalovics.online.store.service.session.UserSessionClient;
import com.codahale.metrics.SharedMetricRegistries;
import com.codahale.metrics.jmx.JmxReporter;
import feign.*;
import feign.jackson.JacksonDecoder;
import feign.jackson.JacksonEncoder;
import feign.micrometer.MicrometerCapability;
import feign.slf4j.Slf4jLogger;
import io.micrometer.core.instrument.Clock;
import io.micrometer.jmx.JmxConfig;
import io.micrometer.jmx.JmxMeterRegistry;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.TimeUnit;

@Configuration
public class FeignConfiguration {
    @Bean
    public UserSessionClient userSessionClient() {
        return AsyncFeign.asyncBuilder()
                .logLevel(Logger.Level.FULL)
                .logger(new Slf4jLogger())
                .encoder(new JacksonEncoder())
                .decoder(new JacksonDecoder())
                .requestInterceptor(new SourceRequestInterceptor())
                //Customize ConnectionTimeOut, ReadTimeOut and the last parameter Redirect(which by default for Feign Client is True) if we make it False then
                //request will not be redirected(which usually contain 3XX error codes) to the /V2 user-session api endpoint of user-session application
                .options(new Request.Options(10, TimeUnit.SECONDS, 10, TimeUnit.SECONDS, true))
                .target(UserSessionClient.class, "http://localhost:8082");
    }

    @Bean
    public InventoryServiceClient inventoryServiceClient() {
//        JmxReporter reporter = JmxReporter.forRegistry(SharedMetricRegistries.getOrCreate("feign")).build();
//        reporter.start();

        return Feign.builder()
                //Configuring Dropwizard metrics and this metrics will be created in JConsole upon the first call of product creation api
                .addCapability(new MicrometerCapability(new JmxMeterRegistry(JmxConfig.DEFAULT, Clock.SYSTEM)))
                .logLevel(Logger.Level.FULL)
                .logger(new Slf4jLogger())
                .encoder(new JacksonEncoder())
                .decoder(new JacksonDecoder())
                .errorDecoder(new InventoryServiceErrorDecoder())
                //By default the Feign client will retry will 5 times in-case of Retryable Exception or IOException
                //so if we want to customize it below line is the implementation
                .retryer(new Retryer.Default(1000, 5000, 2))
                .requestInterceptor(new SourceRequestInterceptor())
                .target(InventoryServiceClient.class, "http://localhost:8081");
    }
}
