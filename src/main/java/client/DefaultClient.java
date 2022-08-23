package client;

import io.restassured.specification.RequestSpecification;
import specification.ServiceSpecification;

public abstract class DefaultClient {

    RequestSpecification serviceSpecification;

    protected DefaultClient(String basePath) {
        serviceSpecification = ServiceSpecification.getServiceSpecification(basePath);
    }

    public RequestSpecification getServiceSpecification() {
        return serviceSpecification;
    }
}
