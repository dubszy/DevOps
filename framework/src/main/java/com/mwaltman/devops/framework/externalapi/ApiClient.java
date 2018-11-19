package com.mwaltman.devops.framework.externalapi;

import lombok.Getter;
import org.apache.http.StatusLine;
import org.apache.http.annotation.Contract;
import org.apache.http.annotation.ThreadingBehavior;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Closeable;
import java.io.IOException;

/**
 * Client for making HTTP requests and providing responses and cookies.
 *
 * This class is thread-safe.
 */
@Contract(threading = ThreadingBehavior.SAFE)
public final class ApiClient implements Closeable {

    private static final Logger log = LoggerFactory.getLogger(ApiClient.class);

    private final HttpClientContext context = HttpClientContext.create();

    @Getter
    private final CookieMonster cookieStore = new CookieMonster();

    private final CloseableHttpClient httpClient = HttpClients.createDefault();

    public ApiClient() {
        context.setCookieStore(cookieStore);
    }

    /**
     * Send a HTTP request.
     *
     * @param request Pre-built request to send
     *
     * @return HTTP response as a {@link HttpResponse}
     *
     * @exception RuntimeException (As a wrapped {@link IOException}) if the
     * {@link CloseableHttpClient} fails to execute the request, or if
     * {@link EntityUtils} fails to consume the request entity.
     */
    public HttpResponse call(final HttpRequestBase request) {
        log.debug("[API] Call to {} {} {}", request.getMethod(), request.getURI(), request.getAllHeaders());

        /* We need to modify this while locked on httpClient inside the try
         * block and consume the response entity in the finally block, so don't
         * make this a try-with-resources block.
         */
        CloseableHttpResponse rawResponse = null;

        try {
            /* Lock on httpClient to prevent other calls from being made and to
             * prevent it from being closed while we're making a call.
             */
            synchronized (httpClient) {
                rawResponse = httpClient.execute(request, context);
            }
            StatusLine statusLine = rawResponse.getStatusLine();

            return new HttpResponse(
                    statusLine.getStatusCode(),
                    statusLine.getReasonPhrase(),
                    rawResponse.getAllHeaders(),
                   EntityUtils.toString(rawResponse.getEntity()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            if (rawResponse != null) {
                try {
                    EntityUtils.consume(rawResponse.getEntity());
                    rawResponse.close();
                } catch (IOException e) {
                    /* Don't throw here because we don't want to mask exceptions
                     * that could be thrown in the above try block.
                     */
                    log.error("Failed to close stream.\n{}", e);
                }
            }
        }
    }

    @Override
    public void close() throws IOException {
        try {
            /* Lock on httpClient to prevent requests from being made while
             * we're closing (to prevent abandoning open streams).
             */
            synchronized (httpClient) {
                httpClient.close();
            }
        } catch (IOException e) {
            throw new RuntimeException("Failed to close the HTTP client", e);
        }
    }
}
