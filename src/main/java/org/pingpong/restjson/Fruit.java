package org.pingpong.restjson;

public class Fruit {

    // los propiedades han de ser publicas para que jackson
    // pueda acceder a ellar por reflection
    public String name;
    public String description;

    public Fruit() {
    }

    public Fruit(String name, String description) {
        this.name = name;
        this.description = description;
    }
}
