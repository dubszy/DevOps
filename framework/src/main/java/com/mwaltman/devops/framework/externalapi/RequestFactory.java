package com.mwaltman.devops.framework.externalapi;

import org.apache.http.Header;
import org.apache.http.client.methods.HttpEntityEnclosingRequestBase;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.entity.StringEntity;

import java.net.URI;
import java.util.stream.Stream;

/**
 * Factory for building {@link HttpRequestBase HTTP requests} which can be
 * {@link ApiClient#call(HttpRequestBase) executed}. Designed to be overridden
 * by a specific request factory for each external service's API.
 */
public abstract class RequestFactory {

    /**
     * Get common headers for all API calls to this external service. Must be
     * overridden by a subclass.
     *
     * @return An array of {@link Header headers} to build into each request
     * built by this factory.
     */
    public abstract Header[] getHeaders();

    /**
     * Get the base URI for all API calls to this external service. Must be
     * overridden by a subclass.
     *
     * @return A base URI (as a string) to prepend to the URI for each request
     * built by this factory.
     */
    public abstract String getBaseUri();

    /**
     * Build a request with a request type and a relative URI.
     *
     * @param requestType The type of request to be built
     * @param relativeUri The URI to append to {@link #getBaseUri()}
     * @param <T> The type of the class that will be constructed (determined by
     *           the class parameter of the {@code requestType})
     *
     * @return A request ready to be {@link ApiClient#call(HttpRequestBase)
     * executed}
     *
     * @see RequestType Request types
     * @see #build(RequestType, String, Header[]) Build a request with headers
     * @see #build(RequestType, String, String, String) Build a request with content and no headers
     * @see #build(RequestType, String, Header[], String, String) Build a request with headers and content
     */
    public <T extends HttpRequestBase> T build(RequestType requestType, String relativeUri) {
        return build(requestType, relativeUri, null, null, null);
    }

    /**
     * Build a request with a request type, relative URI, and headers.
     *
     * @param requestType The type of request to be built
     * @param relativeUri The URI to append to {@link #getBaseUri()}
     * @param headers The headers (in addition to the {@link #getHeaders()
     *           common headers}) to be built into this request
     * @param <T> The type of the class that will be constructed (determined by
     *           the class parameter of the {@code requestType}
     *
     * @return A request ready to be {@link ApiClient#call(HttpRequestBase)
     * executed}
     *
     * @see RequestType Request types
     * @see #build(RequestType, String) Build a request without headers
     * @see #build(RequestType, String, String, String) Build a request with content and no headers
     * @see #build(RequestType, String, Header[], String, String) Build a request with headers and content
     */
    public <T extends HttpRequestBase> T build(RequestType requestType, String relativeUri, Header[] headers) {
        return build(requestType, relativeUri, headers, null, null);
    }

    /**
     * Build a request with a request type, relative URI, and content.
     *
     * @param requestType Type of request to be built
     * @param relativeUri URI to append to {@link #getBaseUri()}
     * @param content Content (body) of the request
     * @param contentType MIME type of {@code content}
     * @param <T> Type of class that will be constructed (determined by the
     *           class parameter of {@code requestType})
     *
     * @return Request ready to be {@link ApiClient#call(HttpRequestBase)
     * executed}
     *
     * @see RequestType Request types
     * @see #build(RequestType, String) Build a request without headers or content
     * @see #build(RequestType, String, Header[]) Build a request with headers and no content
     * @see #build(RequestType, String, Header[], String, String) Build a request with headers and content
     */
    public <T extends HttpRequestBase> T build(RequestType requestType,
                                               String relativeUri,
                                               String content,
                                               String contentType) {
        return build(requestType, relativeUri, null, content, contentType);
    }

    /**
     * Build a request with a request type, relative URI, headers, and content.
     *
     * @param requestType The type of request to be built
     * @param relativeUri The URI to append to {@link #getBaseUri()}
     * @param headers The headers (in addition to the {@link #getHeaders()
     *           common headers}) to be built into this request
     * @param content The content (body) of the request
     * @param contentType The type of {@code content}
     * @param <T> The type of the class that will be constructed (determined by
     *           the class parameter of the {@code requestType}
     *
     * @return A request ready to be {@link ApiClient#call(HttpRequestBase)
     * executed}
     *
     * @see RequestType Request types
     * @see #build(RequestType, String) Build a request with just type and URI
     * @see #build(RequestType, String, String, String) Build a request with content and no headers
     * @see #build(RequestType, String, Header[]) Build a request with headers and no content
     */
    public <T extends HttpRequestBase> T build(RequestType requestType,
                                               String relativeUri,
                                               Header[] headers,
                                               String content,
                                               String contentType) {
        T request = requestType.getInstance();
        request.setURI(URI.create(getBaseUri() + relativeUri));

        // Stream all the headers together
        Header[] commonHeaders = getHeaders();
        request.setHeaders(
                // FIXME: Stream doesn't like null, so this is a quick fix
                Stream.of(((commonHeaders != null) ? commonHeaders : new Header[]{}),
                        ((headers != null) ? headers : new Header[]{}))
                        .flatMap(Stream::of)
                        .toArray(Header[]::new));

        /* Make sure we can actually set content:
         * - Content must not be null
         * - Content type must not be null
         * - HttpRequestBase doesn't have #setEntity, only
         *      HttpEntityEnclosingRequestBase and its subclasses do
         */
        if (content != null &&
                contentType != null &&
                request instanceof HttpEntityEnclosingRequestBase) {
            StringEntity entity = new StringEntity(content, "UTF-8");
            entity.setContentType(contentType);
            ((HttpEntityEnclosingRequestBase) request).setEntity(entity);
        }
        return request;
    }
}
