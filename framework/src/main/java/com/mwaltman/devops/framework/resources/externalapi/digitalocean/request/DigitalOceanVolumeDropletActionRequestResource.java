package com.mwaltman.devops.framework.resources.externalapi.digitalocean.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Request resource representing a droplet action to perform on a DigitalOcean
 * block storage volume. Currently, this is only for attaching to or detaching
 * from a droplet.
 */
@Data
@EqualsAndHashCode(callSuper = true)
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class DigitalOceanVolumeDropletActionRequestResource
        extends DigitalOceanVolumeActionRequestResource {

    /**
     * ID of the droplet the action will be related to.
     */
    @JsonProperty(required = true)
    private Long dropletId;
}
