package com.arnoldgalovics.online.store.service.common.filter;

import com.arnoldgalovics.online.store.service.session.UserSessionClient;
import com.arnoldgalovics.online.store.service.session.model.UserSessionValidatorResponse;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@Component
@RequiredArgsConstructor
public class SessionValidationFilter implements Filter {
    private final UserSessionClient userSessionClient;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;

        String sessionIdHeader = httpServletRequest.getHeader("X-Session-Id");
        if (sessionIdHeader == null) {
            httpServletResponse.sendError(HttpStatus.FORBIDDEN.value());
        } else {
            String sleepTime = httpServletRequest.getHeader("X-Sleep");
            Map<String, Object> headerMap = new HashMap<>();
            if(StringUtils.isNotEmpty(sleepTime)){
                headerMap.put("X-Sleep", sleepTime);
            }
            UUID sessionIdUUID = UUID.fromString(sessionIdHeader);
           UserSessionValidatorResponse userSessionValidatorResponse = userSessionClient.validateSession(sessionIdUUID, headerMap);
            try {
                if (!userSessionValidatorResponse.isValid()) {
                    httpServletResponse.sendError(HttpStatus.FORBIDDEN.value());
                } else {
                    chain.doFilter(request, response);
                }
            } catch (Exception e) {
                throw new RuntimeException("Exception while waiting for the future to complete", e);
            }
        }

    }
}
