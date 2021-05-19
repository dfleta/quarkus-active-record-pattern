package org.pingpong.restjson;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class ActiveRecord {

    public Set<Fruit> list() {
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

    public Optional<Fruit> get(String name) {
        return Fruit.find("name", name).firstResultOptional();
    }    
}
