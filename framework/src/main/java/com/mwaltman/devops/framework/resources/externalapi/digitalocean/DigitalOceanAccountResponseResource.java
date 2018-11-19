package com.mwaltman.devops.framework.resources.externalapi.digitalocean;

import com.fasterxml.jackson.annotation.JsonRootName;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.mwaltman.devops.framework.resources.externalapi.ApiResponseResource;
import lombok.Getter;

/**
 * POJO for deserialization of responses to requests made to the /account
 * endpoint in the DigitalOcean API.
 * <br/><br/>
 * Annotated with:
 * <br/>
 * <b>Getter</b>: Auto-generates getters for all fields
 * <br/><br/>
 * <b>JsonNaming(SnakeCaseStrategy)</b>: Instructs Jackson to convert JSON values from
 *      snake_case to camelCase
 * <br/><br/>
 * <b>JsonRootName</b>: When the {@link
 *      com.fasterxml.jackson.databind.DeserializationFeature#UNWRAP_ROOT_VALUE
 *      unwrap root value feature} is
 *      {@link com.fasterxml.jackson.databind.ObjectMapper#enable(
 *              com.fasterxml.jackson.databind.DeserializationFeature) enabled},
 *      the value is the name of the root node to unwrap.
 */
@Getter
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
@JsonRootName("account")
public class DigitalOceanAccountResponseResource extends ApiResponseResource {

    private Integer dropletLimit;
    private Integer floatingIpLimit;
    private Integer volumeLimit;
    private String email;
    private String uuid;
    private Boolean emailVerified;
    private String status;
    private String statusMessage;

    public DigitalOceanAccountResponseResource() { }

    public DigitalOceanAccountResponseResource(Integer dropletLimit,
                                               Integer floatingIpLimit,
                                               Integer volumeLimit,
                                               String email,
                                               String uuid,
                                               Boolean emailVerified,
                                               String status,
                                               String statusMessage) {
        this.dropletLimit = dropletLimit;
        this.floatingIpLimit = floatingIpLimit;
        this.volumeLimit = volumeLimit;
        this.email = email;
        this.uuid = uuid;
        this.emailVerified = emailVerified;
        this.status = status;
        this.statusMessage = statusMessage;
    }
}
