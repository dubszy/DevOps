package com.mwaltman.devops.framework.resources.externalapi.digitalocean;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Getter;

import java.util.List;

@Getter
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class DigitalOceanDropletResponseResource {

    private Long id;
    private String name;
    private Integer memory;
    private Integer vcpus;
    @JsonProperty("disk")
    private Integer diskSizeInGb;
    private Boolean locked;
    private String createdAt;
    private String status;
    private List<Long> backupIds;
    private List<Long> snapshotIds;
    private List<String> features;
    private DigitalOceanRegionResponseResource region;
    private DigitalOceanImageResponseResource image;
    private DigitalOceanSizeResponseResource size;
    private String sizeSlug;
    private DigitalOceanNetworksResource networks;
    private DigitalOceanKernelResponseResource kernel;
    private DigitalOceanBackupWindowResponseResource nextBackupWindow;
    private List<String> tags;
    @JsonProperty("volume_ids")
    private List<String> volumeUuids;
}
