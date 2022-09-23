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

    //As Spring Cloud Open Feign does not support Async call as like we have CompletableFuture in UserSessionClient, so wee need to use below bean to achive it
    //but it is recommended not to use this Async version as it will not work with Eureka, LoadBalancer
    //so we should use original blocking version i.e; without using CompletableFuture
    @Bean
    public Targeter feignTargeter(){
        return new Targeter() {
            @Override
            public <T> T target(FeignClientFactoryBean factory, Feign.Builder builder, FeignContext context, Target.HardCodedTarget<T> target) {
                String contextId = factory.getContextId();
                AsyncFeign.AsyncBuilder<Object> objectAsyncBuilder = AsyncFeign.asyncBuilder();
                objectAsyncBuilder.decoder(context.getInstance(contextId, Decoder.class));
                objectAsyncBuilder.errorDecoder(context.getInstance(contextId, ErrorDecoder.class));
                if(factory.isDecode404()){
                    objectAsyncBuilder.decode404();
                }
                ReflectionUtils.doWithFields(AsyncFeign.AsyncBuilder.class, field -> {
                    ReflectionUtils.makeAccessible(field);
                    ReflectionUtils.setField(field, objectAsyncBuilder, builder);
                }, field -> field.getName().equalsIgnoreCase("builder"));
                return objectAsyncBuilder.target(target);
            }
        };
    }
}
