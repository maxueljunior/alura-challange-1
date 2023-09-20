package br.com.leuxam.alura.challange1.domain.videos;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface VideosRepository extends JpaRepository<Videos, Long>{
	
	List<Videos> findAllByAtivoTrue();
	
	Videos findByIdAndAtivoTrue(Long id);
	
	@Query("SELECT v FROM Videos v WHERE v.categorias.id = :id")
	List<Videos> findAllByIdCategoria(Long id);
	
	@Query("SELECT v FROM Videos v WHERE v.titulo LIKE %:titulo%")
	List<Videos> findAllByTitulo(String titulo);
}
