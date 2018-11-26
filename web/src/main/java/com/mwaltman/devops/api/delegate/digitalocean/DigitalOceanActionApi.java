package com.mwaltman.devops.api.delegate.digitalocean;

import com.mwaltman.devops.framework.ExternalApiBundle;
import com.mwaltman.devops.framework.resources.externalapi.digitalocean.DigitalOceanActionResource;
import com.mwaltman.devops.framework.resources.externalapi.digitalocean.DigitalOceanActionsResource;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 * DigitalOcean Action API
 */
@Path("api/v1/digitalocean/actions")
@Produces(MediaType.APPLICATION_JSON)
public class DigitalOceanActionApi {

    private final ExternalApiBundle externalApiBundle;

    public DigitalOceanActionApi(ExternalApiBundle externalApiBundle) {
        this.externalApiBundle = externalApiBundle;
    }

    /**
     * Get all DigitalOcean Actions.
     *
     * @return Resource containing all Actions
     */
    @GET
    public DigitalOceanActionsResource getAllActions() {
        return externalApiBundle
                .getExternalDigitalOceanActionApi()
                .getAllActions();
    }

    /**
     * Get a specific DigitalOcean Action.
     *
     * @param id ID of the action
     * @return Resource representing the Action
     */
    @GET
    @Path("/{id}")
    public DigitalOceanActionResource getAction(@PathParam("id") String id) {
        return externalApiBundle
                .getExternalDigitalOceanActionApi()
                .getAction(id);
    }
}
