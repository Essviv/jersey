package com.cmcc.syw;

import com.google.gson.Gson;

import java.net.URI;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.inject.Singleton;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Cookie;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

/**
 * 人
 *
 * Created by sunyiwei on 2016/11/21.
 */
@Path("persons")
@Singleton
public class Person {
    private final Gson gson = new Gson();
    private List<Person> persons = new LinkedList<>();
    private String name;
    private int age;
    private Gender gender;
    private String message;

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String get(@QueryParam("name") String name) {
        for (Person person : persons) {
            if (person.getName().equals(name)) {
                return gson.toJson(person);
            }
        }

        return "Not Found";
    }

    @DELETE
    public Response delete(@QueryParam("name") String name) {
        Iterator<Person> iter = persons.iterator();
        while (iter.hasNext()) {
            Person person = iter.next();
            if (person.getName().equals(name)) {
                iter.remove();
                return Response.ok().build();
            }
        }

        return Response.status(Response.Status.NOT_FOUND).build();
    }

    @PUT
    public Response put(@QueryParam("name") String name, @QueryParam("age") int age) {
        Person person = new Person();
        person.setName(name);
        person.setAge(age);
        person.setGender(Gender.MALE);

        persons.add(person);

        return Response.created(URI.create("/persons/" + name)).build();
    }

    @GET
    @Path("uriContext")
    public String uriContext(@Context UriInfo uriInfo, @Context HttpHeaders headers) {
        StringBuilder stringBuilder = new StringBuilder();

        Map<String, Cookie> cookies = headers.getCookies();
        for (String s : cookies.keySet()) {
            stringBuilder.append("Cookie[name = " + s + "] = " + cookies.get(s));
            stringBuilder.append(System.lineSeparator());
        }

        MultivaluedMap<String, String> headers1 = headers.getRequestHeaders();
        for (String s : headers1.keySet()) {
            stringBuilder.append("Header[name = " + s + "] = " + String.join(",", headers1.get(s)));
            stringBuilder.append(System.lineSeparator());
        }

        return stringBuilder.toString();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public enum Gender {
        MALE, FEMALE
    }
}
