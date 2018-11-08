package com.mwaltman.devops.framework;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.dropwizard.Configuration;
import org.hibernate.validator.constraints.NotEmpty;

public class AppConfiguration extends Configuration {

    protected static final transient String ADDITIONAL_CONFIG_FILE_NAME = "sensitive_config.yml";

    @NotEmpty
    private String template;

    @NotEmpty
    private String defaultName = "Stranger";

    @NotEmpty
    private String etcdUrl;

    @JsonProperty
    public String getTemplate() {
        return template;
    }

    @JsonProperty
    public void setTemplate(String template) {
        this.template = template;
    }

    @JsonProperty
    public String getDefaultName() {
        return defaultName;
    }

    @JsonProperty
    public void setDefaultName(String defaultName) {
        this.defaultName = defaultName;
    }

    @JsonProperty
    public String getEtcdUrl() {
        return etcdUrl;
    }

    @JsonProperty
    public void setEtcdUrl(String etcdUrl) {
        this.etcdUrl = etcdUrl;
    }
}
