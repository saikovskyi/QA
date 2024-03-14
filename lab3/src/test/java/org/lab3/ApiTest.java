package org.lab3;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.jupiter.api.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ApiTest {

    private static String token;

    @BeforeAll
    public static void authTest(){
        Response response = RestAssured.given()
                .baseUri("https://airportgap.com/api")
                .params("email", "igorxlonline24@gmail.com", "password", "12345678")
                .when()
                .post("/tokens");
        response.then().statusCode(200);
        token = response.jsonPath().getString("token");
    }

    @Order(2)
    @Test
    public void testGetAirports() {
        RestAssured.given()
                .baseUri("https://airportgap.com/api")
                .when()
                .get("/airports")
                .then()
                .statusCode(200);
    }

    @Order(3)
    @Test
    public void testGetAirportsById() {
        RestAssured.given()
                .baseUri("https://airportgap.com/api")
                .params("id", "KIX")
                .when()
                .get("/airports")
                .then()
                .statusCode(200);
    }

    @Order(4)
    @Test
    public void testAirportDistance() {
        RestAssured.given()
                .baseUri("https://airportgap.com/api")
                .params("from", "KIX", "to", "HND")
                .when()
                .post("/airports/distance")
                .then()
                .statusCode(200);
    }

    @Order(5)
    @Test
    public void testClearAllFavorites() {
        RestAssured.given()
                .baseUri("https://airportgap.com/api")
                .header("Authorization", "Bearer " + token)
                .when()
                .delete("/favorites/clear_all")
                .then()
                .statusCode(204);
    }

    @Order(6)
    @Test
    public void testAddFavorite() {
        RestAssured.given()
                .baseUri("https://airportgap.com/api")
                .header("Authorization", "Bearer " + token)
                .params("airport_id", "KIX")
                .when()
                .post("/favorites")
                .then()
                .statusCode(201);
    }

    @Order(7)
    @Test
    public void testGetFavorites() {
        RestAssured.given()
                .baseUri("https://airportgap.com/api")
                .header("Authorization", "Bearer " + token)
                .when()
                .get("/favorites")
                .then()
                .statusCode(200)
                .extract().response().jsonPath().getString("data[0].attributes.airport.iata").equals("KIX");
    }

    @Order(8)
    @Test
    public void testGetFavoritesById() {
        RestAssured.given()
                .baseUri("https://airportgap.com/api")
                .header("Authorization", "Bearer " + token)
                .params("id", "KIX")
                .when()
                .get("/favorites")
                .then()
                .statusCode(200);
    }

    @Order(9)
    @Test
    public void testUnauthorized() {
        RestAssured.given()
                .baseUri("https://airportgap.com/api")
                .when()
                .get("/favorites")
                .then()
                .statusCode(401);
    }
}
