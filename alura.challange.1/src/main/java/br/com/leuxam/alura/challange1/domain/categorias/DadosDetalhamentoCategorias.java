package br.com.leuxam.alura.challange1.domain.categorias;

public record DadosDetalhamentoCategorias(
		Long id,
		String titulo,
		String cor) {
	public DadosDetalhamentoCategorias(Categorias categoria) {
		this(categoria.getId(), categoria.getTitulo(), categoria.getCor());
	}
}
