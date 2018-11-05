package com.mwaltman.devops.framework;

import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;

public abstract class MonitorApplication extends Application<AppConfiguration> {

    @Override
    public void initialize(Bootstrap<AppConfiguration> bootstrap) {
        super.initialize(bootstrap);
    }
}
