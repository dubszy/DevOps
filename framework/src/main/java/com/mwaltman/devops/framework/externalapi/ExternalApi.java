package com.mwaltman.devops.framework.externalapi;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mwaltman.devops.framework.resources.externalapi.ApiResponseResource;
import com.mwaltman.devops.framework.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

import static com.mwaltman.devops.framework.util.StringUtils.WS_26_SPACES;
import static com.mwaltman.devops.framework.util.StringUtils.WS_28_SPACES;

/**
 * Base class for extending to define and communicate with API endpoints for
 * external services.
 */
public abstract class ExternalApi {

    private static final Logger log = LoggerFactory.getLogger(ExternalApi.class);

    /**
     * Deserialize a string of JSON into a class using Jackson.
     *
     * @param clazz The class to deserialize the JSON into
     * @param unwrapRootValue Whether the supplied JSON has a root value that
     *                        must be unwrapped prior to deserialization
     * @param json The JSON to deserialize
     * @param <T> The type of {@code clazz}
     *
     * @return A new instance of type {@code T} instantiated via
     * {@link java.lang.reflect.Constructor#newInstance(Object...) reflection}
     *
     * @exception RuntimeException (As a wrapped {@link IllegalAccessException},
     * {@link InstantiationException}, {@link InvocationTargetException}, or
     * {@link NoSuchMethodException}) if a new instance of type {@code T} could
     * not be created
     *
     * @see ObjectMapper#readerFor(Class) Creating a JSON reader for a class
     * @see com.fasterxml.jackson.databind.ObjectReader#readValue(String) Reading JSON as a string
     * @see ObjectMapper#enable(DeserializationFeature) Enabling deserialization features
     * @see DeserializationFeature#UNWRAP_ROOT_VALUE Unwrap root value deserialization feature
     */
    protected <T extends ApiResponseResource> T deserializeJson(Class<T> clazz, Boolean unwrapRootValue, String json) {
        ObjectMapper objectMapper = new ObjectMapper();
        if (unwrapRootValue) { // Unwrap the root value, if requested
            objectMapper.enable(DeserializationFeature.UNWRAP_ROOT_VALUE);
        }

        try {
            // Create a new reader and read the JSON
            return objectMapper.readerFor(clazz).readValue(json);
        } catch (IOException e) {
            /*
             * Log out the response and pretty-print the exception thrown so
             * we can figure out exactly what went wrong. Additionally, if the
             * response is somewhat large, we don't want to print out the whole
             * response, so we'll gut the middle of it if it's longer than 512
             * characters.
             *
             * TODO: May want to store whole responses somewhere for debugging
             */
            String trimmedJson = json;
            if (json.length() > 512) {
                trimmedJson = WS_28_SPACES
                        + json.substring(0, 255)
                        + "\n"
                        + WS_28_SPACES
                        + "...\n"
                        + WS_28_SPACES
                        + json.substring(json.length() - 256, json.length());
            }
            log.error("Failed to deserialize API response to object.\n{}\n\n{}JSON:\n{}\n\n",
                    StringUtils.prettyException(e, WS_26_SPACES),
                    WS_26_SPACES,
                    trimmedJson);
        }

        try {
            /* This has the possibility to fail if a no-arg constructor is not
             * defined on <T>.
             * TODO: Either a better solution should be found, or this rule needs to be enforced (possibly with compile-time annotations)
             */
            return clazz.getDeclaredConstructor().newInstance();
        } catch (IllegalAccessException |
                InstantiationException |
                InvocationTargetException |
                NoSuchMethodException e) {
            throw new RuntimeException("Failed to create a new instance of "
                    + clazz.toString(), e);
        }
    }
}
