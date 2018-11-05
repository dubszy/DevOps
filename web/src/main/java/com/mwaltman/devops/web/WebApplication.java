package com.mwaltman.devops.web;

import com.mwaltman.devops.api.HelloApi;
import com.mwaltman.devops.framework.AppConfiguration;
import com.mwaltman.devops.framework.MonitorApplication;
import com.mwaltman.devops.framework.health.TemplateHealthCheck;
import io.dropwizard.setup.Environment;

public class WebApplication extends MonitorApplication {

    public static void main(final String[] args) throws Exception {
        WebApplication webApplication = new WebApplication();
        webApplication.run(args);
    }

    @Override
    public void run(AppConfiguration configuration, Environment environment) throws Exception {
        environment.healthChecks().register("template",
                new TemplateHealthCheck(configuration.getTemplate()));

        environment.jersey().register(
                new HelloApi(configuration.getTemplate(),
                        configuration.getDefaultName()));
    }
}
