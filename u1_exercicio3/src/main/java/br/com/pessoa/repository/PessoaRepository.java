package br.com.pessoa.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.pessoa.model.Pessoa;

public interface PessoaRepository extends JpaRepository<Pessoa, Integer> {

}
