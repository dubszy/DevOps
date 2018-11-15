package com.mwaltman.devops.framework.externalapi;

import lombok.Getter;
import org.apache.http.client.methods.*;

import java.lang.reflect.InvocationTargetException;

public enum RequestType {
    GET(HttpGet.class),
    POST(HttpPost.class),
    PUT(HttpPut.class),
    DELETE(HttpDelete.class);

    @Getter
    Class<? extends HttpRequestBase> requestClass;

    RequestType(Class<? extends HttpRequestBase> requestClass) {
        this.requestClass = requestClass;
    }

    public <T extends HttpRequestBase> T getInstance() {
        try {
            //noinspection unchecked
            return (T) this.getRequestClass().getDeclaredConstructor().newInstance();
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
    }
}
