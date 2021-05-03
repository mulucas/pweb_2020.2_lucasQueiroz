package br.com.pessoa.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import br.com.pessoa.model.Pessoa;
import br.com.pessoa.repository.PessoaRepository;

@Service
public class PessoaService {

	private PessoaRepository repo;

	public PessoaService(PessoaRepository repo) {
		super();
		this.repo = repo;
	}

	public List<Pessoa> buscarTodos() {

		return repo.findAll();
	}

	public Pessoa salvarPessoa(Pessoa pessoa) {

		return repo.save(pessoa);

	}

	public Pessoa editarTarefa(Integer id, Pessoa pessoa) {
		pessoa.setId(id);
		pessoa = repo.save(pessoa);

		return pessoa;

	}

	public void deletarPessoa(Integer id) {
		repo.deleteById(id);
	}
	
	public Optional<Pessoa> buscarPorId(Integer id) {
		Optional<Pessoa> pessoa = repo.findById(id);
		return pessoa;
	}

}
