package com.mwaltman.devops.framework.resources.externalapi.digitalocean.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 *
 * This request is not wrapped in a root value.
 */
@Data
@EqualsAndHashCode
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class DigitalOceanVolumeRequestResource {

    @JsonProperty(required = true)
    private String name; // Only letters, numbers, '-' allowed; 64 char limit

    private String description;

    @JsonProperty(required = true)
    private String region;

    private String snapshotId;

    @JsonProperty(required = true)
    private Integer sizeGigabytes;

    private String filesystemType;

    private String filesystemLabel;
}
