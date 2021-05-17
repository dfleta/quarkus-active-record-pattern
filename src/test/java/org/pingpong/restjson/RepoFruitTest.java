package org.pingpong.restjson;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import javax.inject.Inject;

import org.assertj.core.api.Assertions;
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

    @Test
    public void initTest() {
        assertEquals(2, repo.list().size());
        Assertions.assertThat(2).isEqualTo(repo.list().size());
    }

    @Test
    public void containsTest() {
        assertTrue(repo.list().stream().anyMatch(f -> f.name.equals("Pineapple")));
    }

    @Test
    public void removeTest() {
        repo.remove("Apple");
        assertEquals(1, repo.list().size());
        assertFalse(repo.list().stream().anyMatch(f -> f.name.equals("Apple")));
    }

    @Test
    public void addTest() {
        repo.add(new Fruit("Banana", "And an attached Gorilla"));
        assertEquals(3, repo.list().size());
        assertTrue(repo.list().stream().anyMatch(f -> f.name.equals("Banana")));
    }
    
}
