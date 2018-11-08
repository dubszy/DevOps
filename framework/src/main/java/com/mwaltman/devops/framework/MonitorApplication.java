package com.mwaltman.devops.framework;

import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;

public abstract class MonitorApplication extends Application<AppConfiguration> {

    @Override
    public void initialize(Bootstrap<AppConfiguration> bootstrap) {
        bootstrap.setConfigurationSourceProvider(
                new MultipleFileConfigurationSourceProvider(
                        AppConfiguration.ADDITIONAL_CONFIG_FILE_NAME));
        bootstrap.addBundle(etcd);
    }

    private EtcdBundle<AppConfiguration> etcd = new EtcdBundle<AppConfiguration>() {
        @Override
        public String populateEtcdUrl(AppConfiguration configuration) {
            return configuration.getEtcdUrl();
        }
    };
}
