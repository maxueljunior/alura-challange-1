package br.com.leuxam.alura.challange1.domain.videos;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import br.com.leuxam.alura.challange1.domain.categorias.Categorias;

class VideosTest {

	@Test
	@DisplayName("Deveria salvar corretamente um Video")
	void test_cenario01() {
		var videos = mock();
		
		assertEquals(1L, videos.getId());
		assertEquals(1L, videos.getCategorias().getId());
		assertEquals("titulo video", videos.getTitulo());
		assertEquals("descricao", videos.getDescricao());
		assertEquals("http://localhost:8080", videos.getUrl());
		assertTrue(videos.getAtivo());
	}
	
	@Test
	@DisplayName("Deveria atualizar corretamente um video")
	void test_cenario02() {
		var videos = mock();
		
		videos.atualizarDados(new DadosAtualizadosVideo(1L, null, "titulo video atualizado", null, null));
		
		assertEquals(1L, videos.getId());
		assertEquals(1L, videos.getCategorias().getId());
		assertEquals("titulo video atualizado", videos.getTitulo());
		assertEquals("descricao", videos.getDescricao());
		assertEquals("http://localhost:8080", videos.getUrl());
		assertTrue(videos.getAtivo());
	}
	
	@Test
	@DisplayName("Deveria desativar um Video que está ativo")
	void test_cenario03() {
		var videos = mock();
		videos.desativar();
		assertFalse(videos.getAtivo());
	}
	
	@Test
	@DisplayName("Não deveria desativar um Video que já está desativado")
	void test_cenario04() {
		var videos = mockVideoDesativado();
		var exception = assertThrows(VideoDesativadoException.class, () -> {
			videos.desativar();
		});
		
		String message = "O video já está desativado";
		
		assertEquals(message, exception.getMessage());
	}
	
	public Videos mock() {
		var categoria = mockCategoria();
		var videos = new Videos(1L,"titulo video", "descricao", "http://localhost:8080", true,categoria);
		
		return videos;
	}
	
	public Videos mockVideoDesativado() {
		var categoria = mockCategoria();
		var videos = new Videos(1L,"titulo video", "descricao", "http://localhost:8080", false, categoria);
		
		return videos;
	}
	
	private Categorias mockCategoria() {
		var categoria = new Categorias(1L, "titulo", "#FFFFFF");
		return categoria;
	}
}
