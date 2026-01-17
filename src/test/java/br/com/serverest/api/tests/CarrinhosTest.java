package br.com.serverest.api.tests;

import br.com.serverest.api.client.*;
import br.com.serverest.api.data.*;
import br.com.serverest.api.dataFactory.CarrinhosDataFactory;
import br.com.serverest.api.model.*;
import br.com.serverest.api.specs.BaseSpec;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import static org.hamcrest.Matchers.*;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;

public class CarrinhosTest extends BaseSpec {

    private final CarrinhosClient carrinhosClient = new CarrinhosClient();
    private final UsuarioClient usuarioClient = new UsuarioClient();
    private final LoginClient loginClient = new LoginClient();
    private final ProdutoClient produtoClient = new ProdutoClient();

    private String token;
    private String idProduto;

    @BeforeMethod
    public void setup() {
        UsuarioModel usuario = UsuarioDataFactory.criarUsuarioDadosDinamicos();
        usuario.setAdministrador("true");
        usuarioClient.cadastrarUsuario(usuario);

        LoginModel login = new LoginModel(usuario.getEmail(), usuario.getPassword());
        token = loginClient.realizarLogin(login)
                .jsonPath()
                .getString("authorization");

        ProdutoModel produto = ProdutoDataFactory.criarProdutoDinamico();
        idProduto = produtoClient.cadastrarProduto(produto, token)
                .jsonPath()
                .getString("_id");
    }

    @AfterMethod
    public void tearDown() {
        carrinhosClient.cancelarCompra(token);
    }

    // --- TESTES FUNCIONAIS (REGRAS DE NEGÓCIO) ---

    @Test(description = "Deve cadastrar um carrinho com sucesso")
    public void deveCadastrarCarrinhoComSucesso() {
        CarrinhosModel carrinho = CarrinhosDataFactory.gerarCarrinhoComUmProduto(idProduto, 1);

        carrinhosClient.cadastrarCarrinho(carrinho, token)
                .then()
                .statusCode(201)
                .body("message", is("Cadastro realizado com sucesso"));
    }

    @Test(description = "Não deve permitir mais de um carrinho por usuário")
    public void naoDevePermitirMaisDeUmCarrinho() {
        CarrinhosModel carrinho = CarrinhosDataFactory.gerarCarrinhoComUmProduto(idProduto, 1);
        carrinhosClient.cadastrarCarrinho(carrinho, token);

        carrinhosClient.cadastrarCarrinho(carrinho, token)
                .then()
                .statusCode(400)
                .body("message", is("Não é permitido ter mais de 1 carrinho"));
    }

    @Test(description = "Deve consultar um carrinho pelo ID com sucesso")
    public void deveConsultarCarrinhoPorId() {
        CarrinhosModel carrinho = CarrinhosDataFactory.gerarCarrinhoComUmProduto(idProduto, 1);
        String idCarrinho = carrinhosClient.cadastrarCarrinho(carrinho, token)
                .jsonPath()
                .getString("_id");

        carrinhosClient.consultarCarrinhoPorId(idCarrinho)
                .then()
                .statusCode(200)
                .body("_id", is(idCarrinho));
    }

    @Test(description = "Ao concluir uma compra o carrinho deve ser excluído")
    public void deveExcluirCarrinhoAoConcluirCompra() {
        CarrinhosModel carrinho = CarrinhosDataFactory.gerarCarrinhoComUmProduto(idProduto, 1);
        carrinhosClient.cadastrarCarrinho(carrinho, token);

        carrinhosClient.concluirCompra(token)
                .then()
                .statusCode(200)
                .body("message", is("Registro excluído com sucesso"));
    }

    // --- TESTES DE CONTRATO (JSON SCHEMA) ---

    @Test(description = "Deve validar o contrato da resposta de cadastro de carrinho")
    public void deveValidarContratoCadastroCarrinho() {
        CarrinhosModel carrinho = CarrinhosDataFactory.gerarCarrinhoComUmProduto(idProduto, 1);

        carrinhosClient.cadastrarCarrinho(carrinho, token)
                .then()
                .statusCode(201)
                .body(matchesJsonSchemaInClasspath("schemas/carrinho-post-schema.json"));
    }

    @Test(description = "Deve validar o contrato da consulta de carrinho por ID")
    public void deveValidarContratoConsultaCarrinho() {
        CarrinhosModel carrinho = CarrinhosDataFactory.gerarCarrinhoComUmProduto(idProduto, 1);
        String idCarrinho = carrinhosClient.cadastrarCarrinho(carrinho, token)
                .jsonPath()
                .getString("_id");

        carrinhosClient.consultarCarrinhoPorId(idCarrinho)
                .then()
                .statusCode(200)
                .body(matchesJsonSchemaInClasspath("schemas/carrinho-get-schema.json"));
    }
}