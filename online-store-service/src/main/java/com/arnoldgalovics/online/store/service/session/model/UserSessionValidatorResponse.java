package com.arnoldgalovics.online.store.service.session.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserSessionValidatorResponse {
    private boolean valid;
    private String sessionId;
}
