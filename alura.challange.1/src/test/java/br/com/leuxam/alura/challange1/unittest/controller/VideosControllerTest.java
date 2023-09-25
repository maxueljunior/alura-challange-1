package br.com.leuxam.alura.challange1.unittest.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.servlet.MockMvc;

import br.com.leuxam.alura.challange1.domain.categorias.Categorias;
import br.com.leuxam.alura.challange1.domain.videos.DadosDetalhamentoVideos;
import br.com.leuxam.alura.challange1.domain.videos.Videos;
import br.com.leuxam.alura.challange1.domain.videos.VideosRepository;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureJsonTesters
class VideosControllerTest {
	
	@Autowired
	private MockMvc mvc;
	
	@MockBean
	private VideosRepository videosRepository;
	
	@Autowired
	private JacksonTester<List<DadosDetalhamentoVideos>> dadosDetalhamento;
	
	@Test
	@DisplayName("Deveria retornar codigo HTTP 200 porem sem nenhum conteudo caso titulo passado não exista")
	void cenario_01() throws Exception {
		
		when(videosRepository.findAllByTitulo("algumacoisa")).thenReturn(new ArrayList<>());
		
		var response = mvc.perform(get("/videos/")
					.param("search", "algumacoisa"))
				.andReturn().getResponse();
		
		assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
		assertThat(response.getContentAsString()).isEqualTo("[]");
	}
	
	@Test
	@DisplayName("Deveria retornar codigo HTTP 200 e o conteudo caso o titulo passado seja correto")
	void cenario_02() throws Exception {
		
		var lista = mock();
		
		when(videosRepository.findAllByTitulo(any())).thenReturn(lista);
		
		var response = mvc.perform(get("/videos/?search=titulo"))
				.andReturn().getResponse();
		
		var jsonEsperado = dadosDetalhamento.write(lista.stream()
				.map(DadosDetalhamentoVideos::new)
				.collect(Collectors.toList())).getJson();
		
		assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
		assertThat(response.getContentAsString()).isEqualTo(jsonEsperado);
		
	}
	
	public List<Videos> mock(){
		List<Videos> videos = new ArrayList<>();
		var categoria = new Categorias(1L, "Musica", "#FFFFFF");
		videos.add(new Videos(1L, "titulo 01", "descricao 01", "http://", true, categoria));
		videos.add(new Videos(2L, "titulo 02", "descricao 02", "http://", true, categoria));
		videos.add(new Videos(3L, "titulo 03", "descricao 03", "http://", true, categoria));
		
		return videos;
	}
}


























