package br.com.leuxam.alura.challange1.integrationtest.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import br.com.leuxam.alura.challange1.domain.videos.DadosDetalhamentoVideos;
import br.com.leuxam.alura.challange1.domain.videos.VideosRepository;

@SpringBootTest(webEnvironment = WebEnvironment.DEFINED_PORT)
@AutoConfigureMockMvc
@AutoConfigureJsonTesters
@ActiveProfiles("test")
class VideosControllerTest {
	
	@Autowired
	private MockMvc mvc;
	
	@Autowired
	private VideosRepository videosRepository;
	
	@Autowired
	private JacksonTester<List<DadosDetalhamentoVideos>> dadosDetalhamento;

	@Test
	@DisplayName("Deveria retornar codigo Http 200 e o conteudo caso o titulo seja existente")
	void test_cenario01() throws Exception {
		var response = mvc.perform(get("/videos/")
						.param("search", "titulo"))
				.andReturn().getResponse();
		
		var videos = dadosDetalhamento.parseObject(response.getContentAsString());
		
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
	@DisplayName("Deveria retornar codigo Http 200 e sem conteudo caso o titulo não seja existente")
	void test_cenario02() throws Exception {
		var response = mvc.perform(get("/videos/")
						.param("search", "zzzzzzzzzzzz"))
				.andReturn().getResponse();
		
		var videos = dadosDetalhamento.parseObject(response.getContentAsString());
		
		assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
		assertThat(videos.size()).isEqualTo(0);
		
	}


}
