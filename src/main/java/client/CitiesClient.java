package client;

import io.restassured.response.Response;

import static io.restassured.RestAssured.given;


public class CitiesClient extends DefaultClient {

    public CitiesClient() {
        super("cities");
    }

    public Response getCityById(int id) {
        return given()
                .spec(serviceSpecification)
                .pathParam("id", id)
                .get("/{id}");
    }

    public Response getCitiesByMinPopulation(long minPopulation) {
        return given()
                .spec(serviceSpecification)
                .queryParam("minPopulation", minPopulation)
                .get();
    }

    public Response getCityByNamePrefix(String namePrefix) {
        return given()
                .spec(serviceSpecification)
                .queryParam("namePrefix", namePrefix)
                .get();
    }

    public Response getNearbyCities(int id, int radius) {
        return given()
                .spec(serviceSpecification)
                .queryParam("radius", radius)
                .get("/{id}/nearbyCities", id);
    }

}
