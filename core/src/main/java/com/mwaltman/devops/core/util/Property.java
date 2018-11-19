package com.mwaltman.devops.core.util;

import com.mwaltman.devops.core.exceptions.SystemPropertyException;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public enum Property {

    ETCD_KEYNAME_DIGITAL_OCEAN_RW("etcd.keyname.digitalocean.rw");



    private String key;

    Property(String key) {
        this.key = key;
    }

    public String getKey() {
        return key;
    }

    public String getValueOrExcept() throws SystemPropertyException {
        return getValue()
                .orElseThrow(() -> new SystemPropertyException(
                        "Property '" + key + "' was not set"));
    }

    public Optional<String> getValue() {
        return Optional.ofNullable(System.getProperty(key));
    }

    public static Map<Property, String> getAllProperties() {
        Map<Property, String> properties = new HashMap<>();
        for (Property aProperty : values()) {
            //noinspection ConstantConditions
            properties.put(aProperty, aProperty.getValue().get());
        }
        return properties;
    }
}
