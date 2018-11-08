package com.mwaltman.devops.framework.health;

import com.codahale.metrics.health.HealthCheck;
import mousio.etcd4j.EtcdClient;

import java.util.Date;

public class EtcdHealthCheck extends HealthCheck {

    private static final String HEALTH_KEY = "dwhealthcheck";

    private final EtcdClient etcdClient;

    public EtcdHealthCheck(EtcdClient etcdClient) {
        this.etcdClient = etcdClient;
    }

    @Override
    protected Result check() throws Exception {
        String now = new Date().toString();
        /* It's possible for an IOException to be thrown here, but exceptions
         * thrown inside this method are automatically caught and turned into
         * unhealthy results with the full stack trace.
         */
        etcdClient.put(HEALTH_KEY, now).send().get();
        String responseNow = etcdClient.get(HEALTH_KEY).send().get().node.value;
        if (now.equals(responseNow)) {
            return Result.healthy("Correct value received from etcd");
        }

        return Result.unhealthy("Incorrect value from etcd. Expected %s | Received %s", now, responseNow);
    }
}
