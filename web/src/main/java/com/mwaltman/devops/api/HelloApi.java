package com.mwaltman.devops.api;

import com.codahale.metrics.annotation.Timed;
import com.mwaltman.devops.resources.HelloResource;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

@Path("/api/v1/hello")
@Produces(MediaType.APPLICATION_JSON)
public class HelloApi {

    private final String template;
    private final String defaultName;
    private final AtomicLong counter;

    public HelloApi(String template, String defaultName) {
        this.template = template;
        this.defaultName = defaultName;
        this.counter = new AtomicLong();
    }

    @SuppressWarnings("OptionalUsedAsFieldOrParameterType")
    @GET
    @Timed
    public HelloResource sayHello(@QueryParam("name") Optional<String> name) {
        final String value = String.format(template, name.orElse(defaultName));
        return new HelloResource(counter.incrementAndGet(), value);
    }
}
