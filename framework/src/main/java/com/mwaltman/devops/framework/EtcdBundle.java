package com.mwaltman.devops.framework;

import com.mwaltman.devops.core.util.Property;
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

    /* So as to not expose the key names in etcd, load all the key values from
     * system properties.
     */
    private static final String KEY_DIGITAL_OCEAN_RW =
            Property.ETCD_KEYNAME_DIGITAL_OCEAN_RW.getValueOrExcept();

    @Getter
    private String digitalOceanRwKey;

    @Getter
    private String etcdUrl;

    @Getter
    private EtcdClient etcdClient;

    public abstract String populateEtcdUrl(T configuration);

    @Override
    public void run(T configuration, Environment environment) throws Exception {
        etcdUrl = populateEtcdUrl(configuration);

        etcdClient = new EtcdClient(URI.create("http://" + getEtcdUrl() + ":2379"));

        digitalOceanRwKey = etcdClient.get(KEY_DIGITAL_OCEAN_RW).send().get().node.value;

        environment.healthChecks().register(HEALTH_CHECK_ETCD, new EtcdHealthCheck(etcdClient));
    }

    @Override
    public void initialize(Bootstrap<?> bootstrap) { }
}
