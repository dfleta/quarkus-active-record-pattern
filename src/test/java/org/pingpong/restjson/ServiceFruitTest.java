package org.pingpong.restjson;

import javax.inject.Inject;
import javax.transaction.Transactional;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import io.quarkus.test.junit.QuarkusTest;

/**
 * Component Unit testing
 */

@QuarkusTest
@Transactional
public class ServiceFruitTest {

    @Inject
    ServiceFruit service;

    // @Test de jupiter, no el de junit
    @Test
    public void testList() {
        Assertions.assertThat(service.list()).hasSize(2);
    }

    @Test
    public void containsTest() {
        Assertions.assertThat(service.list().stream().anyMatch(f -> f.getName().equals("Apple"))).isTrue();
    }
    
    @Test
    public void addTest() {
        service.add(new Fruit("Banana", "And an attached Gorilla"));
        Assertions.assertThat(service.list()).hasSize(3);
        Assertions.assertThat(service.list().stream().anyMatch(f -> f.getName().equals("Banana"))).isTrue();

        // handmade rollback gracias al antipatron ActiveRecord ;)
        Fruit fruit = Fruit.find("name", "Banana").firstResult();
        fruit.delete();
        Assertions.assertThat(Fruit.count()).isEqualTo(2);
    }
    @Test
    public void removeTest(){
        service.remove("Apple");
        Assertions.assertThat(service.list()).hasSize(1);
        Assertions.assertThat(service.list().stream().anyMatch(f -> f.getName().equals("Apple"))).isFalse();

        // handmade rollback gracias al antipatron ActiveRecord ;)
        Fruit.persist(new Fruit("Apple", "Winter fruit"));
        Assertions.assertThat(Fruit.count()).isEqualTo(2);
    }

    @Test
    public void getFruitTest() {
        Assertions.assertThat(service.getFruit("Apple")).get().hasFieldOrPropertyWithValue("name", "Apple").hasFieldOrPropertyWithValue("description", "Winter fruit");
        Assertions.assertThat(service.getFruit("Mandarina")).isEmpty();
    }    
}
