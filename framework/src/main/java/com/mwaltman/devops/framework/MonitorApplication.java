package com.mwaltman.devops.framework;

import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import lombok.Getter;

/**
 * Base class for the web application and worker application.
 */
public abstract class MonitorApplication extends Application<AppConfiguration> {

    @Override
    public void initialize(Bootstrap<AppConfiguration> bootstrap) {
        bootstrap.setConfigurationSourceProvider(
                new MultipleFileConfigurationSourceProvider(
                        AppConfiguration.ADDITIONAL_CONFIG_FILE_NAME));
        bootstrap.addBundle(etcd);
        bootstrap.addBundle(externalApi);
    }

    /**
     * Bundle for communicating with ETCD
     */
    @Getter
    private EtcdBundle<AppConfiguration> etcd = new EtcdBundle<AppConfiguration>() {
        @Override
        public String populateEtcdUrl(AppConfiguration configuration) {
            return configuration.getEtcdConfiguration().getUrl();
        }
    };

    /**
     * Bundle for communicating with external APIs
     */
    @Getter
    private ExternalApiBundle<AppConfiguration> externalApi = new ExternalApiBundle<AppConfiguration>() {
        @Override
        public String populateDigitalOceanApiKey(AppConfiguration configuration) {
            return etcd.getDigitalOceanRwKey();
        }
    };
}
