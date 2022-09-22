package com.arnoldgalovics.online.store.service.common.model;

import feign.RequestLine;

public interface BaseClient {
    @RequestLine("GET /actuator/health")
    ActuatorHealthResponse health();
}
