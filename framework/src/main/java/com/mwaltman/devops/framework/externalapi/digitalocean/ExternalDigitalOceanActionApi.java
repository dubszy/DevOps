package com.mwaltman.devops.framework.externalapi.digitalocean;

import com.mwaltman.devops.framework.externalapi.ApiClient;
import com.mwaltman.devops.framework.externalapi.DigitalOceanRequestFactory;
import com.mwaltman.devops.framework.externalapi.ExternalApi;
import com.mwaltman.devops.framework.externalapi.HttpResponse;
import com.mwaltman.devops.framework.resources.externalapi.digitalocean.DigitalOceanActionResponseResource;
import com.mwaltman.devops.framework.resources.externalapi.digitalocean.response.DigitalOceanActionsResponseResource;

import static com.mwaltman.devops.framework.externalapi.RequestType.GET;

/**
 * Defines and communicates with API endpoints for the DigitalOcean Action API.
 * This class handles the building and execution of requests to for this API, it
 * is designed to be used via {@code DigitalOceanActionApi} in the {@code web}
 * module.
 */
public class ExternalDigitalOceanActionApi extends ExternalApi {

    /**
     * Factory to use to build requests for this API.
     */
    private final DigitalOceanRequestFactory requestFactory;

    /**
     * API client to use when executing requests for this API.
     */
    private final ApiClient apiClient;

    public ExternalDigitalOceanActionApi(String authToken, ApiClient apiClient) {
        this.requestFactory = new DigitalOceanRequestFactory(authToken);
        this.apiClient = apiClient;
    }

    /**
     * Get all DigitalOcean Actions.
     *
     * @return Resource containing all Actions
     */
    public DigitalOceanActionsResponseResource getAllActions() {
        HttpResponse response = apiClient.call(
                requestFactory.build(GET, "actions"));
        return deserializeJson(DigitalOceanActionsResponseResource.class,
                false,
                response.getContent()).setResponse(response);
    }

    /**
     * Get a specific DigitalOcean Action.
     *
     * @param id ID of the Action
     *
     * @return Resource representing the Action
     */
    public DigitalOceanActionResponseResource getAction(String id) {
        HttpResponse response = apiClient.call(
                requestFactory.build(GET, "actions/" + id));
        return deserializeJson(DigitalOceanActionResponseResource.class,
                true,
                response.getContent()).setResponse(response);
    }
}
