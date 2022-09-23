package com.arnoldgalovics.online.store.service.common.model;

import org.springframework.web.bind.annotation.GetMapping;

public interface BaseClient {
    @GetMapping("/actuator/health")
    ActuatorHealthResponse health();
}
