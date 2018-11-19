package com.mwaltman.devops.framework.externalapi.digitalocean;

import com.mwaltman.devops.framework.externalapi.ApiClient;
import com.mwaltman.devops.framework.externalapi.DigitalOceanRequestFactory;
import com.mwaltman.devops.framework.externalapi.ExternalApi;
import com.mwaltman.devops.framework.externalapi.HttpResponse;
import com.mwaltman.devops.framework.resources.externalapi.digitalocean.DigitalOceanDropletsResource;

import static com.mwaltman.devops.framework.externalapi.RequestType.GET;

public class ExternalDigitalOceanDropletApi extends ExternalApi {

    private final DigitalOceanRequestFactory requestFactory;

    private final ApiClient apiClient;

    public ExternalDigitalOceanDropletApi(String authToken, ApiClient apiClient) {
        this.requestFactory = new DigitalOceanRequestFactory(authToken);
        this.apiClient = apiClient;
    }

    public DigitalOceanDropletsResource getAllDroplets() {
        HttpResponse response = apiClient.call(
                requestFactory.build(GET, "droplets"));
        return deserializeJson(DigitalOceanDropletsResource.class,
                false,
                response.getContent()).setResponse(response);
    }
}
