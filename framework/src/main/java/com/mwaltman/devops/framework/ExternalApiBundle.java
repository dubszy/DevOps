package com.mwaltman.devops.framework;

import com.mwaltman.devops.framework.externalapi.ApiClient;
import com.mwaltman.devops.framework.externalapi.digitalocean.ExternalDigitalOceanAccountApi;
import com.mwaltman.devops.framework.externalapi.digitalocean.ExternalDigitalOceanDropletApi;
import com.mwaltman.devops.framework.health.DigitalOceanHealthCheck;
import io.dropwizard.Configuration;
import io.dropwizard.ConfiguredBundle;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Bundle for communicating with external APIs.
 *
 * @param <T> The type of the application's configuration
 */
public abstract class ExternalApiBundle<T extends Configuration> implements ConfiguredBundle<T> {

    /**
     * Logger for this class
     */
    private static final Logger log = LoggerFactory.getLogger(ExternalApiBundle.class);

    /**
     * The key for the DigitalOcean health check
     */
    private static final String HEALTH_CHECK_DIGITAL_OCEAN = "digital_ocean";

    /**
     * The API client to use for requests
     */
    @Getter
    private final ApiClient apiClient = new ApiClient();

    /**
     * The DigitalOcean Account API
     */
    @Getter
    @Setter(AccessLevel.PROTECTED)
    private ExternalDigitalOceanAccountApi externalDigitalOceanAccountApi;

    @Getter
    @Setter(AccessLevel.PROTECTED)
    private ExternalDigitalOceanDropletApi externalDigitalOceanDropletApi;

    /**
     * Populate the DigitalOcean API key. Must be overridden by a subclass.
     *
     * @param configuration The application's configuration
     *
     * @return The DigitalOcean API key
     */
    public abstract String populateDigitalOceanApiKey(T configuration);

    @Override
    public void initialize(Bootstrap<?> bootstrap) { }

    @Override
    public void run(T configuration, Environment environment) throws Exception {
        log.info("Running ExternalApiBundle");
        externalDigitalOceanAccountApi = new ExternalDigitalOceanAccountApi(populateDigitalOceanApiKey(configuration), apiClient);
        externalDigitalOceanDropletApi = new ExternalDigitalOceanDropletApi(populateDigitalOceanApiKey(configuration), apiClient);

        environment.healthChecks().register(HEALTH_CHECK_DIGITAL_OCEAN, new DigitalOceanHealthCheck(externalDigitalOceanAccountApi));
    }
}
