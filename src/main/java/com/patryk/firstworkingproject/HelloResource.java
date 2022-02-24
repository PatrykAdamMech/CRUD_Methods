package com.patryk.firstworkingproject;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;

@Path("/hello-world")
public class HelloResource {
    @GET
    @Produces("text/plain")
    public String hello(@QueryParam("name") String name) {
        return "Hello, "+name;
    }
    @GET
    @Produces("text/plain")
    public String hello() {
        return "Hello, world";
    }
}