package com.mwaltman.devops.framework.resources.externalapi.digitalocean.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class DigitalOceanVolumeResizeActionRequestResource
        extends DigitalOceanVolumeActionRequestResource {

    @JsonProperty(required = true)
    private Integer sizeGigabytes;
}
