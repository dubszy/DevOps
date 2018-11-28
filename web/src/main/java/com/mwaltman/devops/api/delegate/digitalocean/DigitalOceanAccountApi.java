package com.mwaltman.devops.api.delegate.digitalocean;

import com.mwaltman.devops.framework.ExternalApiBundle;
import com.mwaltman.devops.framework.resources.externalapi.digitalocean.response.DigitalOceanAccountResponseResource;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 * DigitalOcean Account API
 */
@Path("/api/v1/digitalocean/account")
@Produces(MediaType.APPLICATION_JSON)
public class DigitalOceanAccountApi {

    private final ExternalApiBundle externalApiBundle;

    public DigitalOceanAccountApi(ExternalApiBundle externalApiBundle) {
        this.externalApiBundle = externalApiBundle;
    }

    /**
     * Get DigitalOcean Account information.
     *
     * @return Resource representing the Account
     */
    @GET
    public DigitalOceanAccountResponseResource getAccountInformation() {
        return externalApiBundle
                .getExternalDigitalOceanAccountApi()
                .getAccountInformation();
    }
}
