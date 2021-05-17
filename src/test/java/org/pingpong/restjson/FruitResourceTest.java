package org.pingpong.restjson;

import io.quarkus.test.TestTransaction;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.common.mapper.TypeRef;
import io.restassured.http.ContentType;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.containsInAnyOrder;
// importado a mano equalTo
import static org.hamcrest.Matchers.equalTo;

import java.util.List;
import java.util.Map;

import javax.ws.rs.core.MediaType;


/**
 * Behavioral testing
 */

@QuarkusTest
public class FruitResourceTest {

    /**
     * give
     * expect
     * when
     * es la sintaxis legacy REST assured 1.x
     */

    @Test
    public void testHelloEndpoint() {
        // si el content-type de la peticion es TEXT
        // responde el endpoint hello
        given()
            .contentType(ContentType.TEXT)
            .when().get("/fruits")
            .then()
                .statusCode(200)
                .body(is("Colmados Farmer Rick"));
    }

    @Test
    public void testListEndpoint() {
        // Si el content-type de la peticion es JSON 
        // responde el endpoint list
        // list() endpoint devuelve lista de maps [{}, {}]
        List<Map<String, Object>> products = 
            given()
                .contentType(ContentType.JSON)
                .when().get("/fruits")
                .as(new TypeRef<List<Map<String, Object>>>() {});

        Assertions.assertThat(products).hasSize(2);
        Assertions.assertThat(products.get(0)).containsValue("Apple");
        Assertions.assertThat(products.get(1)).containsEntry("description", "Tropical fruit");
    }

    @Test
    @TestTransaction
    public void testList() {
        given()
            .contentType(ContentType.JSON)
            .when().get("/fruits/")
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

    @Test
    public void getTest() {
        given()
            .pathParam("name", "Apple")
        .when()
            .get("/fruits/{name}")
        .then()
            .contentType(ContentType.JSON)
            .body("name", equalTo("Apple"));

        // no fruit
        given()
            .pathParam("name", "Mandarina")
        .when()
            .get("/fruits/{name}")
        .then()
            .statusCode(404);
    }
}