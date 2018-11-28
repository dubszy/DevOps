package com.mwaltman.devops.framework.resources.externalapi.digitalocean.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.mwaltman.devops.framework.resources.externalapi.ApiResponseResource;
import com.mwaltman.devops.framework.resources.externalapi.digitalocean.DigitalOceanRegionResponseResource;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
@JsonRootName("volume")
public class DigitalOceanVolumeResponseResource extends ApiResponseResource {

    @JsonProperty("id")
    private String uuid;

    @JsonProperty(required = true)
    private String name; // Only letters, numbers, '-' allowed; 64 char limit

    private String description; // Free-form

    private List<Long> dropletIds;

    private DigitalOceanRegionResponseResource region;

    private String createdAt; // ISO8601 datetime

    @JsonProperty(required = true)
    private Integer sizeGigabytes;

    private String filesystemType;

    private String filesystemLabel;
}
