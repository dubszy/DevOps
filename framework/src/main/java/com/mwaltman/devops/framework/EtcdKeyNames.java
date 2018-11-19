package com.mwaltman.devops.framework;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.validator.constraints.NotEmpty;

/**
 * POJO for storing the key names set in ETCD. This object is auto-populated
 * when the application starts.
 */
public class EtcdKeyNames {

    /**
     * The name of the DigitalOcean RW API key
     */
    @NotEmpty
    @JsonProperty
    private String digitalOceanRwApiKeyName;

    /**
     * Get the DigitalOcean RW API key name
     *
     * @return The key name as a string
     */
    @JsonProperty
    public String getDigitalOceanRwApiKeyName() {
        return digitalOceanRwApiKeyName;
    }

    /**
     * Set the DigitalOcean RW API key name
     *
     * @param digitalOceanRwApiKeyName The key name as a string
     */
    @JsonProperty
    public void setDigitalOceanRwApiKeyName(String digitalOceanRwApiKeyName) {
        this.digitalOceanRwApiKeyName = digitalOceanRwApiKeyName;
    }
}
