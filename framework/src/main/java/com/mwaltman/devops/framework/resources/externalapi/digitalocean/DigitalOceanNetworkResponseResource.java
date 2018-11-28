package com.mwaltman.devops.framework.resources.externalapi.digitalocean;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Getter;

/**
 * Represents a network object returned by the DigitalOcean API. Can represent
 * either an IPv4 or an IPv6 network object.
 */
@Getter
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class DigitalOceanNetworkResponseResource {

    private String ipAddress;
    private String netmask;
    private String gateway;
    private String type;
}
