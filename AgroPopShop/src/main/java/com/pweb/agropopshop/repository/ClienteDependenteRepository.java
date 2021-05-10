package com.pweb.agropopshop.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.pweb.agropopshop.model.ClienteDependente;


@Repository
@Transactional
public interface ClienteDependenteRepository extends CrudRepository<ClienteDependente, Long> {

	@Query("select c from ClienteDependente c where c.cliente.id = ?1")
	public List<ClienteDependente> getClienteDependentes(Long clienteid);
}
