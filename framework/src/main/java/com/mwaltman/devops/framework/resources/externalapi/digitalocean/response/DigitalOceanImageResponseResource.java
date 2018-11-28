package com.mwaltman.devops.framework.resources.externalapi.digitalocean.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Getter;

import java.util.List;

@Getter
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class DigitalOceanImageResponseResource {

    private Long id;
    private String name;
    private String type;
    private String distribution;
    private String slug; // nullable
    @JsonProperty("public")
    private Boolean isPublic;
    private List<String> regions;
    private String createdAt;
    private Integer minDiskSize; // In GB
    private Integer sizeGigabytes;
    private String description;
    private List<String> tags;
    private String status;
    private String errorMessage;
}
