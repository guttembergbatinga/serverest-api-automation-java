package br.com.serverest.api.specs;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import org.testng.annotations.BeforeSuite;

public class BaseSpec {
    public static RequestSpecification requestSpec;

    @BeforeSuite
    public void setupAll() {
        requestSpec = new RequestSpecBuilder()
                .setBaseUri("https://serverest.dev")
                .setContentType(ContentType.JSON)
                .build();
    }
}