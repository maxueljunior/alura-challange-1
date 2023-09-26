package br.com.leuxam.alura.challange1.integrationtest.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import br.com.leuxam.alura.challange1.domain.user.DadosLogin;
import br.com.leuxam.alura.challange1.infra.security.DadosAutenticacaoJWT;
import br.com.leuxam.alura.challange1.infra.security.TokenService;

@SpringBootTest(webEnvironment = WebEnvironment.DEFINED_PORT)
@AutoConfigureJsonTesters
@AutoConfigureMockMvc
@ActiveProfiles("test")
class AutenticacaoControllerTest {
	
	@Autowired
	private MockMvc mvc;
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private TokenService tokenService;
	
	@Autowired
	private JacksonTester<DadosLogin> dadosLogin;
	
	@Autowired
	private JacksonTester<DadosAutenticacaoJWT> dadosAutenticacao;
	
	@Test
	@DisplayName("Deveria retornar status code HTTP 200 e o token")
	void test_cenario01() throws Exception {
		
		var response = mvc.perform(post("/login")
					.contentType(MediaType.APPLICATION_JSON)
					.content(
							dadosLogin.write(new DadosLogin("maxuel", "123456"))
							.getJson())
					).andReturn().getResponse();
		
		var tokenJWT = dadosAutenticacao.parseObject(response.getContentAsString());
		
		assertThat(tokenJWT.token()).isNotBlank();
		System.out.println(tokenJWT.token());
	}
	
	@Test
	@DisplayName("Deveria retornar status code HTTP 403 e retornar usuario e senha incorretos")
	void test_cenario02() throws Exception {
		
		var response = mvc.perform(post("/login")
					.contentType(MediaType.APPLICATION_JSON)
					.content(
							dadosLogin.write(new DadosLogin("admin", "123"))
							.getJson())
					).andReturn().getResponse();
		
		assertThat(response.getStatus()).isEqualTo(HttpStatus.FORBIDDEN.value());
		assertThat(response.getContentAsString()).isEqualTo("Usuario e senha incorretos");
		
	}

}
