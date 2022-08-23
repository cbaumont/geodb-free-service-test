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
        citiesClient.getCityById(cityTestId)
                .then()
                .statusCode(HttpStatus.SC_OK)
                .body("data.id", is(cityTestId))
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
    void shouldFindCityByExistingNamePrefix() {
        String namePrefix = "Guildf";

        citiesClient.getCityByNamePrefix(namePrefix)
                .then()
                .statusCode(HttpStatus.SC_OK)
                .body("data.name", contains("Guildford"))
                .body("data.country", contains("United Kingdom"))
                .body("data.region", contains("England"))
                .body("metadata.totalCount", is(1));
    }

    @Test
    void shouldFindCitiesNearbyGuildfordWithinTenMilesRadius() {
        List<String> citiesExpected = Arrays.asList("Godalming", "Woking", "Waverley");

        List<CityDTO> citiesResult =
                citiesClient
                        .getNearbyCities(cityTestId, 10)
                        .then()
                        .body("metadata.totalCount", is(citiesExpected.size()))
                        .extract().jsonPath()
                        .getList("data", CityDTO.class);

        assertTrue(citiesResult.stream().allMatch(city -> citiesExpected.contains(city.getName())));
    }

    @Test
    void shouldReturnNotFoundForCityWithInvalidId() {
        int invalidId = 0;

        citiesClient.getCityById(invalidId)
                .then()
                .statusCode(HttpStatus.SC_NOT_FOUND);
    }

    @Test
    void shouldNotFindAnyCityWithPopulationBiggerThanOneBillion() {
        long minPopulation = 1000000000;

        citiesClient.getCitiesByMinPopulation(minPopulation)
                .then()
                .statusCode(HttpStatus.SC_OK)
                .body("metadata.totalCount", is(0));
    }

    @Test
    void shouldNotFindAnyCityWithNonexistentNamePrefix() {
        String namePrefix = "West Pineapple";

        citiesClient.getCityByNamePrefix(namePrefix)
                .then()
                .statusCode(HttpStatus.SC_OK)
                .body("metadata.totalCount", is(0));
    }

    @Test
    void shouldReturnErrorWhenNearbyCitiesRadiusIsZero() {
        citiesClient.getNearbyCities(cityTestId, 0)
                .then()
                .statusCode(HttpStatus.SC_BAD_REQUEST)
                .body("errors.code[0]", is("PARAM_INVALID"));
    }
}
