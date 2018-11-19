package com.mwaltman.devops.framework.externalapi;

import lombok.Getter;
import org.apache.http.client.methods.*;

import java.lang.reflect.InvocationTargetException;

/**
 * Types of requests that can be made to external services.
 *
 * @see RequestFactory Building requests
 * @see ApiClient#call(HttpRequestBase) Executing requests
 * @see ExternalApi Defining endpoints for external services
 */
public enum RequestType {

    /**
     * GET request
     */
    GET(HttpGet.class),

    /**
     * POST request
     */
    POST(HttpPost.class),

    /**
     * PUT request
     */
    PUT(HttpPut.class),

    /**
     * DELETE request
     */
    DELETE(HttpDelete.class);

    @Getter
    Class<? extends HttpRequestBase> requestClass;

    RequestType(Class<? extends HttpRequestBase> requestClass) {
        this.requestClass = requestClass;
    }

    /**
     * Get a new instance of the class associated with this request type.
     *
     * @param <T> The type of the class
     *
     * @return A new instance of type {@code T} instantiated via
     * {@link java.lang.reflect.Constructor#newInstance(Object...) reflection}
     *
     * @exception RuntimeException (As a wrapped {@link IllegalAccessException},
     * {@link InstantiationException}, {@link InvocationTargetException}, or
     * {@link NoSuchMethodException} if a new instance of type {@code T} could
     * not be created
     */
    public <T extends HttpRequestBase> T getInstance() {
        try {
            //noinspection unchecked
            return (T) this.getRequestClass().getDeclaredConstructor().newInstance();
        } catch (IllegalAccessException |
                InstantiationException |
                InvocationTargetException |
                NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
    }
}
