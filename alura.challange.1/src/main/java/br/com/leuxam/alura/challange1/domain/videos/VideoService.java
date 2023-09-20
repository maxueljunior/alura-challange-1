package br.com.leuxam.alura.challange1.domain.videos;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.leuxam.alura.challange1.domain.ValidacaoException;
import br.com.leuxam.alura.challange1.domain.categorias.Categorias;
import br.com.leuxam.alura.challange1.domain.categorias.CategoriasRepository;
import jakarta.transaction.Transactional;

@Service
public class VideoService {
	
	@Autowired
	private VideosRepository videosRepository;
	
	@Autowired
	private CategoriasRepository categoriaRepository;
	
	@Transactional
	public Videos save(DadosCriarVideos dados) {
		
		Long id = dados.idCategoria();
		
		if(dados.idCategoria() == null) {
			id = 1L;
		}
		
		var categoria = categoriaRepository.findById(id);
		
		if(!categoria.isPresent()) {
			throw new ValidacaoException("A categoria informada não existe");
		}
		
		var video = new Videos(dados, categoria.get());
		videosRepository.save(video);
		return video;
	}
	
}
