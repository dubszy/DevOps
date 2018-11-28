package com.mwaltman.devops.framework.resources.externalapi.digitalocean.response;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.mwaltman.devops.framework.resources.externalapi.ApiResponseResource;
import lombok.Getter;

import java.util.List;

@Getter
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class DigitalOceanSnapshotsResponseResource extends ApiResponseResource {

    private List<DigitalOceanSnapshotResponseResource> snapshots;
    private DigitalOceanLinksResponseResource links;
    private DigitalOceanMetaResponseResource meta;
}
