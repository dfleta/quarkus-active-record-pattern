package org.pingpong.restjson;

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
