package org.pingpong.restjson;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.inject.Inject;

import org.assertj.core.api.Assertions;
import org.junit.Test;

import io.quarkus.test.junit.QuarkusTest;

@QuarkusTest
public class ServiceFruitTest {
    
    @Inject
    ServiceFruit service;

    @Test
    public void testList() {
        Assertions.assertThat(service.list()).isEmpty();
    }
    
}
