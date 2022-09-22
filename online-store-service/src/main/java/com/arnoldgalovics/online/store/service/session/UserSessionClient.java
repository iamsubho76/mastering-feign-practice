package com.arnoldgalovics.online.store.service.session;

import com.arnoldgalovics.online.store.service.common.model.BaseClient;
import com.arnoldgalovics.online.store.service.session.model.UserSessionValidatorResponse;
import com.arnoldgalovics.online.store.service.session.model.ValidateSessionRequest;
import feign.HeaderMap;
import feign.QueryMap;
import feign.RequestLine;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

public interface UserSessionClient extends BaseClient {
    @RequestLine("GET /user-sessions/validate")
    CompletableFuture<UserSessionValidatorResponse> validateSession(@QueryMap ValidateSessionRequest validateSessionRequest,
                                                                    @HeaderMap Map<String, Object> headerMap);

    default CompletableFuture<UserSessionValidatorResponse> validateSession(UUID sessionId) {
        Map<String, Object> headerMap = new HashMap<>();
        return validateSession(new ValidateSessionRequest(sessionId.toString()), headerMap);
    }

}
