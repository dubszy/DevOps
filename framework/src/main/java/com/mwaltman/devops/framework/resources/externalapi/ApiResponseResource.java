package com.mwaltman.devops.framework.resources.externalapi;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.mwaltman.devops.framework.externalapi.HttpResponse;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@EqualsAndHashCode
public class ApiResponseResource {

    @Getter
    @JsonIgnore
    private transient HttpResponse response;

    public <T extends ApiResponseResource> T setResponse(HttpResponse response) {
        this.response = response;
        //noinspection unchecked
        return (T) this;
    }
}
