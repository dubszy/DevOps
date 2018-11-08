package com.mwaltman.devops.web;

import com.codahale.metrics.health.HealthCheck;
import com.mwaltman.devops.api.HelloApi;
import com.mwaltman.devops.framework.AppConfiguration;
import com.mwaltman.devops.framework.MonitorApplication;
import com.mwaltman.devops.framework.health.TemplateHealthCheck;
import io.dropwizard.setup.Environment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

public class WebApplication extends MonitorApplication {

    private static final Logger log = LoggerFactory.getLogger(WebApplication.class);

    public static void main(final String[] args) throws Exception {
        WebApplication webApplication = new WebApplication();
        webApplication.run(args);
    }

    @Override
    public void run(AppConfiguration configuration, Environment environment) throws Exception {
        environment.healthChecks().register("template", new TemplateHealthCheck(configuration.getTemplate()));

        environment.jersey().register(
                new HelloApi(configuration.getTemplate(),
                        configuration.getDefaultName()));

        for (Map.Entry<String, HealthCheck.Result> entry : environment.healthChecks().runHealthChecks().entrySet()) {
            if (entry.getValue().isHealthy()) {
                log.info("{} : OK", entry.getKey());
            } else {
                log.error("{} : FAIL", entry.getKey());
            }
        }
    }
}
