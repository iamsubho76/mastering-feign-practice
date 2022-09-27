package com.arnoldgalovics.online.store.service.session.fallbackhandler;

import com.arnoldgalovics.online.store.service.common.model.ActuatorHealthResponse;
import com.arnoldgalovics.online.store.service.session.UserSessionClient;
import com.arnoldgalovics.online.store.service.session.model.UserSessionValidatorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@Slf4j
public class UserSessionClientFallbackFactory implements FallbackFactory<UserSessionClient> {
    @Override
    public UserSessionClient create(Throwable cause) {
        return new UserSessionClient() {
            @Override
            public UserSessionValidatorResponse validateSession(Map<String, Object> queryMap, Map<String, Object> headerMap) {
                log.info("[Fallback] validateSession");
                String sessionId = (String) queryMap.get("sessionId");
                return new UserSessionValidatorResponse(false, sessionId);
            }

            @Override
            public ActuatorHealthResponse health() {
                return new ActuatorHealthResponse("DOWN");
            }
        };
    }
}