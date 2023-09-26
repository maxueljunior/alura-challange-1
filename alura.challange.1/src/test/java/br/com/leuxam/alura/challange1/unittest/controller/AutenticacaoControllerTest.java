package br.com.leuxam.alura.challange1.unittest.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.test.web.servlet.MockMvc;

import br.com.leuxam.alura.challange1.domain.user.DadosLogin;
import br.com.leuxam.alura.challange1.domain.user.Permissions;
import br.com.leuxam.alura.challange1.domain.user.Users;
import br.com.leuxam.alura.challange1.infra.security.DadosAutenticacaoJWT;
import br.com.leuxam.alura.challange1.infra.security.TokenService;

@SpringBootTest
@AutoConfigureJsonTesters
@AutoConfigureMockMvc
class AutenticacaoControllerTest {
	
	@Autowired
	private MockMvc mvc;
	
	@MockBean
	private AuthenticationManager authenticationManager;
	
	@MockBean
	private TokenService tokenService;
	
	@Autowired
	private JacksonTester<DadosLogin> dadosLogin;

	@Autowired
	private JacksonTester<DadosAutenticacaoJWT> dadosAutenticacao;
	
	@Test
	@DisplayName("Deveria devolver status 200 e um token no corpo da resposta")
	void testLogin() throws Exception {
		
		var usuario = mock();
		
		var authenticationToken = new UsernamePasswordAuthenticationToken
				(usuario.getUsername(), usuario.getPassword());
		
		var authentication = new UsernamePasswordAuthenticationToken
				(usuario, authenticationToken.getCredentials(), usuario.getAuthorities());
		
		var token = geraToken();
		
		when(authenticationManager.authenticate(any())).thenReturn(authentication);
		
		when(tokenService.gerarToken(any())).thenReturn(token);
		
		var response = mvc.perform(post("/login")
					.contentType(MediaType.APPLICATION_JSON)
					.content(dadosLogin.write(
							new DadosLogin(
									usuario.getUsername(),
									usuario.getPassword()))
							.getJson()))
				.andReturn().getResponse();
		
		var jsonEsperado = dadosAutenticacao.write(new DadosAutenticacaoJWT(token)).getJson();
		
		assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
		assertThat(response.getContentAsString()).isEqualTo(jsonEsperado);

	}
	
	private Users mock() {
		var permissao = new Permissions(1L, "ADMIN");
		var usuario = new Users(1L ,"maxuel", "12345", true, permissao);
		
		return usuario;
	}
	
	private String geraToken() {
		return "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJBUEkgYWx1cmEgY2hhbGxlbmdlIDEiLCJzdWIiOiJtYXh1ZWwiLCJleHAiOjE2OTU3NDc0ODZ9.tbl_BtgDtOl8j0BOaX6DNjhZ6jPLU_Dg0DkqV0iB30M";
	}
}
