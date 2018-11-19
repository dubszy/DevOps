package com.mwaltman.devops.framework;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.dropwizard.Configuration;
import org.hibernate.validator.constraints.NotEmpty;

/**
 * The application's configuration.
 */
public class AppConfiguration extends Configuration {

    /**
     * The additional config file to tack onto the end of 'app_config.yml'
     */
    static final transient String ADDITIONAL_CONFIG_FILE_NAME = "sensitive_config.yml";

    /**
     * The greeting template for {@code HelloApi}
     */
    @NotEmpty
    private String template;

    /**
     * The default name for the {@code HelloApi}
     */
    @NotEmpty
    private String defaultName = "Stranger";

    /**
     * The ETCD configuration
     */
    private EtcdConfiguration etcdConfiguration;

    /**
     * Get the template for {@code HelloApi}
     *
     * @return The template
     */
    @JsonProperty
    public String getTemplate() {
        return template;
    }

    /**
     * Set the template for {@code HelloApi}
     *
     * @param template The template
     */
    @JsonProperty
    public void setTemplate(String template) {
        this.template = template;
    }

    /**
     * Get the default name for {@code HelloApi}
     *
     * @return The default name
     */
    @JsonProperty
    public String getDefaultName() {
        return defaultName;
    }

    /**
     * Set the default name for {@code HelloApi}
     *
     * @param defaultName The default name
     */
    @JsonProperty
    public void setDefaultName(String defaultName) {
        this.defaultName = defaultName;
    }

    /**
     * Get the ETCD configuration.
     *
     * @return The ETCD configuration
     */
    @JsonProperty
    public EtcdConfiguration getEtcdConfiguration() {
        return etcdConfiguration;
    }

    /**
     * Set the ETCD configuration.
     *
     * @param etcdConfiguration The ETCD configuration
     */
    @JsonProperty
    public void setEtcdConfiguration(EtcdConfiguration etcdConfiguration) {
        this.etcdConfiguration = etcdConfiguration;
    }
}
