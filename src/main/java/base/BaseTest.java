package base;

import client.CitiesClient;
import org.testng.annotations.BeforeClass;

public abstract class BaseTest {

    protected static CitiesClient citiesClient;

    @BeforeClass
    public void setUp() {
        citiesClient = new CitiesClient();
    }
}
