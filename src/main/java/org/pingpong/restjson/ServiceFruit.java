package org.pingpong.restjson;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class ServiceFruit {

    // @Inject
    // RepoFruit repo;

    public ServiceFruit() { 
        // CDI
    }

    public Set<Fruit> list() {
        // stream requiere una transaction
        Stream<Fruit> fruits = Fruit.streamAll();
        return fruits.collect(Collectors.toSet());
    }

    public void add(Fruit fruit) {
        fruit.persist();
    }

    public void remove(String name) {
        Fruit fruit = Fruit.find("name", name).firstResult();
        fruit.delete();
    }

    public Optional<Fruit> getFruit(String name) {
        return name.isBlank()? 
            Optional.ofNullable(null) : 
            Fruit.find("name", name).firstResultOptional();
    }
}
