package com.mwaltman.devops.api.delegate.digitalocean;

import com.mwaltman.devops.framework.ExternalApiBundle;
import com.mwaltman.devops.framework.resources.externalapi.digitalocean.DigitalOceanDropletsResource;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/api/v1/digitalocean/droplets")
@Produces(MediaType.APPLICATION_JSON)
public class DigitalOceanDropletApi {

    private final ExternalApiBundle externalApiBundle;

    public DigitalOceanDropletApi(ExternalApiBundle externalApiBundle) {
        this.externalApiBundle = externalApiBundle;
    }

    @GET
    public DigitalOceanDropletsResource getAllDroplets() {
        return externalApiBundle
                .getExternalDigitalOceanDropletApi()
                .getAllDroplets();
    }
}
