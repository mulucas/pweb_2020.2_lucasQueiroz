package com.pweb.agropopshop.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.pweb.agropopshop.model.Produto;

public interface ProdutoRepository extends JpaRepository<Produto, Long> {

	@Query("select p from Produto p where p.nome like %?1%")
	List<Produto> buscarProdutoPorNome(String nome);
	
	@Query("select p from Produto p order by p.nome asc")
	List<Produto> listaPorNome();
}