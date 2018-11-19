package com.mwaltman.devops.framework.externalapi.digitalocean;

import com.mwaltman.devops.framework.externalapi.ApiClient;
import com.mwaltman.devops.framework.externalapi.DigitalOceanRequestFactory;
import com.mwaltman.devops.framework.externalapi.ExternalApi;
import com.mwaltman.devops.framework.externalapi.HttpResponse;
import com.mwaltman.devops.framework.resources.externalapi.digitalocean.DigitalOceanAccountResource;

import static com.mwaltman.devops.framework.externalapi.RequestType.GET;

/**
 * Defines and communicates with API endpoints for DigitalOcean.
 */
public class ExternalDigitalOceanAccountApi extends ExternalApi {

    /**
     * The factory to use to build requests for this API.
     */
    private final DigitalOceanRequestFactory requestFactory;

    /**
     * The API client to use when executing requests for this API.
     */
    private final ApiClient apiClient;

    /**
     * Construct a new ExternalDigitalOceanAccountApi with an auth token and an API
     * client.
     *
     * @param authToken The auth token to use to authenticate all requests to
     *                  the DigitalOcean API
     * @param apiClient The API client to use to execute all requests to the
     *                  DigitalOcean API
     */
    public ExternalDigitalOceanAccountApi(String authToken, ApiClient apiClient) {
        this.requestFactory = new DigitalOceanRequestFactory(authToken);
        this.apiClient = apiClient;
    }

    /**
     * Get the account information for the authenticated user.
     *
     * @return The account information deserialized into a
     * {@link DigitalOceanAccountResource}
     */
    public DigitalOceanAccountResource getAccountInformation() {
        HttpResponse response = apiClient.call(
                requestFactory.build(GET, "account"));
        return deserializeJson(DigitalOceanAccountResource.class,
                true,
                response.getContent()).setResponse(response);
    }
}
