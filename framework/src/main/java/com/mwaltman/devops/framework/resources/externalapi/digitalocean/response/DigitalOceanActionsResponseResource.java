package com.mwaltman.devops.framework.resources.externalapi.digitalocean.response;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.mwaltman.devops.framework.resources.externalapi.ApiResponseResource;
import com.mwaltman.devops.framework.resources.externalapi.digitalocean.DigitalOceanActionResource;
import com.mwaltman.devops.framework.resources.externalapi.digitalocean.DigitalOceanLinksResource;
import com.mwaltman.devops.framework.resources.externalapi.digitalocean.DigitalOceanMetaResource;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.util.List;

@Getter
@EqualsAndHashCode(callSuper = true)
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class DigitalOceanActionsResponseResource extends ApiResponseResource {

    private List<DigitalOceanActionResource> actions;
    private DigitalOceanLinksResource links;
    private DigitalOceanMetaResource meta;
}
