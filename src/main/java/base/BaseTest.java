package base;

import client.CitiesClient;
import org.testng.annotations.BeforeClass;

public abstract class BaseTest {

    protected CitiesClient citiesClient;
    protected int cityTestId;

    @BeforeClass
    public void setUp() {
        citiesClient = new CitiesClient();
        cityTestId = citiesClient.getCityByNamePrefix("Guildford").jsonPath().getInt("data.id[0]");
    }
}
