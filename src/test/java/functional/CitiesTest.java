package functional;

import base.BaseTest;
import dto.CityDTO;
import org.apache.http.HttpStatus;
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.is;
import static org.testng.Assert.assertTrue;

public class CitiesTest extends BaseTest {

    @Test
    void shouldFindCityByValidId() {
        int guildfordId = citiesClient.getCityByName("Guildford").jsonPath().getInt("data.id[0]");

        citiesClient.getCityById(guildfordId)
                .then()
                .statusCode(HttpStatus.SC_OK)
                .body("data.id", is(guildfordId))
                .body("data.name", is("Guildford"))
                .body("data.country", is("United Kingdom"))
                .body("data.region", is("England"));
    }

    @Test
    void shouldFindCitiesWithPopulationBiggerThanTwentyMillion() {
        long minPopulation = 20000000;

        List<String> citiesExpected = Arrays.asList("Beijing", "Chengdu", "Shanghai", "West Punjab", "Kattakurgan District");
        List<CityDTO> citiesResult =
                citiesClient
                        .getCitiesByMinPopulation(minPopulation)
                        .then()
                        .body("metadata.totalCount", is(citiesExpected.size()))
                        .extract().jsonPath()
                        .getList("data", CityDTO.class);

        assertTrue(citiesResult.stream().allMatch(city -> citiesExpected.contains(city.getName())));
    }

    @Test
    void shouldFindCityByExistingName() {
        String name = "Guildford";

        citiesClient.getCityByName(name)
                .then()
                .statusCode(HttpStatus.SC_OK)
                .body("data.name", contains("Guildford"))
                .body("data.country", contains("United Kingdom"))
                .body("data.region", contains("England"))
                .body("metadata.totalCount", is(1));
    }

    @Test
    void shouldFindCitiesNearbyGuildford() {
        int guildfordId = citiesClient.getCityByName("Guildford").jsonPath().getInt("data.id[0]");

        List<String> citiesExpected = Arrays.asList("Godalming", "Woking", "Waverley");

        List<CityDTO> citiesResult =
                citiesClient
                        .getNearbyCities(guildfordId, 10)
                        .then()
                        .body("metadata.totalCount", is(citiesExpected.size()))
                        .extract().jsonPath()
                        .getList("data", CityDTO.class);

        assertTrue(citiesResult.stream().allMatch(city -> citiesExpected.contains(city.getName())));
    }
}
