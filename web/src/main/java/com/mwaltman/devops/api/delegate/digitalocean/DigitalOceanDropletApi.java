package com.mwaltman.devops.api.delegate.digitalocean;

import com.mwaltman.devops.framework.ExternalApiBundle;
import com.mwaltman.devops.framework.resources.externalapi.digitalocean.response.DigitalOceanDropletsResource;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 * DigitalOcean Droplet API
 */
@Path("/api/v1/digitalocean/droplets")
@Produces(MediaType.APPLICATION_JSON)
public class DigitalOceanDropletApi {

    private final ExternalApiBundle externalApiBundle;

    public DigitalOceanDropletApi(ExternalApiBundle externalApiBundle) {
        this.externalApiBundle = externalApiBundle;
    }

    /**
     * Get all DigitalOcean Droplets.
     *
     * @return Resource containing all Droplets
     */
    @GET
    public DigitalOceanDropletsResource getAllDroplets() {
        return externalApiBundle
                .getExternalDigitalOceanDropletApi()
                .getAllDroplets();
    }
}
