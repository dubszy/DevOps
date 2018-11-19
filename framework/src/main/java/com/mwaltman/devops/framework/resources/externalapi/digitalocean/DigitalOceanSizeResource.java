package com.mwaltman.devops.framework.resources.externalapi.digitalocean;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Getter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class DigitalOceanSizeResource {

    private String slug;
    private Boolean available;
    @JsonProperty("transfer")
    private BigDecimal transferBandwidthInTb;
    private BigDecimal priceMonthly; // In USD
    private BigDecimal priceHourly; // In USD
    private Integer memory; // In MB
    private Integer vcpus;
    @JsonProperty("disk")
    private Integer diskSpaceInGb;
    private List<String> regions;
}
