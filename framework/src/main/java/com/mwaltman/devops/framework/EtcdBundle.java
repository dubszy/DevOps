package com.mwaltman.devops.framework;

import com.mwaltman.devops.framework.health.EtcdHealthCheck;
import io.dropwizard.Configuration;
import io.dropwizard.ConfiguredBundle;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import lombok.Getter;
import mousio.etcd4j.EtcdClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URI;

/**
 * Bundle for communicating with ETCD.
 *
 * @param <T> The type of the application's configuration
 */
public abstract class EtcdBundle<T extends Configuration> implements ConfiguredBundle<T> {

    /**
     * Logger for the ETCD bundle
     */
    private static final Logger log = LoggerFactory.getLogger(EtcdBundle.class);

    /**
     * Health check key for ETCD
     */
    private static final String HEALTH_CHECK_ETCD = "etcd";

    /**
     * The RW API key for DigitalOcean
     */
    @Getter
    private String digitalOceanRwKey;

    /**
     * The URL of the ETCD server
     */
    @Getter
    private String etcdUrl;

    /**
     * The client used to communicate with ETCD
     */
    @Getter
    private EtcdClient etcdClient;

    /**
     * Populate the ETCD URL. Must be overridden in a sublcass.
     *
     * @param configuration The application's configuration
     *
     * @return The ETCD URL
     */
    public abstract String populateEtcdUrl(T configuration);

    @Override
    public void run(T configuration, Environment environment) throws Exception {
        log.info("Running EtcdBundle");
        etcdUrl = populateEtcdUrl(configuration);

        etcdClient = new EtcdClient(URI.create("http://" + getEtcdUrl() + ":2379"));

        digitalOceanRwKey = etcdClient
                .get(((AppConfiguration) configuration)
                        .getEtcdConfiguration()
                        .getKeyNames()
                        .getDigitalOceanRwApiKeyName())
                .send()
                .get()
                .node
                .value;

        environment.healthChecks().register(HEALTH_CHECK_ETCD, new EtcdHealthCheck(etcdClient));
    }

    @Override
    public void initialize(Bootstrap<?> bootstrap) { }
}
