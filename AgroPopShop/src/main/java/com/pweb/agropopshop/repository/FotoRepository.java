package com.pweb.agropopshop.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.pweb.agropopshop.model.Foto;

@Repository
public interface FotoRepository extends JpaRepository<Foto, Long>{

	@Query("select t from Foto t where t.produto.id = ?1")
	public List<Foto> getFotos(Long produtoid);
}

