package specification;

import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import utils.PropertiesReader;

public class ServiceSpecification {

    public static RequestSpecification getServiceSpecification(String basePath) {
        return RequestSpecificationBuilder.build(PropertiesReader.get("base-uri"), basePath, ContentType.JSON);
    }

}
