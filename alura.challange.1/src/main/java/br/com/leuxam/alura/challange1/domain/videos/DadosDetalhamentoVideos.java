package br.com.leuxam.alura.challange1.domain.videos;

public record DadosDetalhamentoVideos(Long id, Long idCategoria, String titulo, String descricao, String url) {
	public DadosDetalhamentoVideos(Videos videos) {
		this(videos.getId(), videos.getCategorias().getId(),
				videos.getTitulo(), videos.getDescricao(), videos.getUrl());
	}
}
