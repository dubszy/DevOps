package com.mwaltman.devops.framework.resources.externalapi;

import com.mwaltman.devops.framework.externalapi.HttpResponse;
import lombok.Getter;

public class ApiResponseResource {

    @Getter
    private HttpResponse response;

    public <T extends ApiResponseResource> T setResponse(HttpResponse response) {
        this.response = response;
        //noinspection unchecked
        return (T) this;
    }
}
