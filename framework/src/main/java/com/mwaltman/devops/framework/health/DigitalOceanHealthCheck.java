package com.mwaltman.devops.framework.health;

import com.codahale.metrics.health.HealthCheck;
import com.mwaltman.devops.framework.externalapi.HttpResponse;
import com.mwaltman.devops.framework.externalapi.digitalocean.ExternalDigitalOceanAccountApi;

import static com.mwaltman.devops.framework.util.StringUtils.WS_26_SPACES;

/**
 * Health check to assert that communication with the DigitalOcean API works
 * properly.
 */
public class DigitalOceanHealthCheck extends HealthCheck {

    private final ExternalDigitalOceanAccountApi externalDigitalOceanAccountApi;

    public DigitalOceanHealthCheck(ExternalDigitalOceanAccountApi externalDigitalOceanAccountApi) {
        this.externalDigitalOceanAccountApi = externalDigitalOceanAccountApi;
    }

    @Override
    protected Result check() throws Exception {
        HttpResponse response = externalDigitalOceanAccountApi.getAccountInformation().getResponse();
        if (response.getStatusCode() == 200) {
            return Result.healthy();
        }
        return Result.unhealthy(
                "Response not OK from DigitalOcean.\n%s",
                response.prettyToString(WS_26_SPACES));
    }
}
