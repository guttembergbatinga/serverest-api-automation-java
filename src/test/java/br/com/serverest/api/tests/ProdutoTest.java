package br.com.serverest.api.tests;

import br.com.serverest.api.client.LoginClient;
import br.com.serverest.api.client.ProdutoClient;
import br.com.serverest.api.client.UsuarioClient;
import br.com.serverest.api.data.ProdutoDataFactory;
import br.com.serverest.api.data.UsuarioDataFactory;
import br.com.serverest.api.model.LoginModel;
import br.com.serverest.api.model.ProdutoModel;
import br.com.serverest.api.model.UsuarioModel;
import br.com.serverest.api.specs.BaseSpec;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;
import io.restassured.response.Response;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import static org.hamcrest.Matchers.*;

@Feature("Gestão de Produtos")
public class ProdutoTest extends BaseSpec {

    private String token;
    private ProdutoClient produtoClient = new ProdutoClient();

    @BeforeClass
    public void prepararAmbiente() {
        UsuarioClient usuarioClient = new UsuarioClient();
        LoginClient loginClient = new LoginClient();
        UsuarioModel admin = UsuarioDataFactory.criarUsuarioDadosDinamicos();
        admin.setEmail("qa_" + System.currentTimeMillis() + "@teste.com");
        usuarioClient.cadastrarUsuario(admin);

        LoginModel login = new LoginModel(admin.getEmail(), admin.getPassword());
        Response response = loginClient.realizarLogin(login);

        token = response.jsonPath().getString("authorization");
    }

    @Test(description = "Deve cadastrar um produto com sucesso")
    @Severity(SeverityLevel.CRITICAL)
    @Story("Cadastro de novos produtos")
    public void deveCadastrarProdutoComSucesso() {
        ProdutoModel produto = ProdutoDataFactory.criarProdutoDinamico();
        Response response = produtoClient.cadastrarProduto(produto, token);

        response.then()
                .statusCode(201)
                .body("message", is("Cadastro realizado com sucesso"))
                .body("_id", notNullValue());
    }

    @Test(description = "Não deve permitir cadastrar produto sem token")
    @Severity(SeverityLevel.NORMAL)
    @Story("Segurança do Cadastro")
    public void deveRetornarErroAoCadastrarProdutoSemToken() {
        ProdutoModel produto = ProdutoDataFactory.criarProdutoDinamico();
        Response response = produtoClient.cadastrarProduto(produto, "");

        response.then()
                .statusCode(401)
                .body("message", is("Token de acesso ausente, inválido, expirado ou usuário do token não existe mais"));
    }

    @Test(description = "Não deve permitir cadastrar produto com preço negativo")
    @Severity(SeverityLevel.MINOR)
    @Story("Validação de campos obrigatórios/inválidos")
    public void deveRetornarErroAoCadastrarProdutoComPrecoNegativo() {
        ProdutoModel produto = ProdutoDataFactory.criarProdutoDinamico();
        produto.setPreco(-50);

        Response response = produtoClient.cadastrarProduto(produto, token);

        response.then()
                .statusCode(400)
                .body("preco", is("preco deve ser um número positivo"));
    }

    @Test(description = "Deve buscar um produto por ID com sucesso")
    @Severity(SeverityLevel.BLOCKER)
    @Story("Consulta de produtos")
    public void deveBuscarProdutoPorIdComSucesso() {
        ProdutoModel produtoNovo = ProdutoDataFactory.criarProdutoDinamico();
        Response responseCadastro = produtoClient.cadastrarProduto(produtoNovo, token);
        String idProduto = responseCadastro.jsonPath().getString("_id");

        Response responseBusca = produtoClient.buscarProdutoPorId(idProduto);

        responseBusca.then()
                .statusCode(200)
                .body("_id", is(idProduto))
                .body("nome", is(produtoNovo.getNome()))
                .body("preco", is(produtoNovo.getPreco()));
    }

    @Test(description = "Deve excluir um produto com sucesso")
    @Severity(SeverityLevel.CRITICAL)
    @Story("Exclusão de produtos")
    public void deveExcluirProdutoComSucesso() {
        ProdutoModel produto = ProdutoDataFactory.criarProdutoDinamico();
        Response responseCadastro = produtoClient.cadastrarProduto(produto, token);
        String id = responseCadastro.jsonPath().getString("_id");

        Response responseExclusao = produtoClient.excluirProduto(id, token);

        responseExclusao.then()
                .statusCode(200)
                .body("message", is("Registro excluído com sucesso"));

        produtoClient.buscarProdutoPorId(id).then().statusCode(400);
    }
}