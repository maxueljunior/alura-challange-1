package br.com.leuxam.alura.challange1.controller;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import br.com.leuxam.alura.challange1.domain.categorias.CategoriasRepository;
import br.com.leuxam.alura.challange1.domain.videos.DadosDetalhamentoVideos;
import br.com.leuxam.alura.challange1.domain.videos.VideosRepository;

class CategoriasControllerTest {
	
	private CategoriasController controller;
	
	@Mock
	private CategoriasRepository categoriasRepository;
	
	@Mock
	private VideosRepository videosRepository;
	
	@BeforeEach
	public void beforeEach() {
		MockitoAnnotations.openMocks(this);
		this.controller = new CategoriasController(categoriasRepository, videosRepository);
	}
	
	@Test
	void testFindAllVideosByCategoria() {
	}
	
}
