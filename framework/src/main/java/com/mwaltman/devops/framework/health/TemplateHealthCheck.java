package com.mwaltman.devops.framework.health;

import com.codahale.metrics.health.HealthCheck;

public class TemplateHealthCheck extends HealthCheck {

    private final String template;

    public TemplateHealthCheck(String template) {
        this.template = template;
    }

    @Override
    protected Result check() throws Exception {
        final String test = String.format(template, "test");
        if (!test.contains("test")) {
            return Result.unhealthy("'template' doesn't include a name");
        }
        return Result.healthy();
    }
}
