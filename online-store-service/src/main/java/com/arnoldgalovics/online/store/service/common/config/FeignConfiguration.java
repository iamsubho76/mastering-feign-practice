package com.arnoldgalovics.online.store.service.common.config;

import com.arnoldgalovics.online.store.service.common.helper.OffsetDateTimeToMillisFormatter;
import feign.AsyncFeign;
import feign.Feign;
import feign.Logger;
import feign.Target;
import feign.codec.Decoder;
import feign.codec.ErrorDecoder;
import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig;
import io.github.resilience4j.timelimiter.TimeLimiterConfig;
import org.springframework.cloud.circuitbreaker.resilience4j.Resilience4JCircuitBreakerFactory;
import org.springframework.cloud.client.circuitbreaker.Customizer;
import org.springframework.cloud.openfeign.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Method;
import java.time.Duration;

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
    @Bean
    public Customizer<Resilience4JCircuitBreakerFactory> circuitBreakerFactoryCustomizer() {
        CircuitBreakerConfig cbConfig = CircuitBreakerConfig.custom()
                .slidingWindowType(CircuitBreakerConfig.SlidingWindowType.COUNT_BASED)
                .slidingWindowSize(5)
                .failureRateThreshold(20.0f)
                .waitDurationInOpenState(Duration.ofSeconds(5))
                .permittedNumberOfCallsInHalfOpenState(5)
                //below one defined how much time is acceptable & beyond that we
                .slowCallDurationThreshold(Duration.ofSeconds(2))
                // and how many api calls will trigger the circuit-breaker open if we exceed the above 2s time frame which mention below as 80%
                // that mean out of the 5 req if 4 req is taking more than 2s time then the Circuit will open
                .slowCallRateThreshold(80.0f)
                .build();
        // The default time-limit is 1s to get the response back
        // Configuring slowness-based circuit breaking with Resilience4J for this we use TimeLimiterConfig
        TimeLimiterConfig timeLimiterConfig = TimeLimiterConfig.custom().timeoutDuration(Duration.ofSeconds(5)).build();
        return resilience4JCircuitBreakerFactory -> resilience4JCircuitBreakerFactory.configure(builder ->
                // we are using "UserSessionClient#validateSession(UUID,Map)", "UserSessionClient#validateSession(Map,Map)" bcoz we have 2
                // different methods inside UserSessionClient
                builder.circuitBreakerConfig(cbConfig).timeLimiterConfig(timeLimiterConfig), "UserSessionClient#validateSession(UUID,Map)", "UserSessionClient#validateSession(Map,Map)");
    }

    /**
     * N.B: To provide the circuitBreakerName we need below defined method and
     * this will be got invoked inside circuitBreakerFactoryCustomizer by using -> UserSessionClient#validateSession
     * @return
     */
    @Bean
    public CircuitBreakerNameResolver circuitBreakerNameResolver() {
//        return new CircuitBreakerNameResolver() {
//            @Override
//            public String resolveCircuitBreakerName(String feignClientName, Target<?> target, Method method) {
//                return Feign.configKey(target.type(), method);
//            }
//        };
        //below one is the more simplified version of the above one
        return (feignClientName, target, method) -> Feign.configKey(target.type(), method);
    }
}
