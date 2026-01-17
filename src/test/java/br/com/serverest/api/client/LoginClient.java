package br.com.serverest.api.client;

import br.com.serverest.api.model.LoginModel;
import br.com.serverest.api.specs.BaseSpec;
import io.restassured.response.Response;
import static io.restassured.RestAssured.given;

public class LoginClient {

    private final String LOGIN_ENDPOINT = "/login";

    public Response realizarLogin(LoginModel login) {
        return given()
                .spec(BaseSpec.requestSpec)
                .log().all()
                .body(login)
                .when()
                .post(LOGIN_ENDPOINT)
                .then()
                .log().all()
                .extract().response();
    }
}