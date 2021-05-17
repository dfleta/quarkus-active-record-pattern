package org.pingpong.restjson;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import javax.inject.Inject;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import io.quarkus.test.junit.QuarkusTest;

/**
 * Component Unit testing
 */

@QuarkusTest
public class RepoFruitTest {

    @Inject
    RepoFruit repo;
    
    @BeforeEach
    public void setup() {
        repo.init();
    }

    // sino, no pasa los rest-test de resource desde mvn test
    @AfterEach
    public void teardown() {
        repo.init();
    }

    @Test
    public void initTest() {
        assertEquals(2, repo.list().size());
        Assertions.assertThat(repo.list().size()).isEqualTo(2);
    }

    @Test
    public void containsTest() {
        assertTrue(repo.list().stream().anyMatch(f -> f.getName().equals("Pineapple")));
    }

    @Test
    public void removeTest() {
        repo.remove("Apple");
        assertEquals(1, repo.list().size());
        assertFalse(repo.list().stream().anyMatch(f -> f.getName().equals("Apple")));
    }

    @Test
    public void addTest() {
        repo.add(new Fruit("Banana", "And an attached Gorilla"));
        assertEquals(3, repo.list().size());
        assertTrue(repo.list().stream().anyMatch(f -> f.getName().equals("Banana")));
    }

    @Test
    public void getFruitTest() {
        Assertions.assertThat(repo.get("Apple")).get().hasFieldOrPropertyWithValue("name", "Apple");
        Assertions.assertThat(repo.get("Mandarina")).isEmpty();
    }    
}
