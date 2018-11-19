package com.mwaltman.devops.framework.resources.externalapi.digitalocean;


import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.mwaltman.devops.framework.resources.externalapi.ApiResponseResource;
import lombok.Getter;

import java.util.List;

/**
 * POJO for deserialization of responses to GET requests made to the /droplets
 * endpoint in the DigitalOcean API.
 */
@Getter
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class DigitalOceanDropletsResource extends ApiResponseResource {

    private List<DigitalOceanDropletResource> droplets;
    private DigitalOceanLinksResource links;
    private DigitalOceanMetaResource meta;
}
