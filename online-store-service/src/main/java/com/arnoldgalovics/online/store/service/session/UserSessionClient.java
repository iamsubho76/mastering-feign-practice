package com.arnoldgalovics.online.store.service.session;

import com.arnoldgalovics.online.store.service.common.model.BaseClient;
import com.arnoldgalovics.online.store.service.session.fallbackhandler.UserSessionClientFallbackFactory;
import com.arnoldgalovics.online.store.service.session.model.UserSessionValidatorResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

//The URL is not required as the logical name 'user-session-service' is registered in Eureka which is sufficient to find the service
@FeignClient(name = "user-session-service", fallbackFactory = UserSessionClientFallbackFactory.class)
public interface UserSessionClient extends BaseClient {
    @GetMapping("/user-sessions/validate")
    UserSessionValidatorResponse validateSession(@RequestParam Map<String, Object> queryMap,
                                                 @RequestHeader Map<String, Object> headerMap);

    default UserSessionValidatorResponse validateSession(UUID sessionId, Map<String, Object> headerMap) {
        Map<String, Object> queryMap = new HashMap<>();
        queryMap.put("sessionId", sessionId.toString());
        return validateSession(queryMap, headerMap);
    }
}
