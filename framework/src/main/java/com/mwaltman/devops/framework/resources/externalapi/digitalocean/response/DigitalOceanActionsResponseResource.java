package com.mwaltman.devops.framework.resources.externalapi.digitalocean.response;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.mwaltman.devops.framework.resources.externalapi.ApiResponseResource;
import com.mwaltman.devops.framework.resources.externalapi.digitalocean.DigitalOceanActionResponseResource;
import com.mwaltman.devops.framework.resources.externalapi.digitalocean.DigitalOceanLinksResponseResource;
import com.mwaltman.devops.framework.resources.externalapi.digitalocean.DigitalOceanMetaResponseResource;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.util.List;

@Getter
@EqualsAndHashCode(callSuper = true)
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class DigitalOceanActionsResponseResource extends ApiResponseResource {

    private List<DigitalOceanActionResponseResource> actions;
    private DigitalOceanLinksResponseResource links;
    private DigitalOceanMetaResponseResource meta;
}
