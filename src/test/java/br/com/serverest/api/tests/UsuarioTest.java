package br.com.serverest.api.tests;

import br.com.serverest.api.client.UsuarioClient;
import br.com.serverest.api.data.UsuarioDataFactory;
import br.com.serverest.api.model.UsuarioModel;
import br.com.serverest.api.specs.BaseSpec;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import static org.hamcrest.Matchers.*;

@Feature("Gestão de Usuários")
public class UsuarioTest extends BaseSpec {

    private UsuarioClient usuarioClient = new UsuarioClient();

    @Test(description = "Deve cadastrar um usuário com sucesso")
    @Severity(SeverityLevel.CRITICAL)
    @Story("Cadastro de Usuários")
    public void deveCadastrarUsuarioComSucesso() {
        UsuarioModel usuario = UsuarioDataFactory.criarUsuarioDadosDinamicos();
        Response response = usuarioClient.cadastrarUsuario(usuario);

        response.then()
                .statusCode(201)
                .body("message", is("Cadastro realizado com sucesso"))
                .body("_id", notNullValue());
    }

    @Test(description = "Deve consultar um usuário com sucesso")
    @Severity(SeverityLevel.NORMAL)
    @Story("Consulta de Usuários")
    public void deveConsultarUmUsuarioComSucesso() {
        UsuarioModel usuario = UsuarioDataFactory.criarUsuarioDadosDinamicos();
        Response response = usuarioClient.cadastrarUsuario(usuario);
        String id = response.jsonPath().getString("_id");

        Response responseConsulta = usuarioClient.buscarUsuarioPorId(id);

        responseConsulta.then()
                .statusCode(200)
                .body("_id", is(id))
                .body("nome", is(usuario.getNome()))
                .body("email", is(usuario.getEmail()));
    }

    @Test(description = "Deve deletar um Usuário com sucesso")
    @Severity(SeverityLevel.CRITICAL)
    @Story("Exclusão de Usuários")
    public void deveDeletarUsuarioComSucesso() {
        UsuarioModel usuario = UsuarioDataFactory.criarUsuarioDadosDinamicos();
        Response response = usuarioClient.cadastrarUsuario(usuario);
        String id = response.jsonPath().getString("_id");

        Response responseDeletar = usuarioClient.deletarUsuarioPorId(id);
        responseDeletar.then()
                .statusCode(200)
                .body("message", is("Registro excluído com sucesso"));

        usuarioClient.buscarUsuarioPorId(id)
                .then()
                .statusCode(400)
                .body("message", is("Usuário não encontrado"));
    }
}