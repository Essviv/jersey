package com.cmcc.syw;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

/**
 * Root resource (exposed at "myresource" path)
 */
@Path("myresource")
public class MyResource {

    /**
     * Method handling HTTP GET requests. The returned object will be sent to the client as
     * "text/plain" media type.
     *
     * @return String that will be returned as a text/plain response.
     */
    @GET
    @Path("string")
    @Produces(MediaType.TEXT_PLAIN)
    public String getString(@QueryParam("name") String name) {
        return "Hello, " + name;
    }

    @GET
    @Path("path/{name}/{age}")
    @Produces(MediaType.TEXT_PLAIN)
    public String getSubPath(@PathParam("name") String name, @PathParam("age") int age) {
        return name + ": " + age;
    }


}
