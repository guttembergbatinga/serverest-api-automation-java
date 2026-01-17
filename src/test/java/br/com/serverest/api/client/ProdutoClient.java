package br.com.serverest.api.client;

import br.com.serverest.api.model.ProdutoModel;
import br.com.serverest.api.specs.BaseSpec;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class ProdutoClient {

    private final String PRODUTOS_ENDPOINT = "/produtos";

    public Response cadastrarProduto(ProdutoModel produto, String token) {
        return given()
                .spec(BaseSpec.requestSpec)
                .header("Authorization", token)
                .body(produto)
                .when()
                .post(PRODUTOS_ENDPOINT)
                .then()
                .log().all()
                .extract().response();
    }
    public Response buscarProdutoPorId(String id) {
        return given()
                .spec(BaseSpec.requestSpec)
                .pathParam("_id", id)
                .when()
                .get(PRODUTOS_ENDPOINT + "/{_id}")
                .then()
                .log().all()
                .extract().response();
    }
    public Response excluirProduto(String id, String token) {
        return given()
                .spec(BaseSpec.requestSpec)
                .header("Authorization", token)
                .pathParam("_id", id)
                .when()
                .delete(PRODUTOS_ENDPOINT + "/{_id}")
                .then()
                .log().all()
                .extract().response();
    }
}