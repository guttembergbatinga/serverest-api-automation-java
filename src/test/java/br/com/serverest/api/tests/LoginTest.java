package br.com.serverest.api.tests;

import br.com.serverest.api.client.LoginClient;
import br.com.serverest.api.client.UsuarioClient;
import br.com.serverest.api.data.UsuarioDataFactory;
import br.com.serverest.api.model.LoginModel;
import br.com.serverest.api.model.UsuarioModel;
import br.com.serverest.api.specs.BaseSpec;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import static org.hamcrest.Matchers.*;

public class LoginTest extends BaseSpec {

    private UsuarioClient usuarioClient = new UsuarioClient();
    private LoginClient loginClient = new LoginClient();

    @Test(description = "Deve realizar login com sucesso")
    public void deveRealizarLoginComSucesso() {
        UsuarioModel usuario = UsuarioDataFactory.criarUsuarioDadosDinamicos();
        usuarioClient.cadastrarUsuario(usuario);
        LoginModel login = new LoginModel(usuario.getEmail(), usuario.getPassword());
        Response response = loginClient.realizarLogin(login);

        response.then()
                .statusCode(200)
                .body("message", is("Login realizado com sucesso"))
                .body("authorization", containsString("Bearer"));

        String token = response.jsonPath().getString("authorization");
        System.out.println("TOKEN CAPTURADO: " + token);
    }
}