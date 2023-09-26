package br.com.leuxam.alura.challange1.controller;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import br.com.leuxam.alura.challange1.domain.categorias.Categorias;
import br.com.leuxam.alura.challange1.domain.categorias.CategoriasRepository;
import br.com.leuxam.alura.challange1.domain.categorias.DadosAtualizacaoCategoria;
import br.com.leuxam.alura.challange1.domain.categorias.DadosCriacaoCategoria;
import br.com.leuxam.alura.challange1.domain.categorias.DadosDetalhamentoCategorias;
import br.com.leuxam.alura.challange1.domain.videos.DadosDetalhamentoVideos;
import br.com.leuxam.alura.challange1.domain.videos.VideosRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/categorias")
public class CategoriasController {
	
	@Autowired
	private CategoriasRepository categoriasRepository;
	
	@Autowired
	private VideosRepository videosRepository;
	
	@GetMapping
	public ResponseEntity<Page<DadosDetalhamentoCategorias>> findAll(
			@PageableDefault(size = 5, sort= {"titulo"}) Pageable pageable){
		
		var categorias = categoriasRepository.findAll(pageable)
				.map(DadosDetalhamentoCategorias::new);
		
		return ResponseEntity.ok(categorias);
	}
	
	@GetMapping("{id}")
	public ResponseEntity<DadosDetalhamentoCategorias> findById(
			@PathVariable(name = "id") Long id){
		var categoria = categoriasRepository.getReferenceById(id);
		
		return ResponseEntity.ok(new DadosDetalhamentoCategorias(categoria));
	}
	
	@GetMapping("/{id}/videos")
	public ResponseEntity<Page<DadosDetalhamentoVideos>> findAllVideosByCategoria(
			@PathVariable(name = "id") Long id,
			@PageableDefault(size = 5, sort= {"titulo"}) Pageable pageable){
		
		var videos = videosRepository.findAllByIdCategoria(id, pageable)
				.map(DadosDetalhamentoVideos::new);
		
		return ResponseEntity.ok(videos);
	}
	
	@PostMapping
	@Transactional
	public ResponseEntity<DadosDetalhamentoCategorias> save(
			@RequestBody @Valid DadosCriacaoCategoria dados,
			UriComponentsBuilder uriBuilder){
		var categoria = new Categorias(dados);
		categoriasRepository.save(categoria);
		URI uri = uriBuilder.path("/categorias/{id}").buildAndExpand(categoria.getId()).toUri();
		return ResponseEntity.created(uri).body(new DadosDetalhamentoCategorias(categoria));
	}
	
	@PutMapping
	@Transactional
	public ResponseEntity<DadosDetalhamentoCategorias> update(
			@RequestBody @Valid DadosAtualizacaoCategoria dados){
		var categoria = categoriasRepository.findById(dados.id()).get();
		categoria.atualizar(dados);
		return ResponseEntity.ok(new DadosDetalhamentoCategorias(categoria));
	}
	
	@DeleteMapping("/{id}")
	@Transactional
	public ResponseEntity delete(@PathVariable(name = "id") Long id) {
		var categoria = categoriasRepository.findById(id).get();
		categoriasRepository.delete(categoria);
		return ResponseEntity.noContent().build();
	}
}





