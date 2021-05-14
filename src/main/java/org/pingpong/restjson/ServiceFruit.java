package org.pingpong.restjson;

import java.util.Set;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

@ApplicationScoped
public class ServiceFruit {

    @Inject
    RepoFruit repo;

    public ServiceFruit() { 
        // CDI
    }

    public Set<Fruit> list() {
        return repo.list();
    }

    public void add(Fruit fruit) {
        repo.add(fruit);
    }

    public void remove(String name) {
        repo.remove(name);
    }
}
