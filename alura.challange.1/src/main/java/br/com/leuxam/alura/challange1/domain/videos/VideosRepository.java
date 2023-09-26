package br.com.leuxam.alura.challange1.domain.videos;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface VideosRepository extends JpaRepository<Videos, Long>{
	
	Page<Videos> findAllByAtivoTrue(Pageable pageable);
	
	Videos findByIdAndAtivoTrue(Long id);
	
	Page<Videos> findTop3ByAtivoTrue(Pageable pageable);
	
	@Query("SELECT v FROM Videos v WHERE v.categorias.id = :id")
	Page<Videos> findAllByIdCategoria(Long id, Pageable pageable);
	
	@Query("SELECT v FROM Videos v WHERE v.titulo LIKE %:titulo%")
	Page<Videos> findAllByTitulo(String titulo, Pageable pageable);
}
