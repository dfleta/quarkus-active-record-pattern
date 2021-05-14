package org.pingpong.restjson;

import io.quarkus.test.TestTransaction;
import io.quarkus.test.junit.QuarkusTest;

import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.containsInAnyOrder;

import javax.ws.rs.core.MediaType;


@QuarkusTest
public class FruitResourceTest {

    @Test
    public void testHelloEndpoint() {
        given()
            .when().get("/fruits")
            .then()
                .statusCode(200)
                .body(is("Colmados Farmer Rick"));
    }

    @Test
    @TestTransaction
    public void testList() {
        given()
            .when().get("/fruits/list")
            .then()
                .statusCode(200)
                .body("$.size()", is(2),
                "name", containsInAnyOrder("Apple", "Pineapple"),
                "description", containsInAnyOrder("Winter fruit", "Tropical fruit"));
    }

    @Test
    public void testAdd() {
        given()
            .body("{\"name\": \"Banana\", \"description\": \"Brings a Gorilla too\"}")
                .header("Content-Type", MediaType.APPLICATION_JSON)
                .when()
                    .post("/fruits")
                    .then()
                        .statusCode(200)
                        .body("$.size()", is(3),
                              "name", containsInAnyOrder("Apple", "Pineapple", "Banana"),
                              "description", containsInAnyOrder("Winter fruit", "Tropical fruit", "Brings a Gorilla too"));
        
        given()
            .body("{\"name\": \"Banana\", \"description\": \"Brings a Gorilla too\"}")
                .header("Content-Type", MediaType.APPLICATION_JSON)
                .when()
                    .delete("/fruits")
                    .then()
                        .statusCode(200)
                        .body("$.size()", is(2),
                              "name", containsInAnyOrder("Apple", "Pineapple"),
                              "description", containsInAnyOrder("Winter fruit", "Tropical fruit"));
    }
}