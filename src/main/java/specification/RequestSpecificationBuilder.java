package specification;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.config.RestAssuredConfig;
import io.restassured.config.SSLConfig;
import io.restassured.filter.log.LogDetail;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

public class RequestSpecificationBuilder {

    static RequestSpecification build(String baseUri, String basePath, ContentType contentType) {
        RequestSpecBuilder requestSpecBuilder = new RequestSpecBuilder();
        requestSpecBuilder.setConfig(new RestAssuredConfig().sslConfig(new SSLConfig().relaxedHTTPSValidation()));
        requestSpecBuilder.setRelaxedHTTPSValidation();
        requestSpecBuilder.log(LogDetail.ALL);
        requestSpecBuilder.addFilter(new ResponseLoggingFilter());
        requestSpecBuilder.setContentType(contentType);
        requestSpecBuilder.setBaseUri(baseUri);
        requestSpecBuilder.setBasePath(basePath);

        return requestSpecBuilder.build();
    }
}
