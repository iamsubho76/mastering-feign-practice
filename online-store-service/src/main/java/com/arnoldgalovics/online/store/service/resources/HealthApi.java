package com.arnoldgalovics.online.store.service.resources;

import com.arnoldgalovics.online.store.service.common.model.ActuatorHealthResponse;
import com.arnoldgalovics.online.store.service.common.model.BaseClient;
import com.arnoldgalovics.online.store.service.common.model.HealthResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class HealthApi {
    private final List<BaseClient> clients;

    @GetMapping("/online-store/health")
    public List<HealthResponse> health() {
        List<HealthResponse> responses = new ArrayList<>();
        for (BaseClient baseClient : clients) {
            ActuatorHealthResponse health = baseClient.health();
            responses.add(new HealthResponse(
                    baseClient.getClass().getInterfaces()[0].getSimpleName(), health.getStatus()));
        }
        return responses;
    }
}
