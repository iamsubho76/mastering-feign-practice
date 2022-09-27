package com.arnoldgalovics.user.session.service.resources;

import com.arnoldgalovics.user.session.service.model.UserSessionValidatorResponse;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@Log4j2
public class UserSessionController {
    @Value("${server.port}")
    private String serverPort;

    @GetMapping("/user-sessions/validate")
    public ResponseEntity<?> validate(@RequestParam("sessionId") UUID sessionId) {
        return ResponseEntity.status(HttpStatus.MOVED_PERMANENTLY)
                .header("Location", "http://localhost:" + serverPort + "/user-sessions/v2/validate?sessionId=" + sessionId.toString())
                .build();
    }

    @GetMapping("/user-sessions/v2/validate")
    public UserSessionValidatorResponse validateV2(@RequestParam("sessionId") UUID sessionId, @RequestHeader(value = "X-Sleep", defaultValue = "0") String sleepTime) throws InterruptedException {
        log.info("User Session Called For Port Number : {}", serverPort);
        Thread.sleep(Long.parseLong(sleepTime));
        boolean isValid = UUID.fromString("ad8614c1-d3e9-4b62-971a-1e7b19345fcb").equals(sessionId);
        return UserSessionValidatorResponse.builder()
                .sessionId(sessionId)
                .valid(isValid)
                .build();
    }
}
