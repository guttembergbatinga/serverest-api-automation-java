package br.com.serverest.api.client;

import br.com.serverest.api.model.UsuarioModel;
import br.com.serverest.api.specs.BaseSpec;
import io.restassured.response.Response;
import static io.restassured.RestAssured.given;

public class UsuarioClient {

    private final String USUARIOS_ENDPOINT = "/usuarios";

    public Response cadastrarUsuario(UsuarioModel usuario) {
        return given()
                .spec(BaseSpec.requestSpec)
                .body(usuario)
                .when()
                .post(USUARIOS_ENDPOINT);
    }

    public Response listarUsuarios() {
        return given()
                .spec(BaseSpec.requestSpec)
                .when()
                .get(USUARIOS_ENDPOINT);
    }
    public Response buscarUsuarioPorId(String id) {
        return given()
                .spec(BaseSpec.requestSpec)
                .pathParams("_id", id)
                .when()
                .get(USUARIOS_ENDPOINT + "/{_id}")
                .then()
                .log().all()
                .extract().response();
    }

    public Response deletarUsuarioPorId (String id){
        return given()
                .spec(BaseSpec.requestSpec)
                .pathParams("_id", id)
                .when()
                .delete(USUARIOS_ENDPOINT + "/{_id}")
                .then()
                .log().all()
                .extract().response();
    }
}