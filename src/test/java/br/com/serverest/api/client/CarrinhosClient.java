package br.com.serverest.api.client;

import br.com.serverest.api.model.CarrinhosModel;
import br.com.serverest.api.specs.BaseSpec;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class CarrinhosClient {

    private final String CARRINHOS_ENDPOINT = "/carrinhos";

    public Response listarCarrinhos() {
        return given()
                .spec(BaseSpec.requestSpec)
                .when()
                .get(CARRINHOS_ENDPOINT)
                .then()
                .log()
                .all()
                .extract()
                .response();
    }

    public Response cadastrarCarrinho(CarrinhosModel carrinho, String token) {
        return given()
                .spec(BaseSpec.requestSpec)
                .header("Authorization", token)
                .body(carrinho)
                .when()
                .post(CARRINHOS_ENDPOINT)
                .then()
                .log()
                .all()
                .extract()
                .response();
    }

    public Response consultarCarrinhoPorId(String idCarrinho) {
        return given()
                .spec((BaseSpec.requestSpec))
                .pathParams("_id", idCarrinho)
                .when()
                .get(CARRINHOS_ENDPOINT + "/{_id}")
                .then()
                .log()
                .all()
                .extract()
                .response();
    }

    public Response concluirCompra(String token) {
        return given()
                .spec(BaseSpec.requestSpec)
                .header("Authorization", token)
                .when()
                .delete(CARRINHOS_ENDPOINT + "/concluir-compra")
                .then()
                .log()
                .all()
                .extract()
                .response();
    }

    public Response cancelarCompra(String token) {
        return given()
                .spec(BaseSpec.requestSpec)
                .header("Authorization", token)
                .when()
                .delete(CARRINHOS_ENDPOINT + "/cancelar-compra")
                .then()
                .log()
                .all()
                .extract()
                .response();
    }
}