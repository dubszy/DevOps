package com.mwaltman.devops.framework.resources.externalapi.digitalocean;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.mwaltman.devops.framework.resources.externalapi.ApiResponseResource;
import lombok.Getter;

import java.util.List;

/**
 * POJO for deserialization of responses to requests made to the /actions
 * endpoint in the DigitalOcean API.
 * <br/><br/>
 * Annotated with:
 * <br/>
 * <b>Getter</b>: Auto-generates getters for all fields
 * <br/>
 * <b>JsonNaming(SnakeCaseStrategy)</b>: Instructs Jackson to convert JSON
 * values from {@code snake_case} to {@code camelCase}
 * <br/>
 * <b>JsonRootName</b>: When the {@link DeserializationFeature#UNWRAP_ROOT_VALUE
 * unwrap root value feature} is {@link ObjectMapper#enable(DeserializationFeature)
 * enabled}, the value is the name of the root node to unwrap.
 */
@Getter
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class DigitalOceanActionsResponseResource extends ApiResponseResource {

    private List<DigitalOceanActionResponseResource> actions;
    private DigitalOceanLinksResponseResource links;
    private DigitalOceanMetaResponseResource meta;
}
