package org.pingpong.restjson;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Optional;
import java.util.Set;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class RepoFruit {

    private Set<Fruit> fruits = Collections.newSetFromMap(Collections.synchronizedMap(new LinkedHashMap<>()));

    public RepoFruit() {
        // CDI
    }

    @PostConstruct
    public void init() {
        fruits.clear();
        fruits.add(new Fruit("Apple", "Winter fruit"));
        fruits.add(new Fruit("Pineapple", "Tropical fruit"));
    }

    public Set<Fruit> list() {
        return this.fruits;
    }

    public void add(Fruit fruit) {
        this.fruits.add(fruit);
    }

    public void remove(String name) {
        this.fruits.removeIf(existingFruit -> existingFruit.getName().contentEquals(name));
    }

    public Optional<Fruit> get(String name) {
        return this.fruits.stream().filter(f -> f.getName().equalsIgnoreCase(name)).findFirst();
    }
}
