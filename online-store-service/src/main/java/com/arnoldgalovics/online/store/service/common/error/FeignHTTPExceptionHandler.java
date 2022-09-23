package com.arnoldgalovics.online.store.service.common.error;

import feign.Response;

public interface FeignHTTPExceptionHandler {
    Exception handle(Response response);
}
