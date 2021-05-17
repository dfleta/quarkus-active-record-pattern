package org.pingpong.restjson;

import java.util.Set;

import javax.inject.Inject;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/fruits")
public class ResourceFruit {

    @Inject
    ServiceFruit service;
    
    public ResourceFruit() {
        // CDI
    }

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String hello() {
        return "Colmados Farmer Rick";
    }

    @GET
    @Path("/list")
    // no es necesario Produces ya que por defecto
    // resteasy jackson desactiva la negociación
    // y sirve MediaType.APPLICATION_JSON
    public Set<Fruit> list() {
        return service.list();
    }

    @POST
    // $ curl -d '{"name":"Banana", "description":"Brings a Gorilla too"}'
    // -H "Content-Type: application/json" -X POST http://localhost:8080/fruits
    public Set<Fruit> add(Fruit fruit) {
        service.add(fruit);
        return this.list();
    }

    @DELETE
    // $ curl -d '{"name":"Banana", "description":"Brings a Gorilla too"}'
    // -H "Content-Type: application/json" -X DELETE http://localhost:8080/fruits   
    public Set<Fruit> delete(Fruit fruit) {
        service.remove(fruit.name);
        return list();
    }

}