package com.mwaltman.devops.framework.externalapi;

import org.apache.http.Header;
import org.apache.http.client.methods.HttpEntityEnclosingRequestBase;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.entity.StringEntity;

import java.net.URI;
import java.util.stream.Stream;

public abstract class RequestFactory {

    public abstract Header[] getHeaders();

    public abstract String getBaseUri();

    public <T extends HttpRequestBase> T build(RequestType requestType, String relativeUri) {
        return build(requestType, relativeUri, null, null, null);
    }

    public <T extends HttpRequestBase> T build(RequestType requestType, String relativeUri, Header[] headers) {
        return build(requestType, relativeUri, headers, null, null);
    }

    public <T extends HttpRequestBase> T build(RequestType requestType,
                                               String relativeUri,
                                               Header[] headers,
                                               String content,
                                               String contentType) {
        T request = requestType.getInstance();
        request.setURI(URI.create(getBaseUri() + relativeUri));
        request.setHeaders(
                Stream.of(getHeaders(), headers)
                        .flatMap(Stream::of)
                        .toArray(Header[]::new));

        if (request instanceof HttpEntityEnclosingRequestBase &&
                content != null &&
                contentType != null) {
            StringEntity entity = new StringEntity(content, "UTF-8");
            entity.setContentType(contentType);
            ((HttpEntityEnclosingRequestBase) request).setEntity(entity);
        }
        return request;
    }
}
