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
class CategoriasControllerTest {
	
	@Autowired
	private MockMvc mvc;
	
	@Autowired
	private VideosRepository videosRepository;
	
	@Autowired
	private JacksonTester<List<DadosDetalhamentoVideos>> dadosDetalhamento;
	
	@Test
	@DisplayName("Deveria retornar codigo Http 200 e retornar o conteudo caso categoria contenha videos")
	void test_cenario01() throws Exception {
		var response = mvc.perform(get("/categorias/1/videos"))
				.andReturn().getResponse();
		
		var videos = dadosDetalhamento.parseObject(response.getContentAsString());
		
		assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
		assertThat(videos.size()).isEqualTo(3);
		
		assertThat(videos.get(0).id()).isEqualTo(1L);
		assertThat(videos.get(0).idCategoria()).isEqualTo(1L);
		assertThat(videos.get(0).descricao()).isEqualTo("alguma coisa");
		assertThat(videos.get(0).titulo()).isEqualTo("titulo");
		assertThat(videos.get(0).url()).isEqualTo("url");
		
		assertThat(videos.get(2).id()).isEqualTo(3L);
		assertThat(videos.get(2).idCategoria()).isEqualTo(1L);
		assertThat(videos.get(2).descricao()).isEqualTo("alguma coisa 3");
		assertThat(videos.get(2).titulo()).isEqualTo("titulo 3");
		assertThat(videos.get(2).url()).isEqualTo("url 3");
	}
	
	@Test
	@DisplayName("Deveria retornar codigo Http 200 porem sem nenhum conteudo caso categoria não contenha nenhum video")
	void test_cenario02() throws Exception {
		var response = mvc.perform(get("/categorias/3/videos"))
				.andReturn().getResponse();
		
		var videos = dadosDetalhamento.parseObject(response.getContentAsString());
		
		assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
		assertThat(videos.size()).isEqualTo(0);
	}

}
