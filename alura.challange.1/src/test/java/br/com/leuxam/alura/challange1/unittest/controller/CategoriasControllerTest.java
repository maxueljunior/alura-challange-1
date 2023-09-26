package br.com.leuxam.alura.challange1.unittest.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.servlet.MockMvc;

import br.com.leuxam.alura.challange1.domain.categorias.Categorias;
import br.com.leuxam.alura.challange1.domain.categorias.CategoriasRepository;
import br.com.leuxam.alura.challange1.domain.videos.DadosDetalhamentoVideos;
import br.com.leuxam.alura.challange1.domain.videos.Videos;
import br.com.leuxam.alura.challange1.domain.videos.VideosRepository;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureJsonTesters
class CategoriasControllerTest {
	
	@Autowired
	private MockMvc mvc;
	
	@Autowired
	private JacksonTester<List<DadosDetalhamentoVideos>> listDadosDetalhamento;
	
	@MockBean
	private CategoriasRepository categoriasRepository;
	
	@MockBean
	private VideosRepository videosRepository;
	
	@Test
	@DisplayName("Deveria devolver codigo http 200 com a lista de videos caso id categoria exista e contenha videos")
	void test_cenario01() throws Exception {
		
		var lista = mockVideos();
		
		when(videosRepository.findAllByIdCategoria(eq(1L), any())).thenReturn(lista);
		
		var response = mvc.perform(get("/categorias/1/videos"))
				.andReturn().getResponse();
		
		var jsonEsperado = listDadosDetalhamento.write(mockVideos().stream()
				.map(DadosDetalhamentoVideos::new).collect(Collectors.toList())).getJson();
		
		assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
		assertThat(response.getContentAsString()).contains(jsonEsperado);
	}
	
	@Test
	@DisplayName("Deveria devolver codigo http 200, porem sem nenhum conteudo caso id categoria não exista")
	void cenario_02() throws Exception {
		
		when(videosRepository.findAllByIdCategoria(eq(100L), any())).thenReturn(new PageImpl<>(new ArrayList<>()));
		
		var response = mvc.perform(get("/categorias/100/videos"))
						.andReturn().getResponse();
		
		assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
		assertThat(response.getContentAsString()).contains("\"content\":[]");
	}

	public Page<Videos> mockVideos(){
		Categorias categoria = new Categorias(1L, "Entreterimento", "#FFFFFF");
		List<Videos> dados = new ArrayList<>();
		
		dados.add(new Videos(1L, "algo", "descricao", "url", true, categoria));
		dados.add(new Videos(2L, "algo2", "descricao2", "url2", true, categoria));
		dados.add(new Videos(3L, "algo3", "descricao3", "url3", true, categoria));
		
		return new PageImpl<>(dados);
	}
	
}







