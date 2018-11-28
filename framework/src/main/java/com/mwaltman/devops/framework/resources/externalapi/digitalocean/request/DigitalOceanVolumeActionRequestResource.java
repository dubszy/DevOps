package com.mwaltman.devops.framework.resources.externalapi.digitalocean.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.mwaltman.devops.framework.resources.externalapi.ApiRequestResource;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Request resource representing an action to perform on a DigitalOcean block
 * storage volume. Currently, this is for resizing, attaching, or detaching a
 * volume.
 */
@Data
@EqualsAndHashCode(callSuper = true)
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class DigitalOceanVolumeActionRequestResource extends ApiRequestResource {

    /**
     * Type of actions that can be performed.
     */
    public enum VolumeActionType {

        ATTACH("attach"),
        DETACH("detach"),
        RESIZE("resize");

        private String jsonValue;

        VolumeActionType(String jsonValue) {
            this.jsonValue = jsonValue;
        }

        public String getJsonValue() {
            return jsonValue;
        }
    }

    /**
     * Type of action to perform. This should be populated with one of the
     * {@link VolumeActionType#getJsonValue() JSON value} of a
     * {@link VolumeActionType}.
     */
    @JsonProperty(required = true)
    private String type;

    /**
     * Region (slug) where the volume is located
     */
    @JsonProperty("region")
    private String regionSlug;
}
