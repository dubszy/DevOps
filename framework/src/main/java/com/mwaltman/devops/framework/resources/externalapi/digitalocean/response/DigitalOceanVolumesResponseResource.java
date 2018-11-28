package com.mwaltman.devops.framework.resources.externalapi.digitalocean.response;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.mwaltman.devops.framework.resources.externalapi.ApiResponseResource;
import com.mwaltman.devops.framework.resources.externalapi.digitalocean.DigitalOceanLinksResponseResource;
import com.mwaltman.devops.framework.resources.externalapi.digitalocean.DigitalOceanMetaResponseResource;
import lombok.Getter;

import java.util.List;

@Getter
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class DigitalOceanVolumesResponseResource extends ApiResponseResource {

    private List<DigitalOceanVolumeResponseResource> volumes;
    private DigitalOceanLinksResponseResource links;
    private DigitalOceanMetaResponseResource meta;
}
