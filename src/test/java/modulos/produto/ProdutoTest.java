package modulos.produto;

import static org.hamcrest.Matchers.equalTo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import io.restassured.RestAssured;
import static io.restassured.RestAssured.given;
import modulos.dataFactory.ProdutoFactory;
import pogo.Produto;
import pogo.Usuario;

@DisplayName("Testes de Api Rest do modulo de Produto")
public class ProdutoTest {

    private String token;

    @BeforeEach
    public void beforeEach() {
        RestAssured.baseURI = "http://165.227.93.41";
        RestAssured.basePath = "/lojinha";

        Usuario user = new Usuario();
        user.setUsuarioLogin("begas");
        user.setUsuarioSenha("123456");

        this.token = given()
                .contentType("application/json")
                .body(user)
                .when()
                .post("/v2/login")
                .then()
                .statusCode(200)
                .extract()
                .path("data.token");
    }

    @Test
    @DisplayName("Validar os limites do valor do produto")
    public void validarLimitesValorProduto() {

        Produto produto = ProdutoFactory.criarProdutoComum("Neil", 0, "Dias");

        given()
                .header("token", this.token)
                .contentType("application/json")
                .body(produto)
                .when()
                .post("/v2/produtos")
                .then()
                .assertThat()
                .body("error", equalTo("O valor do produto deve estar entre R$ 0,01 e R$ 7.000,00"))
                .statusCode(422);

    }

    @Test
    @DisplayName("Validar o cadastro de produto com sucesso")
    public void CadastrarProduto() {

        Produto produto = ProdutoFactory.criarProdutoComum("Neil", 100, "Dias");

        given()
                .log().all() // Isso imprime a requisição completa no console
                .header("token", this.token)
                .contentType("application/json")
                .body(produto)
                .when()
                .post("/v2/produtos")
                .then()
                .log().all() // Isso imprime a resposta completa do servidor (incluindo o erro detalhado!)
                .statusCode(201);
    }

    @Test
    @DisplayName("Listar produtos cadastrados")
    public void ListarProdutos(){
        given()
            .log().all()
            .header("token", this.token)
            .contentType("application/json")
        .when()
            .get("/v2/produtos")
        .then()
            .log().all() // Isso imprime a resposta completa do servidor (incluindo o erro detalhado!)
            .statusCode(200);
    }

    @Test
    @DisplayName("LIstar um produto especifico")
    public void ListarProdutoEspecifico(){
        given()
            .log().all()
            .header("token", this.token)
            .contentType("application/json")
        .when()
            .get("/v2/produtos/1046868")
        .then()
            .log().all() // Isso imprime a resposta completa do servidor (incluindo o erro detalhado!)
            .statusCode(200);
    }

    @Test
    @DisplayName("Alterar um produto especifico")
    public void AlterarProdutoEspecifico(){
        Produto produto = ProdutoFactory.criarProdutoComum("Neil", 200, "Dias");

        given()
            .log().all()
            .header("token", this.token)
            .contentType("application/json")
            .body(produto)
        .when()
            .put("/v2/produtos/1046868")
        .then()
            .log().all()
            .statusCode(200);
    }
}
