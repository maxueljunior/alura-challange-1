package br.com.leuxam.alura.challange1.domain.categorias;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class CategoriasTest {
	
	@Test
	@DisplayName("Deveria retornar uma categoria corretamente")
	void test_cenario01() {
		var categoria = mock();
		
		assertEquals(1L, categoria.getId());
		assertEquals("titulo", categoria.getTitulo());
		assertEquals("#FFFFFF", categoria.getCor());
	}
	
	@Test
	@DisplayName("Deveria atualizar os dados corretamente passando o ID")
	void test_cenario02() {
		var categoria = mock();
		
		categoria.atualizar(new DadosAtualizacaoCategoria(1L, "titulo atualizado", "#CCCCCC"));
		
		assertEquals(1L, categoria.getId());
		assertEquals("titulo atualizado", categoria.getTitulo());
		assertEquals("#CCCCCC", categoria.getCor());
	}
	
	void test_cenario03() {
		var categoria = mock();
	}
	
	private Categorias mock() {
		var categoria = new Categorias(1L, "titulo", "#FFFFFF");
		return categoria;
	}
}
