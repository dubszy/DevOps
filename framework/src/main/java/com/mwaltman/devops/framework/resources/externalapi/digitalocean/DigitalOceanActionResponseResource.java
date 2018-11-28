package com.mwaltman.devops.framework.resources.externalapi.digitalocean;

import com.fasterxml.jackson.annotation.JsonRootName;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.mwaltman.devops.framework.resources.externalapi.ApiResponseResource;
import lombok.Getter;

/**
 * POJO for deserialization of responses to requests made to the /actions/{id}
 * endpoint in the DigitalOcean API.
 * <br/><br/>
 * Annotated with:
 * <br/>
 * <b>Getter</b>: Auto-generates getters for all fields
 * <br/>
 * <b>JsonNaming(SnakeCaseStrategy)</b>: Instructions Jackson to convert JSON
 * values from {@code snake_case} to {@code camelCase}
 * <br/>
 * <b>JsonRootName</b>: When the {@link DeserializationFeature#UNWRAP_ROOT_VALUE
 * unwrap root value feature} is {@link ObjectMapper#enable(DeserializationFeature)
 * enabled}, the value is the name of the root node to unwrap.
 */
@Getter
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
@JsonRootName("action")
public class DigitalOceanActionResponseResource extends ApiResponseResource {

    private Long id;
    private String status;
    private String type;
    private String startedAt;
    private String completedAt;
    private Long resourceId;
    private String resourceType;
    private DigitalOceanRegionResponseResource region;
    private String regionSlug;
}
