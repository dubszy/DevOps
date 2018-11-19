package com.mwaltman.devops.framework.externalapi;

import org.apache.http.Header;
import org.apache.http.message.BasicHeader;

/**
 * Factory for building {@link org.apache.http.client.methods.HttpRequestBase
 * HTTP requests} for the DigitalOcean API.
 */
public class DigitalOceanRequestFactory extends RequestFactory {

    /**
     * The auth token to supply in a header to all requests
     *
     * @see <a href="https://developers.digitalocean.com/documentation/v2/#authentication">
     *     Authenticating with DigitalOcean</a>
     */
    private final String authToken;

    public DigitalOceanRequestFactory(String authToken) {
        this.authToken = authToken;
    }

    /**
     * {@inheritDoc}
     *
     * @return An array with a single header for authorization
     */
    @Override
    public Header[] getHeaders() {
        return new Header[] {
                new BasicHeader("Authorization", "Bearer " + authToken)
        };
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getBaseUri() {
        return "https://api.digitalocean.com/v2/";
    }
}
