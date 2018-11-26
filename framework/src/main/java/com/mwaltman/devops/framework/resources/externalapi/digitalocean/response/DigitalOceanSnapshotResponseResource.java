package com.mwaltman.devops.framework.resources.externalapi.digitalocean.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.mwaltman.devops.framework.resources.externalapi.ApiResponseResource;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
@JsonRootName("snapshot")
public class DigitalOceanSnapshotResponseResource extends ApiResponseResource {

    @JsonProperty("id")
    private String uuid;

    private String name;

    private String createdAt; // ISO8601 datetime

    @JsonProperty("regions")
    private List<String> regionSlugs;

    @JsonProperty("resource_id")
    private String resourceUuid;

    private String resourceType;

    @JsonProperty("min_disk_size")
    private Integer minDiskSizeInGb;

    private Integer sizeGigabytes;
}
