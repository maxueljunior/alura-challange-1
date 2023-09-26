package br.com.leuxam.alura.challange1.integrationtest.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import java.io.IOException;
import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
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
import br.com.leuxam.alura.challange1.domain.videos.DadosDetalhamentoVideos;
import br.com.leuxam.alura.challange1.domain.videos.VideosRepository;
import br.com.leuxam.alura.challange1.infra.security.DadosAutenticacaoJWT;
import br.com.leuxam.alura.challange1.infra.security.TokenService;
import br.com.leuxam.alura.challange1.integrationtest.deserialization.WrapperVideos;

@SpringBootTest(webEnvironment = WebEnvironment.DEFINED_PORT)
@AutoConfigureMockMvc
@AutoConfigureJsonTesters
@ActiveProfiles("test")
@TestInstance(Lifecycle.PER_CLASS)
class VideosControllerTest {
	
	@Autowired
	private MockMvc mvc;
	
	@Autowired
	private VideosRepository videosRepository;
	
	@Autowired
	private JacksonTester<List<DadosDetalhamentoVideos>> dadosDetalhamento;
	
	private String token;
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private TokenService tokenService;
	
	@Autowired
	private JacksonTester<DadosLogin> dadosLogin;
	
	@Autowired
	private JacksonTester<DadosAutenticacaoJWT> dadosAutenticacao;
	
	@Autowired
	private JacksonTester<WrapperVideos> dados;
	
	@BeforeAll
	void login() throws IOException, Exception {
		var response = mvc.perform(post("/login")
				.contentType(MediaType.APPLICATION_JSON)
				.content(
						dadosLogin.write(new DadosLogin("maxuel", "123456"))
						.getJson())
				).andReturn().getResponse();
	
		var tokenJWT = dadosAutenticacao.parseObject(response.getContentAsString());
		token = tokenJWT.token();
	}
	
	@Test
	@DisplayName("Deveria retornar codigo Http 200 e o conteudo caso o titulo seja existente")
	void test_cenario01() throws Exception {
		var response = mvc.perform(get("/videos/")
						.header("Authorization", "Bearer " + token)
						.param("search", "titulo"))
				.andReturn().getResponse();
		
		var allVideos = dados.parseObject(response.getContentAsString());
		var videos = allVideos.getContent();
		
		assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
		assertThat(videos.size()).isEqualTo(4);
		
		assertThat(videos.get(0).id()).isEqualTo(1L);
		assertThat(videos.get(0).idCategoria()).isEqualTo(1L);
		assertThat(videos.get(0).descricao()).isEqualTo("alguma coisa");
		assertThat(videos.get(0).titulo()).isEqualTo("titulo");
		assertThat(videos.get(0).url()).isEqualTo("url");
		
		assertThat(videos.get(3).id()).isEqualTo(4L);
		assertThat(videos.get(3).idCategoria()).isEqualTo(2L);
		assertThat(videos.get(3).descricao()).isEqualTo("alguma coisa 4");
		assertThat(videos.get(3).titulo()).isEqualTo("titulo 4");
		assertThat(videos.get(3).url()).isEqualTo("url 4");
	}
	
	@Test
	@DisplayName("Deveria retornar codigo Http 403 ou erro")
	void test_cenario02() throws Exception {
		
		assertThatThrownBy(() -> {
			var response = mvc.perform(get("/videos/")
					.header("Authorization", "")
					.param("search", "zzzzzzzzzzzz"))
					.andReturn().getResponse();
		}).isInstanceOf(RuntimeException.class)
			.hasMessage("Token JWT invalido ou expirado");
		
	}


}
