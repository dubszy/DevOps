package com.mwaltman.devops.framework.resources.externalapi.digitalocean.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Request resource representing an action to perform on a DigitalOcean block
 * storage volume located by the name of the volume. Currently, this is only
 * for attaching to or detaching from a droplet.
 */
@Data
@EqualsAndHashCode(callSuper = true)
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class DigitalOceanVolumeByNameDropletActionRequestResource
        extends DigitalOceanVolumeDropletActionRequestResource {

    /**
     * Name of the volume the action will be performed on
     */
    @JsonProperty(required = true)
    private String volumeName;
}
