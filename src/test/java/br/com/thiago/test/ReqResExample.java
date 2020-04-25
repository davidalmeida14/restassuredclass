package br.com.thiago.test;

import static br.com.thiago.servicos.Servicos.PATH_USERS_ID;
import static br.com.thiago.servicos.Servicos.PATH_USERS_PAGE;
import static io.restassured.RestAssured.basePath;
import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;

import org.apache.http.HttpStatus;
import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import br.com.thiago.servicos.Servicos;
import br.com.thiago.test.entidate.PessoaRequest;
import br.com.thiago.test.entidate.PessoaResponse;
import br.com.thiago.test.entidate.PessoaResponseUpdate;
import io.restassured.http.ContentType;
import io.restassured.module.jsv.JsonSchemaValidator;

public class ReqResExample {

	@Before
	public void configuraApi() {
		baseURI = 	"https://reqres.in/";
	}
	
	/**
	 * Cenário: Enviar request request para o path /api/users/{id} para buscar um usuário
	 * Resultado: StatusCode igual a 204
	 * Método: GET
	 * 
	 */
	@Test
	public void methodGet() {
		
		basePath = Servicos.PATH_USERS_ID.getValor();
		
		Integer idUsuario = 2;
		
		//		PessoaResponse response = 
		given()
			.pathParam("id", idUsuario )
		.when()
			.get()

		.then()
			.contentType(ContentType.JSON)
			.statusCode(HttpStatus.SC_OK)
			.body(JsonSchemaValidator.matchesJsonSchemaInClasspath("Schemas/davidExemplo.json"));
//		.and()
//			.extract()
//				.response()
//				.as(PessoaResponse.class);
//		System.out.println("Id: " + response.getId());
	}
	
	/**
	 * Cenário de teste: Enviar request para cadastrar um usuário.
	 * Resultado: StatusCode CREATED e corpo da requisição íntegro e válido
	 * Método: POST
	 * 
	 */
	@Test
	public void methodPost() {
		
		PessoaRequest request = new PessoaRequest("David", "Developer");
		
		basePath= "/api/users";		
		
		PessoaResponse as = given()
			.contentType("application/json")
		.body(request)
		.when()
			.post("/")
		.then()
			.statusCode(HttpStatus.SC_CREATED)
			.extract().response().as(PessoaResponse.class);
		
		System.out.println("O Id é: " + as.getId());
	}
	
	/**
	 * Cenário de teste: Enviar request para recuperar listagem de usuários.
	 * Resultado: StatusCode 200 e conteúdo de response válido.
	 * Método: GET
	 * 
	 */
	@Test
	public void dadoListagemDeUsuarios_retornaSucessoAndCamposValidos() {
		
		String pathSchema = "Schemas/listUserPage.json";
		basePath = PATH_USERS_PAGE.getValor();
		
		given()
			.accept(ContentType.JSON)
			.contentType(ContentType.JSON)
			.param("page", "2")
			.param("total", "12")
			.log().all()
		.when()
			.get()
		.then()
			.statusCode(HttpStatus.SC_OK)
			.body(JsonSchemaValidator.matchesJsonSchemaInClasspath(pathSchema))
			.body("page", equalTo(2))
		.and()
			.body("total", equalTo(12))
		.and()
			.body("data", Matchers.hasSize(6));
		
	}
	
	/**
	 * Cenário de teste: Enviar request PUT para atualização de dados.
	 * Resultado: StatusCode 200 e conteúdo de response válido.
	 * Método: PUT
	 * 
	 */
	@Test
	public void dadoPUTdeveRetornar200ComCounteudoAtualizado() {
		
		String pathSchema = "Schemas/userUpdateResponse.json";
		
		basePath = PATH_USERS_ID.getValor();
	
		PessoaRequest request = new PessoaRequest();
		request.setName("Teste");
		request.setJob("Analista de Dados");
		
		int codigoUsuario = 2;
		
			PessoaResponseUpdate response = given()
				.accept(ContentType.JSON)
				.contentType(ContentType.JSON)
				.pathParam("id", codigoUsuario)
				.log().all()
				.body(request)
			.when()
				.put()
			.then()
				.statusCode(HttpStatus.SC_OK)
				.body(JsonSchemaValidator.matchesJsonSchemaInClasspath(pathSchema))
				.extract()
				.response()
				.as(PessoaResponseUpdate.class);
			
			
		Assert.assertThat(response.getName().toLowerCase(), equalTo(request.getName().toLowerCase()));
		Assert.assertThat(response.getJob().toLowerCase(), equalTo(request.getJob().toLowerCase()));
		
	}
	/**
	 * Cenário: Enviar request request para o path /api/users/{id}
	 * Resultado: StatusCode igual a 204
	 * Método: DELETE
	 * 
	 */
	@Test
	public void dadoRequestDelete_DeveRetornar204() {
		
		basePath = Servicos.PATH_USERS_ID.getValor();
		
		given()
			.pathParam("id", 2)
			.log().all()
		.when()
			.delete()
		.then()
			.statusCode(HttpStatus.SC_NO_CONTENT);
			
	}
	
}
