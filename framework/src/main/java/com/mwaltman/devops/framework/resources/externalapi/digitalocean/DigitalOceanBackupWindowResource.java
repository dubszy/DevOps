package com.mwaltman.devops.framework.resources.externalapi.digitalocean;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Getter;

@Getter
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class DigitalOceanBackupWindowResource {

    private String start;
    private String end;
}
