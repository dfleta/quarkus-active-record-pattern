package org.pingpong.restjson;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

public class Fruit {

    // los propiedades han de ser publicas para que jackson
    // pueda acceder a ellar por reflection
    @NotBlank
    public String name;
    @NotEmpty
    public String description;

    public Fruit() {
    }

    public Fruit(String name, String description) {
        this.name = name;
        this.description = description;
    }
}
