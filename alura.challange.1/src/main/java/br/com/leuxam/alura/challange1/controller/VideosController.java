package br.com.leuxam.alura.challange1.controller;

import java.net.URI;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import br.com.leuxam.alura.challange1.domain.videos.DadosAtualizadosVideo;
import br.com.leuxam.alura.challange1.domain.videos.DadosCriarVideos;
import br.com.leuxam.alura.challange1.domain.videos.DadosDetalhamentoVideos;
import br.com.leuxam.alura.challange1.domain.videos.VideoService;
import br.com.leuxam.alura.challange1.domain.videos.VideosRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/videos")
public class VideosController {
	
	@Autowired
	private VideoService service;
	
	@Autowired
	private VideosRepository videosRepository;
	
	@GetMapping
	public ResponseEntity<Page<DadosDetalhamentoVideos>> findAll(
			@PageableDefault(size = 5, sort = {"titulo"}) Pageable pageable) {
		
		var videos = videosRepository.findAllByAtivoTrue(pageable).map(DadosDetalhamentoVideos::new);
		
		return ResponseEntity.ok().body(videos);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<DadosDetalhamentoVideos> findById(
			@PathVariable(name = "id") Long id){
		var video = videosRepository.findByIdAndAtivoTrue(id);
		
		return ResponseEntity.ok(new DadosDetalhamentoVideos(video));
	}
	
	@GetMapping("/")
	public ResponseEntity<Page<DadosDetalhamentoVideos>> findByTitulo(
			@RequestParam(value = "search") String titulo,
			@PageableDefault(size = 5, sort = {"titulo"}) Pageable pageable){
		
		var videos = videosRepository.findAllByTitulo(titulo, pageable).map(DadosDetalhamentoVideos::new);
		
		return ResponseEntity.ok(videos);
	}
	
	@GetMapping("/free")
	public ResponseEntity<Page<DadosDetalhamentoVideos>> findByVideosFree(
			@PageableDefault(size = 3, sort = {"titulo"}) Pageable pageable){
		var videos = videosRepository.findTop3ByAtivoTrue(pageable)
					.map(DadosDetalhamentoVideos::new);
		return ResponseEntity.ok(videos);
	}
	
	@PostMapping
	@Transactional
	public ResponseEntity<DadosDetalhamentoVideos> save(@RequestBody @Valid DadosCriarVideos dados,
			UriComponentsBuilder uriBuilder){
		
		var video = service.save(dados);
		URI uri = uriBuilder.path("/videos/{id}").buildAndExpand(video.getId()).toUri();
		return ResponseEntity.created(uri).body(new DadosDetalhamentoVideos(video));
	}
	
	@PutMapping
	@Transactional
	public ResponseEntity update(@RequestBody @Valid DadosAtualizadosVideo dados){
		var video = videosRepository.findById(dados.id()).get();
		video.atualizarDados(dados);
		return ResponseEntity.ok().build();
	}
	
	@DeleteMapping("/{id}")
	@Transactional
	public ResponseEntity delete(@PathVariable(name = "id") Long id) {
		var video = videosRepository.findById(id).get();
		video.desativar();
		return ResponseEntity.noContent().build();
	}
}










