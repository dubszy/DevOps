package com.mwaltman.devops.framework;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.validator.constraints.NotEmpty;

/**
 * POJO for storing the ETCD configuration. This object is auto-populated when
 * the application starts. The purpose of this class is to prevent key names
 * from being committed to version control.
 */
public class EtcdConfiguration {

    /**
     * The URL of the ETCD server
     */
    @NotEmpty
    @JsonProperty
    private String url;

    /**
     * The names of the keys in ETCD
     */
    @NotEmpty
    @JsonProperty
    private EtcdKeyNames keyNames;

    /**
     * Get the URL of the ETCD server.
     *
     * @return The URL of the ETCD server as a string
     */
    @JsonProperty
    public String getUrl() {
        return url;
    }

    /**
     * Set the URL of the ETCD server.
     *
     * @param url The URL of the ETCD server as a string
     */
    @JsonProperty
    public void setUrl(String url) {
        this.url = url;
    }

    /**
     * Get the names of the keys in ETCD.
     *
     * @return The {@link EtcdKeyNames key names} in ETCD
     */
    @JsonProperty
    public EtcdKeyNames getKeyNames() {
        return keyNames;
    }

    /**
     * Set the names of the keys in ETCD.
     *
     * @param keyNames The {@link EtcdKeyNames key names} in ETCD
     */
    @JsonProperty
    public void setKeyNames(EtcdKeyNames keyNames) {
        this.keyNames = keyNames;
    }
}
