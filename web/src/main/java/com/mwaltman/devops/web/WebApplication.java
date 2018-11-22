package com.mwaltman.devops.web;

import com.codahale.metrics.health.HealthCheck;
import com.mwaltman.devops.api.HelloApi;
import com.mwaltman.devops.api.delegate.digitalocean.DigitalOceanAccountApi;
import com.mwaltman.devops.api.delegate.digitalocean.DigitalOceanActionApi;
import com.mwaltman.devops.api.delegate.digitalocean.DigitalOceanDropletApi;
import com.mwaltman.devops.framework.util.StringUtils;
import com.mwaltman.devops.framework.AppConfiguration;
import com.mwaltman.devops.framework.MonitorApplication;
import com.mwaltman.devops.framework.health.TemplateHealthCheck;
import io.dropwizard.setup.Environment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.Map;

import static com.mwaltman.devops.framework.util.StringUtils.WS_28_SPACES;

/**
 * The main class for the DropWizard web application.
 */
public class WebApplication extends MonitorApplication {

    /**
     * Logger for web application
     */
    private static final Logger log = LoggerFactory.getLogger(WebApplication.class);

    /**
     * The main method.
     *
     * @param args Arguments supplied at runtime
     *
     * @throws Exception If the application throws an exception
     */
    public static void main(final String[] args) throws Exception {
        WebApplication webApplication = new WebApplication();
        webApplication.run(args);
    }

    @Override
    public void run(AppConfiguration configuration, Environment environment) throws Exception {
        environment.healthChecks().register("template", new TemplateHealthCheck(configuration.getTemplate()));

        environment.jersey().register(new HelloApi(configuration.getTemplate(), configuration.getDefaultName()));

        environment.jersey().register(new DigitalOceanAccountApi(getExternalApi()));
        environment.jersey().register(new DigitalOceanActionApi(getExternalApi()));
        environment.jersey().register(new DigitalOceanDropletApi(getExternalApi()));

        log.info("********** Running Health Checks **********");
        for (Map.Entry<String, HealthCheck.Result> entry : environment.healthChecks().runHealthChecks().entrySet()) {
            String message = entry.getValue().getMessage();

            if (entry.getValue().isHealthy()) {
                log.info("{} : OK{}", entry.getKey(),
                        " - " + ((message != null)
                                ? message
                                : "No message"));
            } else {
                Throwable throwable = entry.getValue().getError();
                OutputStream throwableOutputStream = new ByteArrayOutputStream();

                if (throwable != null) {
                    throwable.printStackTrace(new PrintStream(throwableOutputStream));
                }

                log.error("{} : FAIL{}{}", entry.getKey(),
                        " - " + ((message != null)
                                ? message
                                : "No message"),
                        "\n                          Error:\n" + ((throwable != null)
                                ? StringUtils.prettyException(throwable, WS_28_SPACES)
                                : WS_28_SPACES + "No error provided"));
            }
        }
        log.info("********** Health Checks Done **********");
    }
}
