package com.mwaltman.devops.framework;

import com.mwaltman.devops.framework.health.EtcdHealthCheck;
import io.dropwizard.Configuration;
import io.dropwizard.ConfiguredBundle;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import lombok.Getter;
import mousio.etcd4j.EtcdClient;

import java.net.URI;

public abstract class EtcdBundle<T extends Configuration> implements ConfiguredBundle<T> {  

    private static final String HEALTH_CHECK_ETCD = "etcd";

    @Getter
    private String etcdUrl;

    public abstract String populateEtcdUrl(T configuration);

    @Override
    public void run(T configuration, Environment environment) throws Exception {
        etcdUrl = populateEtcdUrl(configuration);

        EtcdClient etcdClient = new EtcdClient(URI.create("http://" + getEtcdUrl() + ":2379"));

        environment.healthChecks().register(HEALTH_CHECK_ETCD, new EtcdHealthCheck(etcdClient));
    }

    @Override
    public void initialize(Bootstrap<?> bootstrap) { }
}
