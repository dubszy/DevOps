package com.mwaltman.devops.framework.resources.externalapi.digitalocean;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Getter;

import java.util.List;

@Getter
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class DigitalOceanDropletResource {

    private Integer id;
    private String name;
    private Integer memory;
    private Integer vcpus;
    @JsonProperty("disk")
    private Integer diskSizeInGb;
    private Boolean locked;
    private String createdAt;
    private String status;
    private List<Integer> backupIds;
    private List<Integer> snapshotIds;
    private List<String> features;
    private DigitalOceanRegionResource region;
    private DigitalOceanImageResource image;
    private DigitalOceanSizeResource size;
    private String sizeSlug;
    private DigitalOceanNetworksResource networks;
    private DigitalOceanKernelResource kernel;
    private DigitalOceanBackupWindowResource nextBackupWindow;
    private List<String> tags;
    @JsonProperty("volume_ids")
    private List<String> volumeUuids;
}
